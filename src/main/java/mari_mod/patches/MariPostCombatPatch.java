package mari_mod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CtBehavior;
import mari_mod.MariMod;
import mari_mod.cards.AbstractMariCard;
import mari_mod.rewards.MariFadingReward;

// Based on Kio's Towel.
@SpirePatch(
        clz = AbstractRoom.class,
        method = "update"
)
public class MariPostCombatPatch {

    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void Insert(AbstractRoom __instance) {
        System.out.println(AbstractDungeon.player.chosenClass);
        if(AbstractDungeon.player.chosenClass == AbstractPlayer.PlayerClass.valueOf(PlayerClassEnum.MARI.toString())) {
            int maxCards = 1;
            for (int i = 2; i <= AbstractDungeon.floorNum; i += i + 2) maxCards++;
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                if (EphemeralCardPatch.EphemeralField.ephemeral.get(c) && c instanceof AbstractMariCard && !((AbstractMariCard) c).faded) {
                    maxCards--;
                }
            }

            if (maxCards > 0) {
                RewardItem reward;
                reward = new MariFadingReward(CardColorEnum.MARI);
                AbstractDungeon.getCurrRoom().addCardReward(reward);
                AbstractDungeon.combatRewardScreen.setupItemReward();
                AbstractDungeon.combatRewardScreen.positionRewards();
                AbstractDungeon.combatRewardScreen.update();
            }

            // convert 10% of investedGold back into Gold
            if (MariMod.saveableKeeper.investedGold > 0) {
                int returns = MariMod.saveableKeeper.investedGold / 10;
                AbstractDungeon.player.gainGold(returns);
                MariMod.saveableKeeper.lifetimeInvestedGoldReturns += returns;
                MariMod.saveableKeeper.investedGold -= returns;
            }
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractDungeon.class, "loading_post_combat");
            int[] found = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
            return new int[]{found[found.length - 1]};
        }
    }
}