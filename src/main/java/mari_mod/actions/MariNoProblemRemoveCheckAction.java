package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import mari_mod.powers.No_Problem_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariNoProblemRemoveCheckAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariNoProblemRemoveCheckAction.class.getName());

    public MariNoProblemRemoveCheckAction() {
        this.actionType = ActionType.POWER;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        AbstractPower power = AbstractDungeon.player.getPower(No_Problem_Power.POWER_ID);
        AbstractPower vulnPower = AbstractDungeon.player.getPower(VulnerablePower.POWER_ID);
        if(power != null && vulnPower != null && vulnPower.amount > power.amount){
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, power));
        }
        this.isDone = true;
    }
}
