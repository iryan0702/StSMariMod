//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import mari_mod.effects.MariEphemeralExhaustEffect;

public class MariPurgeSpecificCardAction extends AbstractGameAction {
    private AbstractCard targetCard;
    private CardGroup group;
    private float startingDuration;
    private boolean inDiscard;

    public MariPurgeSpecificCardAction(AbstractCard targetCard, CardGroup group, boolean inDiscard) {
        this.targetCard = targetCard;
        this.setValues(AbstractDungeon.player, AbstractDungeon.player, this.amount);
        this.actionType = ActionType.EXHAUST;
        this.group = group;
        this.startingDuration = 0.1f;
        this.duration = this.startingDuration;
        this.inDiscard = inDiscard;
    }

    public void update() {
        if (this.duration == this.startingDuration && this.group.contains(this.targetCard)) {
            this.group.removeCard(this.targetCard);
            if(inDiscard){
                AbstractDungeon.effectList.add(new MariEphemeralExhaustEffect(true));
            }else {
                AbstractDungeon.effectList.add(new ExhaustCardEffect(this.targetCard));
            }
        }

        this.tickDuration();
    }
}
