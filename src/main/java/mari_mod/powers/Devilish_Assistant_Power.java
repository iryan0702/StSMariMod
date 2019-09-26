package mari_mod.powers;


import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.MariMod;
import mari_mod.actions.MariDevilishAssistantAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;


public class Devilish_Assistant_Power extends AbstractPower {
    public static final String POWER_ID = "MariMod:Devilish_Assistant_Power";
    private static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    private AbstractMonster lastTarget;
    private int cardsPlayed = 99;
    public boolean oneCardLastTurn = false;
    private AbstractCard mostRecentCard = null;
    private int lastAmount;
    public ArrayList<AbstractCard> cardsToNotGetTriggeredBy;


    public Devilish_Assistant_Power(AbstractCreature owner, int bufferAmt) { //TODO: FIX LAZY REWORK
        this.name = NAME;
        this.type = POWER_TYPE;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = bufferAmt;
        this.isTurnBased = false;
        this.lastTarget = null;
        this.updateDescription();
        this.cardsToNotGetTriggeredBy = new ArrayList<>();
        MariMod.setPowerImages(this);
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    @Override
    public void onInitialApplication() {
        this.cardsPlayed = -1;
        this.lastAmount = 0;
    }

    @Override
    public void atEndOfTurn(boolean playerTurn) {
        if(this.cardsPlayed != -1) {
            this.oneCardLastTurn = true;
        }
    }

    @Override
    public void atStartOfTurnPostDraw() {
        this.cardsPlayed = -1;
        cardsToNotGetTriggeredBy.clear();
        if(this.oneCardLastTurn) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new MariDevilishAssistantAction(mostRecentCard, this.lastAmount, this.lastTarget, this));
        }
        this.oneCardLastTurn = false;
    }

    public void setCardsPlayed(int cardsPlayed) {
        this.cardsPlayed = cardsPlayed;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(!cardsToNotGetTriggeredBy.contains(card)) {
            this.lastAmount = this.amount;
            this.lastTarget = (AbstractMonster) action.target;
            super.onUseCard(card, action);
            this.mostRecentCard = card;
            this.cardsPlayed++;
        }
    }

    public void updateDescription() {
        if(this.amount == 1) {
            this.description = DESCRIPTION[0];
        }else{
            this.description = DESCRIPTION[1] + this.amount + DESCRIPTION[2];
        }
    }
}
