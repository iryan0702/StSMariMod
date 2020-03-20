package mari_mod.powers;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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
    public int radianceGain;
    public Excitement_Power(AbstractCreature owner, int bufferAmt)
    {
        this.name = NAME;
        this.type = POWER_TYPE;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = 0;
        this.radianceGain = bufferAmt;
        this.isTurnBased = false;
        this.updateDescription();
        this.priority = 6;
        MariMod.setPowerImages(this);
    }

    public void stackPower(int stackAmount)
    {
        logger.info("this stacks: " + stackAmount);
        this.fontScale = 8.0F;
        this.radianceGain += stackAmount;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        this.amount = 0;
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        AbstractPlayer p = AbstractDungeon.player;
        super.onCardDraw(card);
        this.amount++;
        if(this.amount > REQUIRED_DRAW){
            for (int i = 0; i < this.radianceGain; i++) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Radiance_Power(p, 1), 1));
            }
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

        if(this.radianceGain == 1) {
            this.description = DESCRIPTION[0];
        }else{
            this.description = DESCRIPTION[1] + this.radianceGain + DESCRIPTION[2];
        }
    }
}
