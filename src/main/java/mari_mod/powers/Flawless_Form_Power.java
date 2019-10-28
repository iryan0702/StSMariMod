package mari_mod.powers;


import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.MariMod;
import mari_mod.actions.MariDelayedActionAction;
import mari_mod.actions.MariDelayedDelayedActionActionAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Flawless_Form_Power extends TwoAmountPowerByKiooehtButIJustChangedItABitSoItShowsZeroAndIsADifferentColor
{
    public static final String POWER_ID = "MariMod:Flawless_Form_Power";
    private static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    private int lastCost;
    private int amountPerTurn;
    //private boolean justApplied = false;
    public Flawless_Form_Power(AbstractCreature owner, int amount)
    {
        this.name = NAME;
        this.type = POWER_TYPE;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.amountPerTurn = amount;
        this.isTurnBased = true;
        this.displayColor2 = Color.PURPLE.cpy();
        this.updateDescription();
        MariMod.setPowerImages(this);
    }

    public void stackPower(int stackAmount)
    {
        logger.info("this stacks: " + stackAmount);
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        this.amountPerTurn += stackAmount;
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
        MariMod.previousCardCost = 9999;
        super.atStartOfTurnPostDraw();
        this.lastCost = 9999;
        this.amount2 = -1;
        this.amount = this.amountPerTurn;
        updateDescription();
    }

    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        int cost = card.costForTurn;
        if(card.cost == -1) cost = card.energyOnUse;
        if(card.freeToPlayOnce) cost = 0;
        if(cost > this.lastCost && this.amount > 0){
            this.amount--;
            AbstractDungeon.actionManager.addToBottom(new MariDelayedDelayedActionActionAction(new GainEnergyAction(cost)));
            this.flashWithoutSound();
        }
        this.lastCost = cost;
        this.amount2 = this.lastCost;
        updateDescription();
    }

    public void updateDescription() {
        String wholeDescription;
        wholeDescription = DESCRIPTION[0] + this.amountPerTurn + DESCRIPTION[1];
        if(this.lastCost == 9999) {
            wholeDescription += DESCRIPTION[2];
        }else{
            wholeDescription += DESCRIPTION[3] + this.lastCost + DESCRIPTION[4];
        }
        this.description = wholeDescription;
    }
}
