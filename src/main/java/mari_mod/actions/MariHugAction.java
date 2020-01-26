package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariHugAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariHugAction.class.getName());

    public MariHugAction(AbstractCreature target, int maxConvert) {
        this.target = target;
        this.amount = maxConvert;
        this.actionType = ActionType.DEBUFF;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player; //reworked but I was lazy to remove unnecessary code FUTURE EDIT: what's going on
        int yourFrail = -1;
        if(p.hasPower(FrailPower.POWER_ID)){
            yourFrail = 1;
        }

        if(yourFrail >= 0){
            //AbstractDungeon.actionManager.addToTop(new DrawCardAction(p, 1));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.target, p, new VulnerablePower(this.target, this.amount, false), this.amount));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.target, p, new WeakPower(this.target, this.amount, false), this.amount));
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(p, p, FrailPower.POWER_ID, yourFrail));
        }
        this.isDone = true;
    }
}
