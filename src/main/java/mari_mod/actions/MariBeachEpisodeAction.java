package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariBeachEpisodeAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariBeachEpisodeAction.class.getName());
    private AbstractPower powerToApply;
    public int goldPerBlock;

    public MariBeachEpisodeAction(AbstractCreature target, int goldPerBlock, int maxBlock) {
        this.target = target;
        this.actionType = ActionType.POWER;
        this.goldPerBlock = goldPerBlock;
        this.amount = maxBlock;
    }

    public void update() {
        if (!this.target.isDying && !this.target.isDead && this.target.currentBlock > 0) {
            int blockRemoved = Math.min(this.target.currentBlock, this.amount);
            this.target.loseBlock(blockRemoved, false);
            addToTop(new MariGainGoldAction(blockRemoved * goldPerBlock));
        }

        this.isDone = true;
    }
}
