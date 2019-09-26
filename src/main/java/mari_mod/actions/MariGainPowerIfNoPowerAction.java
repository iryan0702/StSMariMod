package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariGainPowerIfNoPowerAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariGainPowerIfNoPowerAction.class.getName());
    private AbstractPower powerToApply;

    public MariGainPowerIfNoPowerAction(AbstractCreature target, AbstractPower powerToApply, int amount) {
        this.target = target;
        this.actionType = ActionType.POWER;
        this.amount = amount;
        this.powerToApply = powerToApply;
    }

    public void update() {
        if(!target.hasPower(powerToApply.ID)){
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.target, AbstractDungeon.player, this.powerToApply, this.amount));
        }
        this.isDone = true;
    }
}
