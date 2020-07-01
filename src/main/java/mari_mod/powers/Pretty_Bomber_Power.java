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


public class Pretty_Bomber_Power extends AbstractPower
{
    public static final String POWER_ID = "MariMod:Pretty_Bomber_Power";
    private static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(Pretty_Bomber_Power.class.getName());
    public Pretty_Bomber_Power(AbstractCreature owner)
    {
        this.name = NAME;
        this.type = POWER_TYPE;
        this.ID = POWER_ID;
        this.owner = owner;
        this.isTurnBased = false;
        this.updateDescription();
        MariMod.setPowerImages(this);
    }

    public void onCardDraw(AbstractCard card) {
        if (card.cost >= 0) {
            int newCost = AbstractDungeon.cardRandomRng.random(card.cost);
            if (card.cost != newCost) {
                card.cost = newCost;
                card.costForTurn = card.cost;
                card.isCostModified = true;
            }

            card.freeToPlayOnce = false;
        }

    }


    @Override
    public void updateDescription() {
        this.description = DESCRIPTION[0];
    }
}
