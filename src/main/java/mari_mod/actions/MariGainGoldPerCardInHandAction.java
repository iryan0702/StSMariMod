package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariGainGoldPerCardInHandAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariGainGoldPerCardInHandAction.class.getName());
    private AbstractPower powerToApply;

    public MariGainGoldPerCardInHandAction(int goldPerCard) {
        this.amount = goldPerCard;
    }

    public void update() {
        addToTop(new MariGainGoldAction(this.amount * AbstractDungeon.player.hand.size()));
        this.isDone = true;
    }
}
