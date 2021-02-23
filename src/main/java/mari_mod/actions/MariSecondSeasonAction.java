//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mari_mod.cards.AbstractMariCard;
import mari_mod.cards.MariCustomTags;

public class MariSecondSeasonAction extends AbstractGameAction {
    private AbstractPlayer p = AbstractDungeon.player;

    public MariSecondSeasonAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
//        replaceAndMoveToDrawFrom(p.exhaustPile);
//        replaceAndMoveToDrawFrom(p.drawPile);
//        replaceAndMoveToDrawFrom(p.discardPile);
//        replaceAndMoveToDrawFrom(p.hand);
//
//        p.hand.refreshHandLayout();
//        p.hand.applyPowers();
        CardGroup ep = p.exhaustPile;
        for(int i = 0; i < ep.size(); i++){
            AbstractCard c = ep.group.get(i);
            AbstractCard nc;
            if(c instanceof AbstractMariCard && c.hasTag(MariCustomTags.RADIANCE) && ((AbstractMariCard)c).faded){
                nc = c.makeCopy();
                ep.group.remove(i);
                ep.group.add(i,nc);
            }
        }

        this.isDone = true;
    }

//    public void replaceAndMoveToDrawFrom(CardGroup cardGroup){
//
//        ArrayList<AbstractCard> cardsToMove = new ArrayList<>();
//        for(AbstractCard cardToCheck: cardGroup.group){
//            if(cardToCheck instanceof AbstractMariCard && cardToCheck.hasTag(MariCustomTags.RADIANCE) && ((AbstractMariCard)cardToCheck).faded) {
//                cardsToMove.add(cardToCheck);
//            }
//        }
//        for(AbstractCard cardToRetrieve: cardsToMove) {
//            AbstractCard replacement = cardToRetrieve.makeCopy();
//
//            for (int i = 0; i < cardToRetrieve.timesUpgraded; i++) {
//                replacement.upgrade();
//            }
//
//            p.drawPile.addToRandomSpot(replacement);
//            cardGroup.removeCard(cardToRetrieve);
//
//        }
//
//    }
}
