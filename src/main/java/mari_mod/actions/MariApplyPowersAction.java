package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariApplyPowersAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariApplyPowersAction.class.getName());
    public AbstractCard defianceCard;

    public MariApplyPowersAction(AbstractCard defianceCard) {
        this.actionType = ActionType.BLOCK;
        this.amount = defianceCard.block;
        this.defianceCard = defianceCard;
    }

    public void update() {
        this.defianceCard.applyPowers();
        this.isDone = true;
    }
}
