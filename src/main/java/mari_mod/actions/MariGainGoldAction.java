package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mari_mod.MariMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariGainGoldAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariGainGoldAction.class.getName());

    public MariGainGoldAction(int gainAmount) {
        this.actionType = ActionType.SPECIAL;
        this.amount = gainAmount;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        MariMod.gainGold(this.amount);
        p.hand.applyPowers();
        p.hand.glowCheck();
        this.isDone = true;
    }
}
