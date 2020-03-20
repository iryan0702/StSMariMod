//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class MariSaveAllFollowingActionsExceptTheImmediateNextActionThenRestoreSavedActionsAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariSaveAllFollowingActionsExceptTheImmediateNextActionThenRestoreSavedActionsAction.class.getName());
    public ArrayList<AbstractGameAction> actions;
    public MariSaveAllFollowingActionsExceptTheImmediateNextActionThenRestoreSavedActionsAction(ArrayList<AbstractGameAction> actions) {
        this.actions = actions;
    }

    public void update() {

        logger.info("I AM " + this.getClass().getName());
        for(AbstractGameAction action: AbstractDungeon.actionManager.actions){
            logger.info("ACTIONS NOW: " + action.getClass().getName());
        }

        if(this.actions == null && AbstractDungeon.actionManager.actions.size() > 1){
            ArrayList<AbstractGameAction> actionsToRemember = new ArrayList<>();
            while(AbstractDungeon.actionManager.actions.size() > 1){
                actionsToRemember.add(AbstractDungeon.actionManager.actions.remove(1));
            }
            AbstractDungeon.actionManager.addToBottom(new MariSaveAllFollowingActionsExceptTheImmediateNextActionThenRestoreSavedActionsAction(actionsToRemember));

        }else if(this.actions != null && AbstractDungeon.actionManager.actions.size() > 0){
            AbstractDungeon.actionManager.addToBottom(new MariSaveAllFollowingActionsExceptTheImmediateNextActionThenRestoreSavedActionsAction(actions));
        }else if(this.actions != null && AbstractDungeon.actionManager.actions.size() == 0){
            for(AbstractGameAction action: actions){
                AbstractDungeon.actionManager.addToBottom(action);
            }
        }

        this.isDone = true;
    }
}
