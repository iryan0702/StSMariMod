package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mari_mod.cards.Mari_Heavy_Price;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariHeavyPriceAction extends AbstractGameAction {
    private DamageInfo info;
    public static final Logger logger = LogManager.getLogger(MariHeavyPriceAction.class.getName());

    public MariHeavyPriceAction(AbstractCreature target, DamageInfo info,  int multiplier) {
        this.target = target;
        this.actionType = ActionType.DAMAGE;
        this.amount = multiplier;
        this.info = info;
    }

    public void update() {
        int damage = 0;
        for(AbstractCard costCheckCard : AbstractDungeon.player.hand.group){
            if(costCheckCard.costForTurn > 0) damage += costCheckCard.costForTurn;
        }
        this.info.base += damage*this.amount;
        this.info.applyPowers(this.info.owner, this.target);
        AbstractDungeon.actionManager.addToTop(new DamageAction(this.target, this.info, AttackEffect.BLUNT_HEAVY));
        this.isDone = true;
    }
}
