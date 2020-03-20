package mari_mod.actions;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import mari_mod.effects.MariShowCardAndAddToExhaustEffect;

public class MariMakeTempCardInExhaustPileAction extends AbstractGameAction {
    private AbstractCard cardToMake;
    private boolean top;
    private boolean bot;

    public MariMakeTempCardInExhaustPileAction(AbstractCard card, int amount, boolean top, boolean bot) {
        UnlockTracker.markCardAsSeen(card.cardID);
        this.setValues(this.target, this.source, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startDuration = Settings.FAST_MODE ? Settings.ACTION_DUR_FAST : 0.5F;
        this.duration = this.startDuration;
        this.cardToMake = card;
        this.top = top;
        this.bot = bot;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            AbstractCard c;
            for(int i = 0; i < this.amount; ++i) {
                c = this.cardToMake.makeStatEquivalentCopy();
                if (c.type != CardType.CURSE && c.type != CardType.STATUS && AbstractDungeon.player.hasPower("MasterRealityPower")) {
                    c.upgrade();
                }

                AbstractDungeon.effectList.add(new MariShowCardAndAddToExhaustEffect(c, this.top, this.bot));
            }

            this.duration -= Gdx.graphics.getDeltaTime();
        }

        this.tickDuration();
    }
}
