package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariUnsuccessfulKindleAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariUnsuccessfulKindleAction.class.getName());
    public AbstractCreature target;
    public AbstractGameAction action;

    public MariUnsuccessfulKindleAction(AbstractCreature target, AbstractGameAction action) {
        this.actionType = ActionType.SPECIAL;
        this.target = target;
        this.action = action;
    }

    public void update() {
        if(!target.hasPower(Radiance_Power.POWER_ID) || (target.hasPower(Radiance_Power.POWER_ID) && target.getPower(Radiance_Power.POWER_ID).amount <= 0)){
            AbstractDungeon.actionManager.addToTop(this.action);
        }
        this.isDone = true;
    }
}
