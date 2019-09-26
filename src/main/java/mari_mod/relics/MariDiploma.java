package mari_mod.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import mari_mod.MariMod;
import mari_mod.patches.CardColorEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariDiploma extends AbstractMariRelic
{
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public static final String ID = "MariMod:MariDiploma";
    public MariDiploma()
    {
        super(ID, RelicTier.BOSS, LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }


    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
    }

    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
    }

    public AbstractRelic makeCopy()
    {
        return new MariDiploma();
    }
}
