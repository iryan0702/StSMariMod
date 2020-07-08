package mari_mod.powers;


import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.MariMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Higher_Ups_Power extends AbstractPower
{
    public static final String POWER_ID = "MariMod:Higher_Ups_Power";
    private static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public Higher_Ups_Power(AbstractCreature owner, int bufferAmt)
    {
        this.name = NAME;
        this.type = POWER_TYPE;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = bufferAmt;
        this.isTurnBased = false;
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
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if(!this.owner.hasPower(Higher_Ups_Turn_Power.POWER_ID)) {
            if (MariMod.calculateEffectiveCardCost(card) >= 3 && this.amount > 0) {
                GameActionManager.queueExtraCard(card, m);
                this.amount--;
                if (this.amount == 0) {
                    addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
                }
            }
        }
    }

    @Override
    public void updateDescription() {
        if(this.amount == 1){
            this.description = DESCRIPTION[0];
        }else {
            this.description = DESCRIPTION[1] + this.amount + DESCRIPTION[2];
        }
    }
}
