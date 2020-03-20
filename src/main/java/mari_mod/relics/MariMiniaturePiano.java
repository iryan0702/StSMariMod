package mari_mod.relics;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import mari_mod.MariMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariMiniaturePiano extends AbstractMariRelic
{
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public static final String ID = "MariMod:MariMiniaturePiano";
    public MariMiniaturePiano()
    {
        super(ID, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {

        int cost = card.costForTurn;
        if(card.cost == -1) cost = card.energyOnUse;
        if(card.freeToPlayOnce) cost = 0;

        if(cost >= 3) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
        }
        stopPulse();
    }

    public AbstractRelic makeCopy()
    {
        return new MariMiniaturePiano();
    }
}
