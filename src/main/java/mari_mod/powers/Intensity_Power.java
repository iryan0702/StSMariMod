package mari_mod.powers;


import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.MariMod;
import mari_mod.actions.MariRecallAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Intensity_Power extends AbstractPower
{
    public static final String POWER_ID = "MariMod:Intensity_Power";
    private static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(Intensity_Power.class.getName());
    public static final float BOOST_PER_STACK = 0.01f;
    public AbstractCard card;
    public Intensity_Power(AbstractCreature owner, int bufferAmt)
    {
        this.name = NAME;
        this.type = POWER_TYPE;
        this.ID = POWER_ID;
        this.amount = bufferAmt;
        this.owner = owner;
        this.isTurnBased = true;
        this.updateDescription();
        MariMod.setPowerImages(this);
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();

        AbstractPower radiancePower = AbstractDungeon.player.getPower(Radiance_Power.POWER_ID);
        if(radiancePower != null){
            radiancePower.updateDescription();
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);

        AbstractPower radiancePower = AbstractDungeon.player.getPower(Radiance_Power.POWER_ID);
        if(radiancePower != null){
            radiancePower.updateDescription();
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTION[0] + this.amount + DESCRIPTION[1];
    }
}
