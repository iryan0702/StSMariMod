package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariDelayedDamageRandomEnemyActionAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariDelayedDamageRandomEnemyActionAction.class.getName());
    private static final float DURATION = 0.01F;
    private DamageInfo info;
    private AttackEffect effect;

    public MariDelayedDamageRandomEnemyActionAction(DamageInfo info, AttackEffect effect) {
        this.actionType = ActionType.SPECIAL;
        this.duration = 0.01F;
        this.info = info;
        this.effect = effect;
    }

    public void update() {

        AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(info, effect));
        this.isDone = true;
    }
}
