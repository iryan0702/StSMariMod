package mari_mod.relics;

import com.evacipated.cardcrawl.mod.stslib.relics.BetterOnLoseHpRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import mari_mod.MariMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariFlowerRing extends AbstractMariRelic implements BetterOnLoseHpRelic
{
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public static final String ID = "MariMod:MariFlowerRing";
    public static final int PREVENT_AMOUNT = 4;
    public boolean activated = false;
    public MariFlowerRing()
    {
        super(ID, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        this.beginLongPulse();
        activated = false;
    }

    @Override
    public int betterOnLoseHp(DamageInfo damageInfo, int i) {
        if(!this.activated) {
            this.activated = true;
            this.flash();
            this.stopPulse();
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            return Math.max(0, (i - PREVENT_AMOUNT));
        }
        return i;
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy()
    {
        return new MariFlowerRing();
    }
}
