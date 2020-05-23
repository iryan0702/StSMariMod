package mari_mod.relics;

import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import mari_mod.MariMod;
import mari_mod.patches.CardColorEnum;
import mari_mod.rewards.MariFadingReward;
import mari_mod.rewards.MariUncommonReward;
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

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0] + REWARD_COST + this.DESCRIPTIONS[1];
    }

    @Override
    public void atBattleStartPreDraw() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        MariMod.gainGold(10);
    }



    /*@Override
    public void onRightClick() {
        if(MariMod.saveableKeeper.goldInvested > REWARD_COST) {
            reward = new MariUncommonReward(CardColorEnum.MARI);
            AbstractDungeon.getCurrRoom().addCardReward(reward);
            //AbstractDungeon.combatRewardScreen.setupItemReward();
            AbstractDungeon.combatRewardScreen.positionRewards();
            MariMod.saveableKeeper.goldInvested -= REWARD_COST;
        }
    }*/

    @Override
    public void onTrigger() {
        if(!AbstractDungeon.getCurrRoom().smoked) {
            if (MariMod.saveableKeeper.brilliance >= this.counter) {
                this.flash();
                while (MariMod.saveableKeeper.brilliance >= this.counter) {
                    reward = new MariFadingReward(CardColorEnum.MARI);
                    AbstractDungeon.getCurrRoom().addCardReward(reward);
                    MariMod.saveableKeeper.brilliance -= this.counter;
                    this.counter += REWARD_COST;
                }
                AbstractDungeon.combatRewardScreen.setupItemReward();
                AbstractDungeon.combatRewardScreen.positionRewards();
                AbstractDungeon.combatRewardScreen.update();
            }
        }
    }

    public AbstractRelic makeCopy()
    {
        return new MariTheSpark();
    }
}
