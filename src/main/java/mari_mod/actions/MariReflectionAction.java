package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class MariReflectionAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariReflectionAction.class.getName());
    private static final float DURATION = 0.01F;
    public boolean upgraded;

    public MariReflectionAction(boolean upgraded, AbstractCreature target) {
        this.upgraded = upgraded;
        this.target = target;
    }

    public void update() {

        AbstractPlayer p = AbstractDungeon.player;

        if(this.target.hasPower(Radiance_Power.POWER_ID)) {

            ArrayList<AbstractCreature> allOthers = new ArrayList<>();

            for(AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if(!monster.isDead && !monster.halfDead && !this.target.equals(monster)) {
                    allOthers.add(monster);
                }
            }

            if(!this.target.equals(p)){
                allOthers.add(p);
            }

            int currRadiance = this.target.getPower(Radiance_Power.POWER_ID).amount;

            if(currRadiance > 0 && allOthers.size() > 0) {
                if (this.upgraded) {
                    for(AbstractCreature other: allOthers){
                        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(other, p, new Radiance_Power(other, currRadiance), currRadiance));
                    }
                } else {
                    AbstractCreature luckySoul = allOthers.get(AbstractDungeon.cardRandomRng.random(0,allOthers.size()-1));
                    AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(luckySoul, p, new Radiance_Power(luckySoul, currRadiance), currRadiance));
                }

            }
        }
        this.isDone = true;
    }
}
