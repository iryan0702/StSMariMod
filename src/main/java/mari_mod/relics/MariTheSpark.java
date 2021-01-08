package mari_mod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import mari_mod.MariMod;
import mari_mod.cards.AbstractMariCard;
import mari_mod.patches.CardColorEnum;
import mari_mod.patches.EphemeralCardPatch;
import mari_mod.powers.Intensity_Power;
import mari_mod.rewards.MariFadingReward;
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
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Intensity_Power(p, 3), 3));
        MariMod.gainGold(10);

        updateCost();
    }

    public void updateCost(){
        this.counter = 0;
        for(AbstractCard c : AbstractDungeon.player.masterDeck.group){
            if(EphemeralCardPatch.EphemeralField.ephemeral.get(c) && c instanceof AbstractMariCard && !((AbstractMariCard)c).faded){
                this.counter += 10;
            }
        }
        this.description = getUpdatedDescription();
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
                if (MariMod.saveableKeeper.brilliance >= this.counter) {
                    reward = new MariFadingReward(CardColorEnum.MARI);
                    AbstractDungeon.getCurrRoom().addCardReward(reward);
                    MariMod.saveableKeeper.brilliance -= this.counter;
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

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0] + this.counter + this.DESCRIPTIONS[1];
    }

}
