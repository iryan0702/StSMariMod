package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariApprovedAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariApprovedAction.class.getName());
    public int radiance;

    public MariApprovedAction(int radiance) {
        this.duration = 0.0F;
        this.actionType = ActionType.POWER;
        this.radiance = radiance;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        addToTop(new ApplyPowerAction(p, p, new Radiance_Power(p, this.radiance), this.radiance));
        if(p.currentBlock > 0) addToTop(new ApplyPowerAction(p, p, new Radiance_Power(p, this.radiance), this.radiance));
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            addToTop(new ApplyPowerAction(mo, p, new Radiance_Power(mo, this.radiance), this.radiance));
            if(mo.currentBlock > 0) addToTop(new ApplyPowerAction(mo, p, new Radiance_Power(mo, this.radiance), this.radiance));
        }
        this.isDone = true;
    }
}
