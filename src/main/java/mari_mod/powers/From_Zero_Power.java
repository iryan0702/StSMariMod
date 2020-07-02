package mari_mod.powers;


import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.MariMod;
import mari_mod.actions.MariApplyRandomRadianceAction;
import mari_mod.cards.OnRecallCard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class From_Zero_Power extends AbstractPower implements NonStackablePower
{
    public static final String POWER_ID = "MariMod:From_Zero_Power";
    private static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public From_Zero_Power(AbstractCreature owner, int radianceAmt)
    {
        this.name = NAME;
        this.type = POWER_TYPE;
        this.ID = POWER_ID;
        this.owner = owner;
        this.isTurnBased = false;
        this.amount = radianceAmt;
        this.updateDescription();
        MariMod.setPowerImages(this);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        int cost = card.costForTurn;
        if(card.cost == -1) cost = card.energyOnUse;
        if(card.freeToPlayOnce) cost = 0;
        if(cost == 0){
            this.flashWithoutSound();
            action.exhaustCard = true;
            for (int i = 0; i < this.amount; i++) {
                AbstractDungeon.actionManager.addToBottom(new MariApplyRandomRadianceAction(1));
            }
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player,1));
        }
    }

    public void updateDescription() {
            this.description = DESCRIPTION[0] + 1 + DESCRIPTION[1] + DESCRIPTION[3] + this.amount + DESCRIPTION[4];
    }
}
