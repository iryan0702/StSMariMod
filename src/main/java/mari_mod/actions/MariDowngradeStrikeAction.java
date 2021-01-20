package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import mari_mod.cards.Mari_Strike;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariDowngradeStrikeAction extends AbstractGameAction { //ONLY EQUIPPED FOR BASIC STRIKE
    private DamageInfo info;
    public static final Logger logger = LogManager.getLogger(MariDowngradeStrikeAction.class.getName());
    public Mari_Strike card;
    public boolean exhaust;

    public MariDowngradeStrikeAction(Mari_Strike card) {
        this.actionType = ActionType.SPECIAL;
        this.card = card;
    }

    public void update() {
        //card.downgrade();
        this.isDone = true;
    }
}
