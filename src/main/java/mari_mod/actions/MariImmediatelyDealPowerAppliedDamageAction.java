package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariImmediatelyDealPowerAppliedDamageAction extends AbstractGameAction {
    private DamageInfo info;
    private AttackEffect attackeffect;
    public static final Logger logger = LogManager.getLogger(MariImmediatelyDealPowerAppliedDamageAction.class.getName());

    public MariImmediatelyDealPowerAppliedDamageAction(AbstractCreature target, DamageInfo info, AttackEffect attackeffect) {
        this.target = target;
        this.actionType = ActionType.DAMAGE;
        this.attackeffect = attackeffect;
        this.info = info;
    }

    public void update() {
        this.info.applyPowers(this.info.owner, this.target);
        AbstractDungeon.actionManager.addToTop(new DamageAction(this.target, this.info, attackeffect));
        this.isDone = true;
    }
}
