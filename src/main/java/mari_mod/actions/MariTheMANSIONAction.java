package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariTheMANSIONAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariTheMANSIONAction.class.getName());
    int extraBlockPerDebuff;

    public MariTheMANSIONAction(int blockAmount, int extraBlockPerDebuff) {
        this.actionType = ActionType.DAMAGE;
        this.amount = blockAmount;
        this.extraBlockPerDebuff = extraBlockPerDebuff;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        int extraBlock = 0;
        for(AbstractPower powerToCheck : p.powers) {
            if (powerToCheck.type == AbstractPower.PowerType.DEBUFF) {
                //AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, powerToCheck));
                extraBlock += extraBlockPerDebuff;
            }
        }
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p,p,this.amount + extraBlock));
        this.isDone = true;
    }
}
