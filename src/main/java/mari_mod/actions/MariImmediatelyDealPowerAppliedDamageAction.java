package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariImmediatelyDealPowerAppliedDamageAction extends AbstractGameAction {
    private DamageInfo info = null;
    private AbstractCard card = null;
    private AttackEffect attackeffect;
    public static final Logger logger = LogManager.getLogger(MariImmediatelyDealPowerAppliedDamageAction.class.getName());

    public MariImmediatelyDealPowerAppliedDamageAction(AbstractCreature target, DamageInfo info, AttackEffect attackeffect) {
        this.target = target;
        this.actionType = ActionType.DAMAGE;
        this.attackeffect = attackeffect;
        this.info = info;
    }

    public MariImmediatelyDealPowerAppliedDamageAction(AbstractCreature target, AbstractCard card, AttackEffect attackeffect) {
        this.target = target;
        this.actionType = ActionType.DAMAGE;
        this.attackeffect = attackeffect;
        this.card = card;
    }

    public void update() {
        if(info != null) {
            this.info.applyPowers(this.info.owner, this.target);
            AbstractDungeon.actionManager.addToTop(new DamageAction(this.target, this.info, attackeffect));
        }else if(card != null){
            if(this.target.isPlayer){
                this.card.calculateCardDamage(null);
            }else if(this.target instanceof AbstractMonster){
                this.card.calculateCardDamage((AbstractMonster) this.target);
            }
            AbstractDungeon.actionManager.addToTop(new DamageAction(this.target, new DamageInfo(AbstractDungeon.player, card.damage, card.damageTypeForTurn), attackeffect));
        }
        this.isDone = true;
    }
}
