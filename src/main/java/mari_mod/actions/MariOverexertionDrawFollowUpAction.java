package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariOverexertionDrawFollowUpAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariOverexertionDrawFollowUpAction.class.getName());

    public MariOverexertionDrawFollowUpAction() {
        this.duration = 0.001F;
    }

    public void update() {
        AbstractDungeon.actionManager.addToTop(new WaitAction(0.4F));
        this.tickDuration();
        if (this.isDone) {

            for(AbstractCard c: DrawCardAction.drawnCards){
                if(c.costForTurn >= 1){
                    c.costForTurn = 1;
                }
            }
        }

    }
}
