package mari_mod.powers;


import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.MariMod;
import mari_mod.actions.MariRecallAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class No_Problem_Power extends AbstractPower
{
    public static final String POWER_ID = "MariMod:No_Problem_Power";
    private static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public AbstractCard card;
    public No_Problem_Power(AbstractCreature owner, int bufferAmt, AbstractCard triggerCard)
    {
        this.name = NAME;
        this.type = POWER_TYPE;
        this.ID = POWER_ID;
        this.amount = bufferAmt;
        this.owner = owner;
        this.isTurnBased = true;
        this.card = triggerCard;
        this.updateDescription();
        MariMod.setPowerImages(this);
        this.priority = 10;
    }

    public void stackPower(int stackAmount)
    {
        logger.info("this stacks: " + stackAmount);
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    @Override
    public void atStartOfTurnPostDraw() {
        for(int i = 0; i < this.amount; i++){
            addToBot(new MariRecallAction());
        }
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    public void updateDescription() {
        if(this.amount == 1){
            this.description = DESCRIPTION[0];
        }else{
            this.description = DESCRIPTION[1] + this.amount + DESCRIPTION[2];
        }
    }
}
