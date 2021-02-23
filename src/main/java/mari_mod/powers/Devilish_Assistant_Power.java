package mari_mod.powers;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.MariMod;
import mari_mod.actions.MariDevilishAssistantAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Devilish_Assistant_Power extends AbstractPower {
    public static final String POWER_ID = "MariMod:Devilish_Assistant_Power";
    private static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public Color greenColor = Color.GREEN.cpy();
    public boolean improved;


    //improve > 0 = improve
    public Devilish_Assistant_Power(AbstractCreature owner, int improve) {
        this.name = NAME;
        this.type = POWER_TYPE;
        this.ID = POWER_ID;
        this.owner = owner;
        this.isTurnBased = false;
        this.priority = 0;
        if(improve > 0) {
            improved = true;
        }
        this.updateDescription();
        MariMod.setPowerImages(this);
    }

    @Override
    public void stackPower(int stackAmount) {
        if(stackAmount > 0) {
            improved = true;
        }
        this.updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        addToBot(new MariDevilishAssistantAction(improved));
    }

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        super.renderAmount(sb, x, y, c);
        if(improved){
            c = greenColor;
            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, "+", x, y + 15.0F * Settings.scale, this.fontScale, c);
        }
    }

    public void updateDescription() {
        if(improved) this.description = DESCRIPTION[1];
        else this.description = DESCRIPTION[0];
    }
}
