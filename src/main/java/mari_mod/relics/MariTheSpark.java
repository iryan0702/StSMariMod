package mari_mod.relics;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import mari_mod.MariMod;
import mari_mod.cards.Mari_First_Light;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariTheSpark extends AbstractMariRelic
{
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public static final String ID = "MariMod:MariTheSpark";
    public static final int REWARD_COST = 10;
    public static RewardItem reward;
    public MariTheSpark()
    {
        super(ID, AbstractRelic.RelicTier.STARTER, AbstractRelic.LandingSound.CLINK);
        this.counter = REWARD_COST;
    }

    @Override
    public void atBattleStartPreDraw() {
        this.flash();
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(p, this));
        addToTop(new MakeTempCardInHandAction(new Mari_First_Light()));
    }

    public AbstractRelic makeCopy()
    {
        return new MariTheSpark();
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0] + this.counter + this.DESCRIPTIONS[1];
    }

}
