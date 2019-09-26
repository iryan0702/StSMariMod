package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.MariMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class GoldGainNoBlockAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(GoldGainNoBlockAction.class.getName());

    public GoldGainNoBlockAction(int amount) {
        this.amount = amount;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        logger.info("YOUR BLOCK (in action): " + AbstractDungeon.player.currentBlock);
        boolean hasBlock = AbstractDungeon.player.currentBlock > 0;
        if (!hasBlock){
            MariMod.gainGold(this.amount);
        }
        AbstractDungeon.player.hand.applyPowers();
        AbstractDungeon.player.hand.glowCheck();

        this.isDone = true;
    }
}
