package mari_mod.powers;


import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.MariMod;
import mari_mod.cards.Mari_Oh_My_God;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;


public class Oh_My_God_Power extends AbstractPower
{
    public static final String POWER_ID = "MariMod:Oh_My_God_Power";
    private static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    private boolean turnEnding;
    public Oh_My_God_Power(AbstractCreature owner, int bufferAmt)
    {
        this.name = NAME;
        this.type = POWER_TYPE;
        this.ID = POWER_ID;
        this.amount = bufferAmt;
        this.owner = owner;
        this.isTurnBased = true;
        this.updateDescription();
        MariMod.setPowerImages(this);
        this.priority = 10;
        this.turnEnding = false;
    }

    public void stackPower(int stackAmount)
    {
        logger.info("this stacks: " + stackAmount);
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if(isPlayer){
            this.turnEnding = true;
            this.onAfterCardPlayed(new Mari_Oh_My_God());
        }
    }

    @Override
    public void onAfterCardPlayed(AbstractCard usedCard) {

        AbstractPlayer p = AbstractDungeon.player;
        if(p.hasPower(this.ID) && this.turnEnding){
            if(this.amount <= 1) {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, this.ID));
            }else {
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
            }
        }

        ArrayList<AbstractCard> handCards = AbstractDungeon.player.hand.group;
        AbstractCard cardToPlay = handCards.get(AbstractDungeon.cardRandomRng.random(0,handCards.size()-1));
        cardToPlay.freeToPlayOnce = true;
        AbstractMonster randomMonster = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        AbstractDungeon.actionManager.addToBottom(new QueueCardAction(cardToPlay, randomMonster));
    }

    public void updateDescription() {
        this.description = DESCRIPTION[0];
    }
}
