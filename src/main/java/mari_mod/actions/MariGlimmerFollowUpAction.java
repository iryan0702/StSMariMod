package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariGlimmerFollowUpAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariGlimmerFollowUpAction.class.getName());
    boolean upgraded;

    public MariGlimmerFollowUpAction(int amount) {
        this.actionType = ActionType.POWER;
        this.amount = amount;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractCard recalledCard = MariRecallAction.recalledCard;
        if(recalledCard != null){
            int amount = recalledCard.costForTurn;
            if(recalledCard.cost == -1) amount = recalledCard.energyOnUse;
            if(recalledCard.freeToPlayOnce) amount = 0;
            for(int i = 0; i < amount; i++) AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new Radiance_Power(p, this.amount), this.amount));
            for(AbstractMonster m: AbstractDungeon.getCurrRoom().monsters.monsters) {
                for(int i = 0; i < amount; i++) AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, p, new Radiance_Power(m, this.amount), this.amount));
            }
        }


        this.isDone = true;
    }
}
