package mari_mod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import mari_mod.patches.CardColorEnum;
import mari_mod.rewards.MariUncommonReward;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import mari_mod.MariMod;

import static com.megacrit.cardcrawl.rewards.RewardItem.REWARD_ITEM_X;

public class MariTheSpark extends AbstractMariRelic implements ClickableRelic
{
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public static final String ID = "MariMod:MariTheSpark";
    public static final int REWARD_COST = 40;
    public static RewardItem reward;
    public MariTheSpark()
    {
        super(ID, AbstractRelic.RelicTier.STARTER, AbstractRelic.LandingSound.CLINK);
        this.counter = 0;
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0] + REWARD_COST + this.DESCRIPTIONS[1];
    }

    public void onSpendGold()
    {
        if(AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {

            flash();
            this.counter += MariMod.lastGoldAmountSpent;

        }
    }

    @Override
    public void atBattleStartPreDraw() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        MariMod.gainGold(10);
    }

    @Override
    public void onRightClick() {
        if(MariMod.saveableKeeper.goldInvested > REWARD_COST) {
            reward = new MariUncommonReward(CardColorEnum.MARI);
            AbstractDungeon.getCurrRoom().addCardReward(reward);
            //AbstractDungeon.combatRewardScreen.setupItemReward();
            AbstractDungeon.combatRewardScreen.positionRewards();
            MariMod.saveableKeeper.goldInvested -= REWARD_COST;
        }
    }

    /*@Override
    public void onTrigger() {
        if(!AbstractDungeon.getCurrRoom().smoked) {
            if (this.counter >= REWARD_COST) {
                this.flash();
                while (this.counter >= REWARD_COST) {
                    reward = new MariUncommonReward(CardColorEnum.MARI);
                    AbstractDungeon.getCurrRoom().addCardReward(reward);
                    this.counter -= REWARD_COST;
                }
                AbstractDungeon.combatRewardScreen.setupItemReward();
                AbstractDungeon.combatRewardScreen.positionRewards();
                AbstractDungeon.combatRewardScreen.update();
            }
        }
    }*/

    public AbstractRelic makeCopy()
    {
        return new MariTheSpark();
    }
}
