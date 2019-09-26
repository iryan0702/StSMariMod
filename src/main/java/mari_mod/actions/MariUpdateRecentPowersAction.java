package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mari_mod.MariMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariUpdateRecentPowersAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariUpdateRecentPowersAction.class.getName());
    private static final float DURATION = 0.01F;
    private static final float POST_ATTACK_WAIT_DUR = 0.1F;
    private DamageInfo tornadoDamage;
    private int damage;
    private int numTimes;
    private int amount;

    public MariUpdateRecentPowersAction() {
        this.actionType = ActionType.SPECIAL;
        this.duration = 0.01F;
    }

    public void update() {
        MariMod.recentPowers.clear();
        MariMod.recentPowers.addAll(AbstractDungeon.player.powers);
        this.isDone = true;
    }
}
