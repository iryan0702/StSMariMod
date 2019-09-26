package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariShutUpAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariShutUpAction.class.getName());
    private static final float DURATION = 0.01F;
    public AbstractMonster target;

    public MariShutUpAction(AbstractMonster target, int conversion) {
        this.target = target;
        this.amount = conversion;
    }

    public void update() {
        AbstractMonster m = this.target;
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, p, new StrengthPower(m, -this.amount), -this.amount));
        if (m != null && !m.hasPower(ArtifactPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, p, new GainStrengthPower(m, this.amount), this.amount));
        }
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, this.amount), this.amount));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new LoseStrengthPower(p, this.amount), this.amount));

        this.isDone = true;
    }
}
