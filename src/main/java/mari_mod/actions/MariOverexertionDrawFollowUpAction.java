package mari_mod.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariOverexertionDrawFollowUpAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariOverexertionDrawFollowUpAction.class.getName());

    public MariOverexertionDrawFollowUpAction(int upTo) {
        this.duration = 0.001F;
        this.amount = upTo;
    }

    public void update() {
        this.tickDuration();
        AbstractPlayer p = AbstractDungeon.player;
        int usable = 0;
        for(AbstractCard c: p.hand.group){
            if(c.canUse(p, null)){
                usable++;
            }
        }

        if(usable < this.amount && p.hand.size() < BaseMod.MAX_HAND_SIZE){
            addToTop(new DrawCardAction(1, new MariOverexertionDrawFollowUpAction(this.amount)));
        }

    }
}
