//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariAddNewActionsToSavedActionsAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariAddNewActionsToSavedActionsAction.class.getName());

    public MariAddNewActionsToSavedActionsAction() {
    }

    public void update() {

        logger.info("I AM " + this.getClass().getName());
        for(AbstractGameAction action: AbstractDungeon.actionManager.actions){
            logger.info("ACTIONS NOW: " + action.getClass().getName());
        }

        int bookmark = -1;
        MariSaveAllFollowingActionsExceptTheImmediateNextActionThenRestoreSavedActionsAction saveAction = null;
        for(int i = 0; i < AbstractDungeon.actionManager.actions.size(); i++){
            AbstractGameAction action = AbstractDungeon.actionManager.actions.get(i);
            if(saveAction == null && action instanceof MariSaveAllFollowingActionsExceptTheImmediateNextActionThenRestoreSavedActionsAction){
                saveAction = (MariSaveAllFollowingActionsExceptTheImmediateNextActionThenRestoreSavedActionsAction) action;
                bookmark = i + 1;
            }
        }

        if(saveAction != null && bookmark != -1) {
            while (AbstractDungeon.actionManager.actions.size() > bookmark) {
                logger.info("REMOVING ACTION: " + AbstractDungeon.actionManager.actions.get(bookmark));
                saveAction.actions.add(AbstractDungeon.actionManager.actions.remove(bookmark));
            }
        }

        logger.info("I AM (FINISHED TASK)" + this.getClass().getName());
        for(AbstractGameAction action: AbstractDungeon.actionManager.actions){
            logger.info("ACTIONS NOW: " + action.getClass().getName());
        }

        this.isDone = true;
    }
}
