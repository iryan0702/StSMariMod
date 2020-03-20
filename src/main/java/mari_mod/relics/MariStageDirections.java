package mari_mod.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import mari_mod.MariMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//


public class MariStageDirections extends AbstractMariRelic
{
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public static final String ID = "MariMod:MariStageDirections";
    public static final float VULN_MULTIPLIER = 0.10f;
    public static final float FRAIL_MULTIPLIER = 0.05f;
    public MariStageDirections()
    {
        super(ID, RelicTier.STARTER, LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy()
    {
        return new MariStageDirections();
    }
}
