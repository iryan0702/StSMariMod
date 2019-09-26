package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariCardApplyPowersAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariCardApplyPowersAction.class.getName());
    public AbstractCard card;

    public MariCardApplyPowersAction(AbstractCard card, AbstractMonster target) {
        this.target = target;
        this.card = card;
    }

    public void update() {
        this.card.applyPowers();
        this.card.calculateCardDamage((AbstractMonster) this.target);
        this.isDone = true;
    }
}
