package mari_mod.actions;

import basemod.BaseMod;
import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariOverexertionDrawFollowUpAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariOverexertionDrawFollowUpAction.class.getName());
    AbstractCard targetCard;

    public MariOverexertionDrawFollowUpAction(AbstractCard targetCard) {
        this.duration = 0.001F;
        this.targetCard = targetCard;
    }

    public void update() {
        if(DrawCardAction.drawnCards.size() > 0) {
            AbstractCard drawn = DrawCardAction.drawnCards.get(0);
            AbstractPlayer p = AbstractDungeon.player;

            if (!drawn.canUse(p, null)) {
                for (AbstractGameAction a : AbstractDungeon.actionManager.actions) {
                    if (a instanceof UseCardAction && ReflectionHacks.getPrivate(a, UseCardAction.class, "targetCard") == targetCard) {
                        if(p.hand.size() < BaseMod.MAX_HAND_SIZE) targetCard.returnToHand = true;
                    }
                }
            }
        }

        this.isDone = true;
    }
}
