package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.core.Settings.ACTION_DUR_FAST;

public class MariAdministratorAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private ArrayList<AbstractCard> cannotSwap = new ArrayList<>();
    public AbstractPlayer p;

    public MariAdministratorAction() {
        this.actionType = ActionType.CARD_MANIPULATION;// 18
        this.duration = ACTION_DUR_FAST;
        this.p = AbstractDungeon.player;
    }// 19

    public void update() {
        if (this.duration == ACTION_DUR_FAST) {// 24

            if(p.hand.size() > 0) {
                int firstValidCost = -1;
                boolean doSwap = false;
                for(AbstractCard c: p.hand.group){
                    if(c.cost < 0) cannotSwap.add(c);
                    else{
                        if(firstValidCost == -1){
                            firstValidCost = c.cost;
                        }else if(firstValidCost != c.cost){
                            doSwap = true;
                        }
                    }
                }

                if (p.hand.size() - cannotSwap.size() >= 2 && doSwap) {
                    p.hand.group.removeAll(this.cannotSwap);
                    AbstractDungeon.handCardSelectScreen.open(TEXT[0], 2, false, false, false, false, false);// 25
                }
            }
            this.tickDuration();// 27
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {// 32
                AbstractCard c0 = AbstractDungeon.handCardSelectScreen.selectedCards.group.get(0);
                AbstractCard c1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.get(1);

                int tempCost0 = calculateCost(c0);
                c0.freeToPlayOnce = false;
                c0.setCostForTurn(calculateCost(c1));
                c1.freeToPlayOnce = false;
                c1.setCostForTurn(tempCost0);

                p.hand.addToTop(c0);
                p.hand.addToTop(c1);
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;// 40

                for(AbstractCard c: cannotSwap){
                    p.hand.addToTop(c);
                }
            }

            this.tickDuration();// 43
        }
    }// 28 44

    public int calculateCost(AbstractCard c){
        if(c.freeToPlayOnce) return 0;
        else return Math.max(0,c.costForTurn);
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("MariAdministratorAction");// 13
        TEXT = uiStrings.TEXT;// 14
    }
}
