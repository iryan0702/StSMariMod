package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import mari_mod.powers.No_Problem_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariNoProblemAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariNoProblemAction.class.getName());

    public MariNoProblemAction() {
        this.actionType = ActionType.POWER;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractPower power = p.getPower(VulnerablePower.POWER_ID);
        if(power != null) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new No_Problem_Power(p, power.amount), power.amount));
        }
        this.isDone = true;
    }
}
