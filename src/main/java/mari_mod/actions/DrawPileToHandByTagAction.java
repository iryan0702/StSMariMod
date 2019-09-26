//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.actions;

import basemod.BaseMod;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mari_mod.cards.Mari_The_Light_We_Chase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;

public class DrawPileToHandByTagAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(DrawPileToHandByTagAction.class.getName());
    private AbstractPlayer p;
    private AbstractCard.CardTags tagToCheck;
    private static final int MAX = BaseMod.MAX_HAND_SIZE;

    public DrawPileToHandByTagAction(int amount, AbstractCard.CardTags tag) {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, this.p, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
        this.tagToCheck = tag;
    }

    public void update() {

        AbstractDungeon.actionManager.addToTop(new MariHandGlowCheckAction());

        if (this.duration == Settings.ACTION_DUR_MED) {
            if (this.p.drawPile.isEmpty()) {
                this.isDone = true;
                return;
            }
            logger.info("looking for radiance cards!");
            CardGroup tmp = new CardGroup(CardGroupType.UNSPECIFIED);
            Iterator var2 = this.p.drawPile.group.iterator();

            AbstractCard card;
            while(var2.hasNext()) {
                card = (AbstractCard)var2.next();
                if (card.hasTag(this.tagToCheck)) {
                    logger.info("found radiance cards!");
                    tmp.addToRandomSpot(card);
                }
            }

            if (tmp.size() == 0) {
                this.isDone = true;
                return;
            }

            for(int i = 0; i < this.amount; ++i) {
                if (!tmp.isEmpty()) {
                    tmp.shuffle();
                    card = tmp.getBottomCard();
                    tmp.removeCard(card);
                    if (this.p.hand.size() == MAX) {
                        this.p.drawPile.moveToDiscardPile(card);
                        this.p.createHandIsFullDialog();
                    } else {
                        card.unhover();
                        card.lighten(true);
                        card.setAngle(0.0F);
                        card.drawScale = 0.12F;
                        card.targetDrawScale = 0.75F;
                        card.current_x = CardGroup.DRAW_PILE_X;
                        card.current_y = CardGroup.DRAW_PILE_Y;
                        this.p.drawPile.removeCard(card);
                        AbstractDungeon.player.hand.addToTop(card);
                        AbstractDungeon.player.hand.refreshHandLayout();
                        AbstractDungeon.player.hand.applyPowers();
                    }
                }
            }

            this.isDone = true;
        }

        this.tickDuration();
    }
}
