package mari_mod.powers;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.MariMod;
import mari_mod.actions.DrawPileToHandByTagAction;
import mari_mod.actions.MariDelayedDamageRandomEnemyActionAction;
import mari_mod.cards.MariCustomTags;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Excitement_Power extends AbstractPower
{
    public static final String POWER_ID = "MariMod:Excitement_Power";
    private static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public float damageBoost;
    public Excitement_Power(AbstractCreature owner, int bufferAmt)
    {
        this.name = NAME;
        this.type = POWER_TYPE;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = bufferAmt;
        this.isTurnBased = false;
        this.updateDescription();
        this.damageBoost = 0.00F;
        this.priority = 6;
        MariMod.setPowerImages(this);
    }

    public void stackPower(int stackAmount)
    {
        logger.info("this stacks: " + stackAmount);
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if(power.ID.equals(Radiance_Power.POWER_ID)){
            AbstractDungeon.actionManager.addToBottom(new DrawPileToHandByTagAction(this.amount, MariCustomTags.RADIANCE));
        }
    }

    /*public float atDamageGive(float damage, DamageInfo.DamageType type) {
        damageBoost = 0.00F;
        if (this.owner.hasPower(Radiance_Power.POWER_ID)) damageBoost = 0.01F * this.amount * this.owner.getPower(Radiance_Power.POWER_ID).amount;
        this.updateDescription();
        return (type == DamageInfo.DamageType.NORMAL) ? damage * (float)(1.0F +(damageBoost)): damage;
    }*/

    public void updateDescription() {
        //this.description = DESCRIPTION[0] + this.amount + DESCRIPTION[1] + (int) (damageBoost*100F+0.1F) + DESCRIPTION[2];

        if(this.amount == 1) {
            this.description = DESCRIPTION[0];
        }else{
            this.description = DESCRIPTION[1] + this.amount + DESCRIPTION[2];
        }
    }
}
