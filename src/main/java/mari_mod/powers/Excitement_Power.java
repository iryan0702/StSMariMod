package mari_mod.powers;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.MariMod;
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
    public static final int REQUIRED_DRAW = 3;
    public Excitement_Power(AbstractCreature owner, int bufferAmt)
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
        logger.info("this stacks: " + stackAmount);
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        AbstractPlayer p = AbstractDungeon.player;
        super.onUseCard(card, action);
        int repeat = MariMod.calculateEffectiveCardCost(card) * this.amount;
        for(int i = 0; i < repeat; i++){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Radiance_Power(p, 1), 1));
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
