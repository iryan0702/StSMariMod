package mari_mod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CtBehavior;

// Based on Kio's Towel.
@SpirePatch(
        clz = AbstractRoom.class,
        method = "update"
)
public class MariTheSparkPatch {

    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void Insert(AbstractRoom __instance) {
        /*AbstractRelic relic = AbstractDungeon.player.getRelic(MariTheSpark.ID);
        if(relic != null){
            relic.onTrigger();
        }*/
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