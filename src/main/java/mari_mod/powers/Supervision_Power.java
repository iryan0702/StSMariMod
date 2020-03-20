package mari_mod.powers;


import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.MariMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Supervision_Power extends AbstractPower
{
    public static final String POWER_ID = "MariMod:Supervision_Power";
    private static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.DESCRIPTIONS;
    private static final int GOLD_COST = 20;
    private static final int ENERGY_GAIN = 3;
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public Supervision_Power(AbstractCreature owner, int bufferAmt)
    {
        this.name = NAME;
        this.type = POWER_TYPE;
        this.ID = POWER_ID;
        this.owner = owner;
        this.isTurnBased = false;
        this.amount = bufferAmt;
        this.updateDescription();
        MariMod.setPowerImages(this);
    }

    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.amount));
        this.flash();
    }

    /*@Override
    public void onAfterCardPlayed(AbstractCard card) {
        int cost = card.costForTurn;
        if(card.cost == -1) cost = card.energyOnUse;
        if(card.freeToPlayOnce) cost = 0;
        /*if(cost >= 3){
            if(AbstractDungeon.player.gold >= this.amount){
                this.flash();
                MariMod.spendGold(this.amount);
                AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(ENERGY_GAIN));
            }
        }
        if(AbstractDungeon.player.hasPower(Radiance_Power.POWER_ID) && AbstractDungeon.player.getPower(Radiance_Power.POWER_ID).amount >= cost){
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player,AbstractDungeon.player,AbstractDungeon.player.getPower(Radiance_Power.POWER_ID),cost));
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(cost));
        }
    }*/

    public void updateDescription() {
        this.description = DESCRIPTION[0] + this.amount + DESCRIPTION[1];
    }
}
