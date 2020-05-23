package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariShinyAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariShinyAction.class.getName());
    private static final float DURATION = 0.01F;
    public AbstractCreature target;
    public boolean targetAll;
    public boolean faded;

    public MariShinyAction(AbstractCreature target, boolean targetAll, boolean faded) {
        this.target = target;
        this.targetAll = targetAll;
        this.faded = faded;
    }

    public void update() {
        if(this.targetAll){
            for(AbstractMonster m: AbstractDungeon.getCurrRoom().monsters.monsters){
                if(m.hasPower(Radiance_Power.POWER_ID)) {
                    Radiance_Power radPower = (Radiance_Power) m.getPower(Radiance_Power.POWER_ID);
                    int current = this.faded ? Math.min(1,radPower.amount) : radPower.amount;
                    radPower.burstOfParticles(150);
                    AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, AbstractDungeon.player, new Radiance_Power(m, current), current));
                }
            }
            if(AbstractDungeon.player.hasPower(Radiance_Power.POWER_ID)) {
                Radiance_Power radPower = (Radiance_Power) AbstractDungeon.player.getPower(Radiance_Power.POWER_ID);
                int current = this.faded ? Math.min(1,radPower.amount) : radPower.amount;
                radPower.burstOfParticles(150);
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new Radiance_Power(AbstractDungeon.player, current), current));
            }
        }else if(this.target.hasPower(Radiance_Power.POWER_ID)) {
            Radiance_Power radPower = (Radiance_Power) this.target.getPower(Radiance_Power.POWER_ID);
            int current = this.faded ? Math.min(1,radPower.amount) : radPower.amount;
            radPower.burstOfParticles(150);
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.target, AbstractDungeon.player, new Radiance_Power(this.target, current), current));
        }
        this.isDone = true;
    }
}
