package mari_mod.powers;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.MariMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Practice_Outfit_Power extends AbstractPower

{
    public static final String POWER_ID = "MariMod:Practice_Outfit_Power";
    private static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.DESCRIPTIONS;
    public static final int RADIANCE = 1;
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public Practice_Outfit_Power(AbstractCreature owner)
    {
        this.name = NAME;
        this.type = POWER_TYPE;
        this.ID = POWER_ID;
        this.amount = -1;
        this.owner = owner;
        this.isTurnBased = true;
        this.updateDescription();
        MariMod.setPowerImages(this);
    }

    public void characterDamageBlocked(AbstractCreature creature){
        this.flashWithoutSound();
        addToBot(new ApplyPowerAction(creature, owner, new Radiance_Power(creature, 1), 1));
    }

    public void updateDescription() {
        this.description = DESCRIPTION[0];
    }
}
