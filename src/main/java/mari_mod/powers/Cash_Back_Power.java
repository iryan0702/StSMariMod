package mari_mod.powers;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import mari_mod.MariMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Cash_Back_Power extends TwoAmountPowerByKiooehtButIJustChangedItABitSoItShowsZeroAndIsADifferentColor
{
    public static final String POWER_ID = "MariMod:Cash_Back_Power";
    private static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public static final int CASH_BACK_AMOUNT = 60;
    public Cash_Back_Power(AbstractCreature owner, int perTurn)
    {
        this.name = NAME;
        this.type = POWER_TYPE;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = perTurn;
        this.amount2 = perTurn;
        this.isTurnBased = false;
        this.canGoNegative = true;
        this.updateDescription();
        MariMod.setPowerImages(this);
    }


    public void stackPower(int stackAmount)
    {
        logger.info("this stacks: " + stackAmount);
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if(amount2 < 0) amount2 = 0;
        this.amount2 += stackAmount;
    }

    @Override
    public void atStartOfTurn() {
        this.amount2 = this.amount;
        super.atStartOfTurn();
    }

    @Override
    public void onSpecificTrigger() {
        int goldSpent = MariMod.lastGoldAmountSpent;
        if(this.amount2 > 0){
            AbstractDungeon.player.gainGold ((int)((float) goldSpent * CASH_BACK_AMOUNT * 0.01f));
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player,1));
            this.flash();
        }
        this.amount2--;
        if(amount2 == 0) amount2--;
    }

    @Override
    public void updateDescription() {
        if(this.amount == 1){
            this.description = DESCRIPTION[0];
        }else{
            this.description = DESCRIPTION[1] + this.amount + DESCRIPTION[2];
        }
        this.description += CASH_BACK_AMOUNT + DESCRIPTION[3];
    }
}
