package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.effects.MariShiningTornadoFirstTossEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariStartShiningTornadoAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariStartShiningTornadoAction.class.getName());
    private static final float DURATION = 0.01F;
    public int bounceAmount;
    public int damage;
    public int radianceAmount;

    public MariStartShiningTornadoAction(int bounceAmount, int damage, int radianceAmount) {
        this.bounceAmount = bounceAmount;
        this.damage = damage;
        this.radianceAmount = radianceAmount;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractMonster randomMonster = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng);

        AbstractDungeon.actionManager.addToTop(new MariShiningTornadoAction(randomMonster, this.radianceAmount, this.bounceAmount, this.damage));

        if (randomMonster != null) {
            AbstractDungeon.actionManager.addToTop(new VFXAction(new MariShiningTornadoFirstTossEffect(p.hb.cX, p.hb.cY, randomMonster.hb.cX, p.hb.cY), 0.2F));
        }

        this.isDone = true;
    }
}
