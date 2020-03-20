package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import mari_mod.MariMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariSpendGoldAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariSpendGoldAction.class.getName());

    public MariSpendGoldAction(int spendAmount) {
        this.actionType = ActionType.SPECIAL;
        this.amount = spendAmount;
    }

    public void update() {
        MariMod.spendGold(this.amount);
        this.isDone = true;
    }
}
