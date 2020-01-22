package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.patches.MariDrawnCardsTrackerPatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariDrawnCardsTrackerAction extends AbstractGameAction {
    private DamageInfo info;
    public static final Logger logger = LogManager.getLogger(MariDrawnCardsTrackerAction.class.getName());

    boolean enable;
    boolean clear;

    public MariDrawnCardsTrackerAction(boolean enableTracker, boolean clearTracker) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.enable = enableTracker;
        this.clear = clearTracker;
    }

    public void update() {
        MariDrawnCardsTrackerPatch.tracking = enable;
        if(clear) MariDrawnCardsTrackerPatch.clearTracker();
        this.isDone = true;
    }
}
