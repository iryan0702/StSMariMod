package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariAllOrNothingAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariAllOrNothingAction.class.getName());
    private static final float DURATION = 0.01F;
    private boolean upgraded;
    private AbstractCard theCardItself;

    public MariAllOrNothingAction(boolean upgraded, AbstractCard theCardItself) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = 0.01F;
        this.upgraded = upgraded;
        this.theCardItself = theCardItself;
    }

    public void update() {
        for(AbstractCard cardToModify: AbstractDungeon.player.exhaustPile.group){
            if(cardToModify.costForTurn >= 0 && (cardToModify.costForTurn != 0 || !this.upgraded)){
                int newCost = AbstractDungeon.cardRandomRng.random(0,1)*3;
                if(cardToModify.cost != newCost) {
                    cardToModify.cost = newCost;
                    cardToModify.costForTurn = cardToModify.cost;
                    cardToModify.isCostModified = true;
                }
            }
        }
        for(AbstractCard cardToModify: AbstractDungeon.player.discardPile.group){
            if(cardToModify.costForTurn >= 0 && (cardToModify.costForTurn != 0 || !this.upgraded)){
                int newCost = AbstractDungeon.cardRandomRng.random(0,1)*3;
                if(cardToModify.cost != newCost) {
                    cardToModify.cost = newCost;
                    cardToModify.costForTurn = cardToModify.cost;
                    cardToModify.isCostModified = true;
                }
            }
        }
        for(AbstractCard cardToModify: AbstractDungeon.player.drawPile.group){
            if(cardToModify.costForTurn >= 0 && (cardToModify.costForTurn != 0 || !this.upgraded)){
                int newCost = AbstractDungeon.cardRandomRng.random(0,1)*3;
                if(cardToModify.cost != newCost) {
                    cardToModify.cost = newCost;
                    cardToModify.costForTurn = cardToModify.cost;
                    cardToModify.isCostModified = true;
                }
            }
        }
        for(AbstractCard cardToModify: AbstractDungeon.player.hand.group){
            if(cardToModify.costForTurn >= 0 && (cardToModify.costForTurn != 0 || !this.upgraded)){
                int newCost = AbstractDungeon.cardRandomRng.random(0,1)*3;
                if(cardToModify.cost != newCost) {
                    cardToModify.cost = newCost;
                    cardToModify.costForTurn = cardToModify.cost;
                    cardToModify.isCostModified = true;
                }
            }
        }
        if(theCardItself.costForTurn >= 0 && (theCardItself.costForTurn != 0 || !this.upgraded)){
            int newCost = AbstractDungeon.cardRandomRng.random(0,1)*3;
            if(theCardItself.cost != newCost) {
                theCardItself.cost = newCost;
                theCardItself.costForTurn = theCardItself.cost;
                theCardItself.isCostModified = true;
            }
        }
        AbstractDungeon.player.hand.applyPowers();
        AbstractDungeon.player.discardPile.applyPowers();
        AbstractDungeon.player.drawPile.applyPowers();
        AbstractDungeon.player.exhaustPile.applyPowers();
        this.isDone = true;
    }
}
