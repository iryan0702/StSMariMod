//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.cards.AbstractMariCard;
import mari_mod.powers.Radiance_Power;

import java.util.Iterator;

public class MariJarOfLightAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private float startingDuration;

    public MariJarOfLightAction(int numCards) {
        this.amount = numCards;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
    }

    public void update() {
        if (this.duration == this.startingDuration) {
            if (AbstractDungeon.player.drawPile.isEmpty()) {
                this.isDone = true;
                return;
            }

            CardGroup tmpGroup = new CardGroup(CardGroupType.UNSPECIFIED);

            for(int i = 0; i < AbstractDungeon.player.drawPile.size(); ++i) {
                AbstractCard c = (AbstractCard)AbstractDungeon.player.drawPile.group.get(i);
                if(c instanceof AbstractMariCard && ((AbstractMariCard)c).radiance > 0) tmpGroup.addToRandomSpot(c);
            }

            if(tmpGroup.isEmpty()){
                this.isDone = true;
                return;
            }

            AbstractDungeon.gridSelectScreen.open(tmpGroup, this.amount, true, TEXT[0]);
        } else if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            Iterator var3 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

            while(var3.hasNext()) {
                AbstractMariCard c = (AbstractMariCard)var3.next();
                c.radiance--;
                c.stopGlowing();
            }

            AbstractPlayer p = AbstractDungeon.player;
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new Radiance_Power(mo, AbstractDungeon.gridSelectScreen.selectedCards.size()), AbstractDungeon.gridSelectScreen.selectedCards.size()));
            }
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Radiance_Power(p, AbstractDungeon.gridSelectScreen.selectedCards.size()), AbstractDungeon.gridSelectScreen.selectedCards.size()));

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }

        this.tickDuration();
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ReprogramAction");
        TEXT = uiStrings.TEXT;
    }
}
