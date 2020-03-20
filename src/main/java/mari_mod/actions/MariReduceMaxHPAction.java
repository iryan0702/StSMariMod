package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariReduceMaxHPAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariReduceMaxHPAction.class.getName());

    public MariReduceMaxHPAction(AbstractCreature target, int hpReduction) {
        this.target = target;
        this.amount = hpReduction;
        this.actionType = ActionType.DEBUFF;
    }

    public void update() {
        if(!target.isDead && !target.halfDead) {
            this.target.maxHealth -= this.amount;
            if (this.target.maxHealth <= 0) this.target.maxHealth = 1;
            if (this.target.currentHealth > this.target.maxHealth) this.target.currentHealth = this.target.maxHealth;
            this.target.healthBarUpdatedEvent();
        }
        this.isDone = true;
    }
}
