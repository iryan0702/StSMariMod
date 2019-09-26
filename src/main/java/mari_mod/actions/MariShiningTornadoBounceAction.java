package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.effects.MariShiningTornadoEffect;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariShiningTornadoBounceAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariShiningTornadoBounceAction.class.getName());
    private static final float DURATION = 0.01F;
    private static final float POST_ATTACK_WAIT_DUR = 0.1F;
    private int damage;
    private int numTimes;
    private int amount;

    public MariShiningTornadoBounceAction(AbstractCreature target, int amount, int numTimes, int damage) {
        this.target = target;
        this.actionType = ActionType.DEBUFF;
        this.duration = 0.01F;
        this.numTimes = numTimes;
        this.amount = amount;
        this.damage = damage;
    }

    public void update() {
        if (this.numTimes > 1 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            //Jankiest code to make sure a monster that is about to die will not be targeted
            //Resolve order: new target exception > new target > damage old target > cycle to new target
            AbstractMonster currentTargetIsException = null;
            /*for(AbstractMonster monsters : AbstractDungeon.getMonsters().monsters) {
                if(monsters != this.target && !monsters.isDead) {
                    if (this.target.hasPower(Radiance_Power.POWER_ID)) {
                        if (this.target.currentHealth <= this.damage + this.target.getPower(Radiance_Power.POWER_ID).amount + amount) {
                            currentTargetIsException = (AbstractMonster) this.target;
                        }
                    } else {
                        if (this.target.currentHealth <= this.damage) {
                            currentTargetIsException = (AbstractMonster) this.target;
                        }
                    }
                }
            }*/


            --this.numTimes;
            AbstractMonster randomMonster = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)currentTargetIsException, true, AbstractDungeon.cardRandomRng);

            if(randomMonster != null) {
                AbstractDungeon.actionManager.addToTop(new MariShiningTornadoAction(randomMonster, this.amount, this.numTimes, this.damage));
                AbstractDungeon.actionManager.addToTop(new VFXAction(new MariShiningTornadoEffect(this.target.hb.cX, this.target.hb.cY, randomMonster.hb.cX, randomMonster.hb.cY), 0.4F));
            }
        }
        logger.info("finishing! (complete cycle) " + numTimes + " times left!");
        this.isDone = true;

    }
}
