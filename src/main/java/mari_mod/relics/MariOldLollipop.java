package mari_mod.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import mari_mod.MariMod;
import mari_mod.actions.MariReduceMaxHPAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariOldLollipop extends AbstractMariRelic
{
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public static final String ID = "MariMod:MariOldLollipop";
    public MariOldLollipop()
    {
        super(ID, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if(info.type == DamageInfo.DamageType.NORMAL && !target.isPlayer) {
            super.onAttack(info, damageAmount, target);
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(target, this));
            AbstractDungeon.actionManager.addToTop(new MariReduceMaxHPAction(target, 5));
        }
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy()
    {
        return new MariOldLollipop();
    }
}
