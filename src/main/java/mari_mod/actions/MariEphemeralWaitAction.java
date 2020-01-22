//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class MariEphemeralWaitAction extends AbstractGameAction {
    public MariEphemeralWaitAction(float setDur) {
        this.setValues((AbstractCreature)null, (AbstractCreature)null, 0);
        this.duration = setDur;

        this.actionType = ActionType.WAIT;
    }

    public void update() {
        this.tickDuration();
    }
}
