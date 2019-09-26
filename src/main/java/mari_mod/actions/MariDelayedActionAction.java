package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariDelayedActionAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariDelayedActionAction.class.getName());
    private static final float DURATION = 0.01F;
    private AbstractGameAction action;

    public MariDelayedActionAction(AbstractGameAction action) {
        this.actionType = ActionType.SPECIAL;
        this.duration = 0.01F;
        this.action = action;
    }

    public void update() {

        AbstractDungeon.actionManager.addToBottom(this.action);
        this.isDone = true;
    }
}
