package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import mari_mod.effects.MariHelicopterEffect;
import mari_mod.effects.MariShiningTornadoEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariTheHelicopterAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariTheHelicopterAction.class.getName());
    private static final float DURATION = 0.01F;
    private static final float POST_ATTACK_WAIT_DUR = 0.1F;
    private DamageInfo helicopterDamage;
    private int damage;
    private int numTimes;
    private int amount;

    public MariTheHelicopterAction(int numTimes, int damage) {
        this.actionType = ActionType.DAMAGE;
        this.duration = 0.001F;
        this.numTimes = numTimes;
        this.damage = damage;
        this.helicopterDamage = new DamageInfo(AbstractDungeon.player,damage, DamageInfo.DamageType.NORMAL);
    }

    public void update() {

        this.duration = 0F;
        if (this.numTimes >= 1 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {

            --this.numTimes;
            AbstractMonster randomMonster = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng);

            if(randomMonster != null) {
                helicopterDamage.applyPowers(AbstractDungeon.player, randomMonster);

                AbstractDungeon.actionManager.addToTop(new MariTheHelicopterAction(this.numTimes, this.damage));
                AbstractDungeon.actionManager.addToTop(new DamageAction(randomMonster, helicopterDamage, AbstractGameAction.AttackEffect.BLUNT_LIGHT, true));
            }else{
                endAnimation();
            }
        }else{
            endAnimation();
        }
        this.isDone = true;

    }

    public void endAnimation(){
        for(AbstractGameEffect e : AbstractDungeon.effectList){
            if(e instanceof MariHelicopterEffect){
                ((MariHelicopterEffect) e).endAnimation();
            }
        }
    }
}
