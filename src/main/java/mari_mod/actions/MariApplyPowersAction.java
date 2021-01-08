package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariApplyPowersAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariApplyPowersAction.class.getName());
    public AbstractCard card;

    public MariApplyPowersAction(AbstractCard card) {
        this.actionType = ActionType.BLOCK;
        this.amount = card.block;
        this.card = card;
    }

    public void update() {
        this.card.applyPowers();
        this.isDone = true;
    }
}
