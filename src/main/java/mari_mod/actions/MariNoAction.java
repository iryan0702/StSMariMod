package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariNoAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariNoAction.class.getName());
    private static final float DURATION = 0.01F;

    public MariNoAction() {
        //?????
        //?????
        //?????
        //?????
        //?????
    }

    public void update() {
        this.isDone = true;
    }
}
