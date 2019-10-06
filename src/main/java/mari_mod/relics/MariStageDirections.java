package mari_mod.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import mari_mod.MariMod;
import mari_mod.actions.MariIncreaseRadianceInHandAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//


public class MariStageDirections extends AbstractMariRelic
{
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public static final String ID = "MariMod:MariStageDirections";
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
