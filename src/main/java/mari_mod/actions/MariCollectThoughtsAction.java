package mari_mod.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import mari_mod.MariMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class MariCollectThoughtsAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariCollectThoughtsAction.class.getName());
    private static final float DURATION = 0.01F;
    private boolean upgraded;

    public MariCollectThoughtsAction(boolean upgraded) {
        this.duration = DURATION;
        this.upgraded = upgraded;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        ArrayList<AbstractCard> cardCopies = new ArrayList<>();
        ArrayList<AbstractCard> cardsInHand = new ArrayList<>();
        for(AbstractCard cardToCopy : p.hand.group){
            cardCopies.add(0, cardToCopy.makeStatEquivalentCopy());
            cardsInHand.add(cardToCopy);
        }
        int handSize = AbstractDungeon.player.hand.size();
        for(AbstractCard cardToAdd : cardCopies){
            if(this.upgraded && cardToAdd.canUpgrade()) cardToAdd.upgrade();
            AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(cardToAdd, 1));
        }
        for(AbstractCard cardsToMove : cardsInHand){
            if(this.upgraded && cardsToMove.canUpgrade()) cardsToMove.upgrade();
            AbstractDungeon.player.hand.moveToDeck(cardsToMove,true);
        }

        this.isDone = true;
    }
}
