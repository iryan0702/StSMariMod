package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariTeaTimeAction extends AbstractGameAction {
    private DamageInfo info;
    public static final Logger logger = LogManager.getLogger(MariTeaTimeAction.class.getName());

    public MariTeaTimeAction(int stackReduction) {
        this.actionType = ActionType.DAMAGE;
        this.amount = stackReduction;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        for(AbstractPower powerToCheck : p.powers){
            if(powerToCheck.type == AbstractPower.PowerType.DEBUFF && (powerToCheck.canGoNegative || powerToCheck.amount >= 0)){
                if(powerToCheck.amount > 0){
                    AbstractDungeon.actionManager.addToTop(new ReducePowerAction(p,p,powerToCheck,this.amount));
                }else if(powerToCheck.amount < 0){
                    AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p,p,powerToCheck,this.amount));
                }
            }
        }
        this.isDone = true;
    }
}
