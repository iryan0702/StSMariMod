package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import mari_mod.MariMod;
import mari_mod.effects.MariHelicopterEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariTheHelicopterAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariTheHelicopterAction.class.getName());
    private int maxDamage;
    private int totalDamage;
    public static final float MAX_DUR = 20f;
    public static final float TIME_PER_HIT = 0.1f;

    public MariTheHelicopterAction(int damage) {
        this.actionType = ActionType.DAMAGE;
        this.duration = MAX_DUR;
        this.maxDamage = damage;
        this.totalDamage = 0;
    }

    public void update() {

        if((MAX_DUR - this.duration)/TIME_PER_HIT > totalDamage) {
            if (totalDamage < maxDamage) {
                totalDamage++;
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    MariMod.instantDamageAction(m, new DamageInfo(AbstractDungeon.player, 1, DamageInfo.DamageType.NORMAL), AttackEffect.SLASH_HORIZONTAL);
                }
            } else {
                endAnimation();
                this.isDone = true;
            }
        }

        tickDuration();
    }

    public void endAnimation(){
        for(AbstractGameEffect e : AbstractDungeon.effectList){
            if(e instanceof MariHelicopterEffect){
                ((MariHelicopterEffect) e).endAnimation();
            }
        }
    }
}
