package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class MariSlapAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariSlapAction.class.getName());
    public int scaling;

    public MariSlapAction(AbstractMonster target, int damageAmount, int scaling) {
        this.actionType = ActionType.DAMAGE;
        this.target = target;
        this.amount = damageAmount;
        this.scaling = scaling;
    }

    public void update() {
        int damageToDeal = this.amount;
        AbstractPlayer p = AbstractDungeon.player;
        if(p.hasPower(VulnerablePower.POWER_ID)) damageToDeal += this.scaling * p.getPower(VulnerablePower.POWER_ID).amount;
        AbstractDungeon.actionManager.addToTop(new DamageAction(this.target,new DamageInfo(p,damageToDeal, DamageInfo.DamageType.NORMAL),AttackEffect.BLUNT_HEAVY));
        this.isDone = true;
    }
}
