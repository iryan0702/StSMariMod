package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariDelayIfOtherActionsAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariDelayIfOtherActionsAction.class.getName());
    private static final float DURATION = 0.01F;
    private static final float POST_ATTACK_WAIT_DUR = 0.1F;
    private AbstractGameAction action;
    private int amount;

    public MariDelayIfOtherActionsAction(AbstractGameAction action, int delay) {
        this.duration = 0.01F;
        this.action = action;
        this.amount = delay;
    }

    public void update() {

        boolean otherTypes = false;
        for(AbstractGameAction action : AbstractDungeon.actionManager.actions){
            if(!(action instanceof MariDelayIfOtherActionsAction)){
                otherTypes = true;
            }
        }
        if(!otherTypes) this.amount = 0;

        if(this.amount >= 1){
            AbstractDungeon.actionManager.addToBottom(new MariDelayIfOtherActionsAction(this.action, this.amount-1));
        }else{
            AbstractDungeon.actionManager.addToTop(this.action);
        }
        this.isDone = true;
    }
}
