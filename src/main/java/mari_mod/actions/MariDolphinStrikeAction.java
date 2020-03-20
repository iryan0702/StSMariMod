package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariDolphinStrikeAction extends AbstractGameAction {
    private DamageInfo info;
    public static final Logger logger = LogManager.getLogger(MariDolphinStrikeAction.class.getName());
    private AttackEffect attackEffect;

    public MariDolphinStrikeAction(AbstractCreature target, DamageInfo info, AttackEffect attackEffect) {
        this.actionType = ActionType.DAMAGE;
        this.target = target;
        this.attackEffect = attackEffect;
        this.info = info;
    }

    public void update() {
        AbstractDungeon.actionManager.addToTop(new DamageAction(this.target, this.info, attackEffect));
        if(this.target.hasPower(Radiance_Power.POWER_ID) && this.target.getPower(Radiance_Power.POWER_ID).amount >= 3){
            AbstractDungeon.actionManager.addToTop(new DamageAction(this.target, this.info, attackEffect));
        }
        this.isDone = true;
    }
}
