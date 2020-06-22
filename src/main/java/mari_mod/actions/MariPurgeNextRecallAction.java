//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import mari_mod.effects.MariEphemeralExhaustEffect;

public class MariPurgeNextRecallAction extends AbstractGameAction {
    private float startingDuration;

    public MariPurgeNextRecallAction() {
        this.setValues(AbstractDungeon.player, AbstractDungeon.player, this.amount);
        this.actionType = ActionType.EXHAUST;
        this.startingDuration = 0.1f;
        this.duration = this.startingDuration;
    }

    public void update() {
        AbstractCard c = MariRecallAction.findRecallTarget();
        if (this.duration == this.startingDuration && c != null) {
            AbstractDungeon.player.exhaustPile.removeCard(c);

            c.current_x = Settings.WIDTH - AbstractCard.IMG_WIDTH * 0.7f;
            c.target_x = c.current_x;
            c.current_y = Settings.HEIGHT * 0.5f;
            c.target_y = c.current_y;
            c.drawScale = 0.7f;
            c.targetDrawScale = 0.7f;

            c.stopGlowing();
            c.unhover();
            c.unfadeOut();

            AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
        }

        this.tickDuration();
    }
}
