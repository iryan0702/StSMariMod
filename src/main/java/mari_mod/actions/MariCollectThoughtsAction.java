//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MariCollectThoughtsAction extends AbstractGameAction {
    public AbstractCard card;

    public MariCollectThoughtsAction() {
        this.duration = 0.5f;
    }

    public void update() {
        this.tickDuration();

        if(this.isDone) {
            this.card = MariRecallAction.recalledCard;
            if (this.card != null && AbstractDungeon.player.hand.contains(card)) {
                AbstractDungeon.player.hand.moveToDeck(card, true);
            }
        }
    }
}
