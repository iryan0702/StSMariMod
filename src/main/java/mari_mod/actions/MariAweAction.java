package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariAweAction extends AbstractGameAction {
    private DamageInfo info;
    public static final Logger logger = LogManager.getLogger(MariAweAction.class.getName());


    public MariAweAction() {
        this.actionType = ActionType.POWER;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;

        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1));
        AbstractDungeon.actionManager.addToTop(new GainEnergyAction(1));
        this.isDone = true;
    }
}
