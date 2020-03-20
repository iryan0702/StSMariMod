package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariActionQueueBookmarkAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariActionQueueBookmarkAction.class.getName());

    public MariActionQueueBookmarkAction() {
    }

    public void update() {
        this.isDone = true;
    }
}
