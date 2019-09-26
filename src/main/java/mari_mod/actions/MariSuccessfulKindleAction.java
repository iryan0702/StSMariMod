package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mari_mod.MariMod;
import mari_mod.powers.Radiance_Power;
import mari_mod.powers.Research_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class MariSuccessfulKindleAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariSuccessfulKindleAction.class.getName());
    public AbstractCreature target;
    public AbstractGameAction action;

    public MariSuccessfulKindleAction(AbstractCreature target, AbstractGameAction action) {
        this.actionType = ActionType.SPECIAL;
        this.target = target;
        this.action = action;
    }

    public void update() {
        if(target.hasPower(Radiance_Power.POWER_ID) && target.getPower(Radiance_Power.POWER_ID).amount >= 1){
            AbstractDungeon.actionManager.addToTop(this.action);

            ///

            if(AbstractDungeon.player.hasPower(Research_Power.POWER_ID)) {
                CardGroup hand = AbstractDungeon.player.hand;
                ArrayList<AbstractCard> canHit = new ArrayList<>();
                for(AbstractCard c : hand.group){
                    if(c.cost > 0){
                        canHit.add(c);
                    }
                }
                if(canHit.size() > 0 && !this.target.isPlayer) {
                    AbstractCard randomCard = canHit.get(AbstractDungeon.cardRandomRng.random(0,canHit.size()-1));
                    AbstractDungeon.player.getPower(Research_Power.POWER_ID).flash();
                    AbstractDungeon.actionManager.addToTop(new ReduceCostAction(randomCard.uuid,AbstractDungeon.player.getPower(Research_Power.POWER_ID).amount));
                    randomCard.flash();
                }
            }

            ///

            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(target, AbstractDungeon.player, target.getPower(Radiance_Power.POWER_ID),1));
        }
        this.isDone = true;
    }
}
