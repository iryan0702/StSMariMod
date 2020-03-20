package mari_mod.powers;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.MariMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Indulgence_Power extends AbstractPower
{
    public static final String POWER_ID = "MariMod:Indulgence_Power";
    private static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public Indulgence_Power(AbstractCreature owner, int bufferAmt)
    {
        this.name = NAME;
        this.type = POWER_TYPE;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = bufferAmt;
        this.isTurnBased = false;
        this.description = DESCRIPTION[0] + this.amount + DESCRIPTION[1];
        this.priority = 4;
        MariMod.setPowerImages(this);
    }

    public void stackPower(int stackAmount)
    {
        logger.info("this stacks: " + stackAmount);
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }


    public void onAfterCardPlayed(AbstractCard card) {
        int cost = card.costForTurn;
        if(card.cost == -1) cost = card.energyOnUse;
        if(card.freeToPlayOnce) cost = 0;
        if(cost >= 3){
            this.flash();
            for(AbstractCard cardToReduce : AbstractDungeon.player.hand.group){
                for(int i = 0; i < this.amount; i++) {
                    if (cardToReduce.cost > 1 && cardToReduce != card) cardToReduce.modifyCostForCombat(-1);
                }
            }
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTION[0] + this.amount + DESCRIPTION[1];
    }
}
