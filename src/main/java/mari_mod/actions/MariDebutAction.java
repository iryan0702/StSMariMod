package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariDebutAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariDebutAction.class.getName());
    boolean upgraded;

    public MariDebutAction(int applyAmount, boolean upgraded) {
        //this.target = target;
        this.upgraded = upgraded;
        this.amount = applyAmount;
        this.actionType = ActionType.POWER;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
    AbstractPower radiance = p.getPower(Radiance_Power.POWER_ID);
        int applyAmount = upgraded ? radiance.amount : this.amount;
        for(AbstractMonster m: AbstractDungeon.getCurrRoom().monsters.monsters) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, p, new Radiance_Power(m, applyAmount), applyAmount));
        }

        this.isDone = true;
    }
}
