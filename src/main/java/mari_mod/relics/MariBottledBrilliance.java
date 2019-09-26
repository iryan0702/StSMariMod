package mari_mod.relics;

import basemod.abstracts.CustomBottleRelic;
import com.evacipated.cardcrawl.mod.stslib.relics.BetterOnSmithRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import mari_mod.MariMod;
import mari_mod.cards.Mari_Strike;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//
import java.util.function.Predicate;

public class MariBottledBrilliance extends AbstractMariRelic implements CustomBottleRelic
{
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public static final String ID = "MariMod:MariBottledBrilliance";
    public MariBottledBrilliance()
    {
        super(ID, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public Predicate<AbstractCard> isOnCard() {
        //some changes
        return null;
    }

    @Override
    public void onMasterDeckChange() {
        super.onMasterDeckChange();
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy()
    {
        return new MariBottledBrilliance();
    }
}
