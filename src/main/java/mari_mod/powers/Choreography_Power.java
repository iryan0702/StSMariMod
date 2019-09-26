package mari_mod.powers;


import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.MariMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Choreography_Power extends TwoAmountPowerByKiooehtButIJustChangedItABitSoItShowsZeroAndIsADifferentColor
{
    public static final String POWER_ID = "MariMod:Choreography_Power";
    private static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    private int lastCost;
    //private boolean justApplied = false;
    public Choreography_Power(AbstractCreature owner, int bufferAmt)
    {
        this.name = NAME;
        this.type = POWER_TYPE;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = bufferAmt;
        this.isTurnBased = false;
        this.displayColor2 = Color.GOLD.cpy();
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
    public void onInitialApplication() {
        super.onInitialApplication();
        AbstractCard card = AbstractDungeon.actionManager.cardsPlayedThisTurn.get(AbstractDungeon.actionManager.cardsPlayedThisTurn.size()-1);
        int cost = card.costForTurn;
        if(card.cost == -1) cost = card.energyOnUse;
        if(card.freeToPlayOnce) cost = 0;
        this.lastCost = cost;
        this.amount2 = this.lastCost;
        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        this.lastCost = -9;
        this.amount2 = this.lastCost;
        updateDescription();
    }

    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        int cost = card.costForTurn;
        if(card.cost == -1) cost = card.energyOnUse;
        if(card.freeToPlayOnce) cost = 0;
        if(cost < this.lastCost){
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player,this.amount,false));
            this.flashWithoutSound();
        }
        this.lastCost = cost;
        this.amount2 = this.lastCost;
        updateDescription();
    }

    public void updateDescription() {
        String wholeDescription;
        if(this.amount == 1) {
            wholeDescription = DESCRIPTION[0];
        }else{
            wholeDescription = DESCRIPTION[1] + this.amount + DESCRIPTION[2];
        }
        if(this.lastCost == -9) {
            wholeDescription += DESCRIPTION[3];
        }else{
            wholeDescription += DESCRIPTION[4] + this.lastCost + DESCRIPTION[5];
        }
        this.description = wholeDescription;
    }
}
