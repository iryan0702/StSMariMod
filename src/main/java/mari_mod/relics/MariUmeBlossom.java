package mari_mod.relics;

import com.evacipated.cardcrawl.mod.stslib.relics.BetterOnSmithRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import mari_mod.MariMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariUmeBlossom extends AbstractMariRelic implements BetterOnSmithRelic
{
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public static final String ID = "MariMod:MariUmeBlossom";
    public MariUmeBlossom()
    {
        super(ID, RelicTier.SPECIAL, LandingSound.CLINK);
    }


    @Override
    public void betterOnSmith(AbstractCard abstractCard) {
        AbstractDungeon.topPanel.panelHealEffect();
        AbstractDungeon.player.maxHealth += 7;
        this.flash();
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy()
    {
        return new MariUmeBlossom();
    }
}
