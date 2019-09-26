package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class MariActionQueueBookmarkAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariActionQueueBookmarkAction.class.getName());

    public MariActionQueueBookmarkAction() {
    }

    public void update() {
        this.isDone = true;
    }
}
