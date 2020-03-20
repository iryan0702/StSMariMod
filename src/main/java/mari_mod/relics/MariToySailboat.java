package mari_mod.relics;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import mari_mod.MariMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariToySailboat extends AbstractMariRelic
{
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public static final String ID = "MariMod:MariToySailboat";
    public MariToySailboat()
    {
        super(ID, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        if(this.counter < 0){
            this.counter = 0;
        }
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.POWER) {
            ++this.counter;
            if (this.counter % 2 == 0) {
                this.counter = 0;
                this.flash();
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player,AbstractDungeon.player,7));
            }
        }

    }

    public AbstractRelic makeCopy()
    {
        return new MariToySailboat();
    }
}
