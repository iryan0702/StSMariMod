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

public class MariSecondSeasonAction extends AbstractGameAction {
    private ArrayList<AbstractCard> list = new ArrayList<AbstractCard>();
    private AbstractPlayer p = AbstractDungeon.player;

    public MariSecondSeasonAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if(!p.exhaustPile.isEmpty()) {

            ArrayList<AbstractCard> cardsToMove = new ArrayList<>();
            for(AbstractCard cardToCheck: p.exhaustPile.group){
                if(cardToCheck.hasTag(MariCustomTags.RADIANCE)) {
                    cardsToMove.add(cardToCheck);
                }
            }
            for(AbstractCard cardToRetrieve: cardsToMove) {
                    cardToRetrieve.unfadeOut();
                    cardToRetrieve.freeToPlayOnce = true;
                    p.drawPile.addToRandomSpot(cardToRetrieve);

                    cardToRetrieve.fadingOut = false;
                    p.exhaustPile.removeCard(cardToRetrieve);

                    p.hand.refreshHandLayout();
                    p.hand.applyPowers();
            }
        }
        this.isDone = true;
    }
}
