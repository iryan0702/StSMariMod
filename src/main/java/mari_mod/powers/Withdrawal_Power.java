package mari_mod.powers;


import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.MariMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Withdrawal_Power extends AbstractPower implements OnReceivePowerPower
{
    public static final String POWER_ID = "MariMod:Withdrawal_Power";
    private static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public Withdrawal_Power(AbstractCreature owner, int amount)
    {
        this.name = NAME;
        this.type = POWER_TYPE;
        this.ID = POWER_ID;
        this.owner = owner;
        this.isTurnBased = false;
        this.amount = amount;
        this.updateDescription();
        MariMod.setPowerImages(this);
    }

    public void stackPower(int stackAmount)
    {
        logger.info("this stacks: " + stackAmount);
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        this.updateDescription();
    }

    @Override
    public boolean onReceivePower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        AbstractPlayer p = AbstractDungeon.player;
        if(power.type == PowerType.DEBUFF && target.isPlayer){
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.amount, true));
        }
        return true;
    }

    public void updateDescription() {
        this.description = DESCRIPTION[0] + this.amount + DESCRIPTION[1];
    }
}
