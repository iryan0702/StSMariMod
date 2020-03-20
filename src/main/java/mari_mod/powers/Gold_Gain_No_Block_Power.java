package mari_mod.powers;


import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.MariMod;
import mari_mod.actions.GoldGainNoBlockAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Gold_Gain_No_Block_Power extends AbstractPower
{
    public static final String POWER_ID = "MariMod:Gold_Gain_No_Block_Power";
    private static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    private int lastCost;
    //private boolean justApplied = false;
    public Gold_Gain_No_Block_Power(AbstractCreature owner, int bufferAmt)
    {
        this.name = NAME;
        this.type = POWER_TYPE;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = bufferAmt;
        this.isTurnBased = false;
        this.updateDescription();
        MariMod.setPowerImages(this);
    }

    public void stackPower(int stackAmount)
    {
        logger.info("this stacks: " + stackAmount);
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        }

    @Override
    public void atEndOfRound() {
        AbstractDungeon.actionManager.addToTop(new GoldGainNoBlockAction(this.amount, AbstractDungeon.player.currentBlock == 0));
        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if(isPlayer) {
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTION[0] + this.amount + DESCRIPTION[1];
    }
}
