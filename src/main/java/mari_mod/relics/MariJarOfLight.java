package mari_mod.relics;

import basemod.abstracts.CustomBottleRelic;
import com.evacipated.cardcrawl.mod.stslib.relics.BetterOnSmithRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import mari_mod.MariMod;
import mari_mod.actions.MariJarOfLightAction;
import mari_mod.actions.MariIncreaseRadianceInHandAction;
import mari_mod.cards.AbstractMariCard;
import mari_mod.cards.Mari_Strike;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//
import java.util.function.Predicate;

public class MariJarOfLight extends AbstractMariRelic
{
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public static final String ID = "MariMod:MariJarOfLight";
    public MariJarOfLight()
    {
        super(ID, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public void atBattleStart() {
        AbstractDungeon.actionManager.addToBottom(new MariJarOfLightAction(3));
    }

    @Override
    protected void initializeTips() {
        super.initializeTips();
        this.tips.remove(tips.size()-1);
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy()
    {
        return new MariJarOfLight();
    }
}
