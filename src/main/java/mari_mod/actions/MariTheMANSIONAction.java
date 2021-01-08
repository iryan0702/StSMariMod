package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariTheMANSIONAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariTheMANSIONAction.class.getName());

    public MariTheMANSIONAction() {
        this.actionType = ActionType.DAMAGE;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractPower power = p.getPower(FrailPower.POWER_ID);
        if(power != null && power.amount > 1){
            addToTop(new ReducePowerAction(p, p, power, power.amount - 1));
        }
        power = p.getPower(VulnerablePower.POWER_ID);
        if(power != null && power.amount > 1){
            addToTop(new ReducePowerAction(p, p, power, power.amount - 1));
        }
        power = p.getPower(WeakPower.POWER_ID);
        if(power != null && power.amount > 1){
            addToTop(new ReducePowerAction(p, p, power, power.amount - 1));
        }
        this.isDone = true;
    }
}
