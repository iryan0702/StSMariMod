package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariDelayedDelayedActionActionAction extends AbstractGameAction { //TODO: WTF AM I DOING
    public static final Logger logger = LogManager.getLogger(MariDelayedDelayedActionActionAction.class.getName());
    private static final float DURATION = 0.01F;
    private AbstractGameAction action;

    public MariDelayedDelayedActionActionAction(AbstractGameAction action) {
        this.actionType = ActionType.SPECIAL;
        this.duration = 0.01F;
        this.action = action;
    }

    public void update() {

        AbstractDungeon.actionManager.addToBottom(new MariDelayedActionAction(this.action));
        this.isDone = true;
    }
}
