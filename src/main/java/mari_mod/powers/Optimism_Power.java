package mari_mod.powers;


import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.MariMod;
import mari_mod.cards.AbstractMariCard;
import mari_mod.cards.MariCustomTags;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Optimism_Power extends AbstractPower implements ModifyRadiancePower
{
    public static final String POWER_ID = "MariMod:Optimism_Power";
    private static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public Optimism_Power(AbstractCreature owner, int bufferAmt)
    {
        this.name = NAME;
        this.type = POWER_TYPE;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = bufferAmt;
        this.isTurnBased = false;
        this.updateDescription();
        this.priority = 6;
        MariMod.setPowerImages(this);
    }

    public void stackPower(int stackAmount)
    {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(card.hasTag(MariCustomTags.RADIANCE) && (!(card instanceof AbstractMariCard) || !((AbstractMariCard)card).faded)){
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));// 49
        }
    }

    @Override
    public float modifyRadiance(float radiance) {
        return radiance + this.amount;
    }

    public void updateDescription() {
        this.description = DESCRIPTION[0] + this.amount + DESCRIPTION[1];
    }
}
