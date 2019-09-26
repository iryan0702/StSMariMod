package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mari_mod.cards.MariCustomTags;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static basemod.BaseMod.MAX_HAND_SIZE;

public class MariMicCheckAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariMicCheckAction.class.getName());
    private static final float DURATION = 0.01F;

    public MariMicCheckAction() {
    }

    public void update() {
        Iterator var1 = AbstractDungeon.player.drawPile.group.iterator();

        AbstractCard cardToCheck;
        List<AbstractCard> cards = new ArrayList<>();
        while(var1.hasNext()) {
            cardToCheck = (AbstractCard)var1.next();
            if(cardToCheck.hasTag(MariCustomTags.QUOTATIONS)) {
                cards.add(cardToCheck);
            }
        }

        AbstractPlayer p = AbstractDungeon.player;

        for(AbstractCard cardToDraw : cards) {
            if (p.hand.size() == MAX_HAND_SIZE) {
                p.drawPile.moveToDiscardPile(cardToDraw);
                p.createHandIsFullDialog();
            } else {
                p.drawPile.removeCard(cardToDraw);
                p.hand.addToTop(cardToDraw);
            }

            p.hand.refreshHandLayout();
            p.hand.applyPowers();
        }
        this.isDone = true;
    }
}
