package mari_mod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CtBehavior;
import mari_mod.MariMod;


public class MariQueueEnemyAttackHookPatch {
    @SpirePatch(
            clz = GameActionManager.class,
            method = "getNextAction"
    )
    //IMPORTANT: TRIGGERS EVEN IF ENEMY TURN WAS SKIPPED
    public static class MariQueueEnemyAttackPatch {

        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert(GameActionManager __instance) {
            MariMod.afterPlayerBeforeEnemyTurn();
        }

        public static class Locator extends SpireInsertLocator {
            public Locator() {
            }
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractRoom.class, "skipMonsterTurn");
                int[] found = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
                return new int[]{found[0]};
            }
        }
    }
}