package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import mari_mod.MariMod;
import mari_mod.cards.AbstractMariCard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariSpendGoldAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariSpendGoldAction.class.getName());
    private AbstractMariCard sourceCard;

    public MariSpendGoldAction(AbstractMariCard sourceCard) {
        this.actionType = ActionType.SPECIAL;
        this.sourceCard = sourceCard;
    }

    public void update() {
        if(this.sourceCard.goldCost > 0) {
            MariMod.spendGold(this.sourceCard.goldCost);
        }
        this.sourceCard.goldCost = 0;
        this.sourceCard.baseGoldCost = 0;
        this.isDone = true;
    }
}
