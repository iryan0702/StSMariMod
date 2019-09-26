package mari_mod.relics;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
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
    public boolean active = false;
    public MariMiniaturePiano()
    {
        super(ID, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        this.counter = 0;
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        this.beginLongPulse();
        active = true;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        ++this.counter;
        if (this.counter % 3 == 0) {
            this.counter = 0;
            if(active) {
                this.flash();
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
                active = false;
            }
            stopPulse();
        }
    }

    public void onVictory() {
        this.counter = -1;
    }

    public AbstractRelic makeCopy()
    {
        return new MariMiniaturePiano();
    }
}
