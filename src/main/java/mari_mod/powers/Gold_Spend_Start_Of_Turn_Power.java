package mari_mod.powers;


import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.MariMod;
import mari_mod.actions.MariSpendGoldAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Gold_Spend_Start_Of_Turn_Power extends TwoAmountPowerByKiooehtButIJustChangedItABitSoItShowsZeroAndIsADifferentColor implements NonStackablePower
{
    public static final String POWER_ID = "MariMod:Gold_Spend_Start_Of_Turn_Power";
    private static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static PowerType POWER_TYPE = PowerType.DEBUFF;
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public Gold_Spend_Start_Of_Turn_Power(AbstractCreature owner, int turnAmt, int spendAmt)
    {
        this.name = NAME;
        this.type = POWER_TYPE;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount2 = spendAmt;
        this.amount = turnAmt;
        this.displayColor2 = Color.GOLD.cpy();
        this.isTurnBased = true;
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
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        this.flash();
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new MariSpendGoldAction(this.amount2));
        if(this.amount <= 1) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, this.ID));
        }else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        }
    }

    public void updateDescription() {
        if(this.amount == 1) {
            this.description = DESCRIPTION[0] + this.amount2 + DESCRIPTION[3];
        }else{
            this.description = DESCRIPTION[1] + this.amount + DESCRIPTION[2] + this.amount2 + DESCRIPTION[3];
        }
    }
}
