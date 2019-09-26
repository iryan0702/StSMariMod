//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mari_mod.cards.MariCustomTags;

import java.util.ArrayList;

public class MariSetCostOfALLCardsAction extends AbstractGameAction {
    private ArrayList<AbstractCard> list = new ArrayList<AbstractCard>();
    private AbstractPlayer p = AbstractDungeon.player;

    public MariSetCostOfALLCardsAction(int cost) {
        this.amount = cost;
    }

    public void update() {
        for(AbstractCard c: AbstractDungeon.player.drawPile.group){
            if(c.cost >= 0){
                c.cost = this.amount;
                c.costForTurn = this.amount;
            }
        }
        for(AbstractCard c: AbstractDungeon.player.hand.group){
            if(c.cost >= 0){
                c.cost = this.amount;
                c.costForTurn = this.amount;
            }
        }
        for(AbstractCard c: AbstractDungeon.player.exhaustPile.group){
            if(c.cost >= 0){
                c.cost = this.amount;
                c.costForTurn = this.amount;
            }
        }
        for(AbstractCard c: AbstractDungeon.player.discardPile.group){
            if(c.cost >= 0){
                c.cost = this.amount;
                c.costForTurn = this.amount;
            }
        }
        this.isDone = true;
    }
}
