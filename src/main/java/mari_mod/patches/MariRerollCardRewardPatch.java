/*
package mari_mod.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import javassist.CtBehavior;
import mari_mod.ui.RerollCardRewardButton;


public class MariRerollCardRewardPatch {

    public static RerollCardRewardButton button;
    public static boolean isReward = false;
    public static RewardItem mostRecentReward;

    @SpirePatch(clz = CardRewardScreen.class, method = "cardSelectUpdate")
    public static class updateButton {

        @SpirePostfixPatch
        public static void Postfix(CardRewardScreen screen) {
            if(button == null){
                button = new RerollCardRewardButton();
            }
            if (isReward && AbstractDungeon.getCurrRoom() instanceof MonsterRoom) {
                button.show();
                button.update();
            }
        }
    }

    @SpirePatch(clz = CardRewardScreen.class, method = "render", paramtypez = SpriteBatch.class)
    public static class renderButton {

        @SpirePostfixPatch
        public static void Postfix(CardRewardScreen screen, SpriteBatch sb) {
            if (isReward && button != null && AbstractDungeon.getCurrRoom() instanceof MonsterRoom) {
                button.render(sb);
            }
        }
    }

    @SpirePatch(clz = CardRewardScreen.class, method = "placeCards", paramtypez = {float.class, float.class})
    public static class checkIfReward {

        @SpirePostfixPatch
        public static void Postfix(CardRewardScreen screen, float x, float y) {
            isReward = !(boolean)ReflectionHacks.getPrivate(screen, CardRewardScreen.class, "codex")
                    && !(boolean)ReflectionHacks.getPrivate(screen, CardRewardScreen.class, "discovery")
                    && !(boolean)ReflectionHacks.getPrivate(screen, CardRewardScreen.class, "chooseOne");
            System.out.println("IS REWARD CHECK: " + isReward + " CURR PHASE: " + AbstractDungeon.getCurrRoom().phase);
        }
    }

    @SpirePatch(
            clz = RewardItem.class,
            method = "claimReward"
    )
    public static class MariRewardLinker {

        @SpireInsertPatch(
                locator = EphemeralCardPatch.MariEphemeralCardRewardOpenCheck.Locator.class
        )
        public static void Insert(RewardItem __instance) {
            if(button != null) {
                button.updateTip();
            }

            mostRecentReward = __instance;
        }

        public static class Locator extends SpireInsertLocator {
            public Locator() {
            }
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(CardRewardScreen.class, "open");
                int[] found = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
                return new int[]{found[0]};
            }
        }
    }

    public static boolean isMostRecentRewardRerolled(){
        if(mostRecentReward != null) {
            return CardRewardLinkField.rerolled.get(mostRecentReward);
        }
        return true;
    }

    public static void setMostRecentRewardRerolled(boolean rerolled){
        CardRewardLinkField.rerolled.set(mostRecentReward, rerolled);
    }

    @SpirePatch(
            clz = RewardItem.class,
            method = SpirePatch.CLASS
    )
    public static class CardRewardLinkField {
        public static SpireField<Boolean> rerolled = new SpireField<>(() -> false);
    }
}
*/