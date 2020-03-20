package mari_mod.powers;


import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.MariMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Debuff_Energy_Power extends AbstractPower
{
    public static final String POWER_ID = "MariMod:Debuff_Energy_Power";
    private static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());

    public boolean frailOnly;

    // -1 = FRAIL ONLY
    // -2 = VULN AND FRAIL

    public Debuff_Energy_Power(AbstractCreature owner, int startingStatus)
    {
        this.name = NAME;
        this.type = POWER_TYPE;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = startingStatus;
        this.isTurnBased = false;
        this.updateDescription();
        MariMod.setPowerImages(this);
        this.frailOnly = -1 == startingStatus;
        updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        if(stackAmount == -2){
            this.frailOnly = false;
            updateDescription();
        }
    }

    @Override
    public void updateDescription() {
        if(this.frailOnly) {
            this.description = DESCRIPTION[0];
        }else{
            this.description = DESCRIPTION[1];
        }
    }
}
