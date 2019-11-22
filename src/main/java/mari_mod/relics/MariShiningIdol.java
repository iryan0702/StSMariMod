package mari_mod.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import mari_mod.MariMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//


public class MariShiningIdol extends AbstractMariRelic
{
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public static final String ID = "MariMod:MariShiningIdol";
    public static final float STARTING_BOOST = 1.00f;
    public static final float BOOST_DECAY = 0.005f;
    public MariShiningIdol()
    {
        super(ID, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy()
    {
        return new MariShiningIdol();
    }
}
