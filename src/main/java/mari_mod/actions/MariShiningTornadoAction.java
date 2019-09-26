package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.vfx.combat.PotionBounceEffect;
import mari_mod.effects.MariShiningTornadoEffect;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.UUID;

public class MariShiningTornadoAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariShiningTornadoAction.class.getName());
    private static final float DURATION = 0.01F;
    private static final float POST_ATTACK_WAIT_DUR = 0.1F;
    private DamageInfo tornadoDamage;
    private int damage;
    private int numTimes;
    private int amount;

    public MariShiningTornadoAction(AbstractCreature target, int amount, int numTimes, int damage) {
        this.target = target;
        this.actionType = ActionType.DEBUFF;
        this.duration = 0.01F;
        this.numTimes = numTimes;
        this.amount = amount;
        this.damage = damage;
        this.tornadoDamage = new DamageInfo(AbstractDungeon.player, this.damage, DamageInfo.DamageType.NORMAL);
    }

    public void update() {
        if (this.target == null) {
            this.isDone = true;
        } else if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
            this.isDone = true;
        } else {
            AbstractDungeon.actionManager.addToTop(new MariShiningTornadoBounceAction(this.target, this.amount, this.numTimes, this.damage));
            if (this.target.currentHealth > 0) {
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.target, AbstractDungeon.player, new Radiance_Power(this.target, this.amount), this.amount, true, AttackEffect.FIRE));
                AbstractDungeon.actionManager.addToTop(new DamageAction(this.target,tornadoDamage,AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                AbstractDungeon.actionManager.addToTop(new WaitAction(0.1F));
            }

            this.isDone = true;
        }
    }
}
