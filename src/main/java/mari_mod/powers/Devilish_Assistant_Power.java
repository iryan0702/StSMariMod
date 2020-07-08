package mari_mod.powers;


import com.megacrit.cardcrawl.actions.defect.RecycleAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.MariMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Devilish_Assistant_Power extends AbstractPower {
    public static final String POWER_ID = "MariMod:Devilish_Assistant_Power";
    private static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());


    public Devilish_Assistant_Power(AbstractCreature owner) {
        this.name = NAME;
        this.type = POWER_TYPE;
        this.ID = POWER_ID;
        this.owner = owner;
        this.isTurnBased = false;
        this.priority = 6;
        this.updateDescription();
        MariMod.setPowerImages(this);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        addToBot(new RecycleAction());
    }

    public void updateDescription() {
        this.description = DESCRIPTION[0];
    }
}
