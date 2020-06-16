//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.UIStrings;
import mari_mod.cards.Mari_Stewshine;
import mari_mod.patches.CardColorEnum;

import java.util.Iterator;

public class MariHigherUpsAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    boolean upgraded;
    int goldCost;

    public MariHigherUpsAction(boolean upgraded, int goldCost) {
        this.upgraded = upgraded;
        this.p = AbstractDungeon.player;
        this.setValues(this.p, AbstractDungeon.player);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
        this.goldCost = goldCost;
    }

    public void update() {
        AbstractCard card;
        if (this.duration == Settings.ACTION_DUR_MED) {
            CardGroup tmp = new CardGroup(CardGroupType.UNSPECIFIED);
            Iterator var5 = CardLibrary.getAllCards().iterator();

            while(var5.hasNext()) {
                card = ((AbstractCard)var5.next()).makeCopy();
                if(card.color.equals(CardColorEnum.MARI) && !(card instanceof Mari_Stewshine) && card.cost * this.goldCost <= AbstractDungeon.player.gold) {
                    if(upgraded && card.canUpgrade() && !card.upgraded) card.upgrade();
                    tmp.addToRandomSpot(card);
                }
            }

            tmp.sortAlphabetically(true);
            tmp.sortByCost(false);
            tmp.sortByRarity(false);

            AbstractDungeon.gridSelectScreen.open(tmp, 1, TEXT[0], false);
            this.tickDuration();
        } else {
            if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
                Iterator var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

                while(var1.hasNext()) {
                    card = (AbstractCard)var1.next();
                    //AbstractDungeon.actionManager.addToTop(new MariSpendGoldAction(card.cost * this.goldCost)); TODO: DEPRECATED
                    card.costForTurn = 0;
                    card.unhover();
                    if (this.p.hand.size() == 10) {
                        this.p.drawPile.addToTop(card);
                        this.p.createHandIsFullDialog();
                    } else {
                        this.p.hand.addToTop(card);
                    }

                    this.p.hand.refreshHandLayout();
                    this.p.hand.applyPowers();
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                this.p.hand.refreshHandLayout();
            }

            this.tickDuration();
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("AnyCardFromDeckToHandAction");
        TEXT = uiStrings.TEXT;
    }
}
