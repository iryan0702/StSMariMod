package mari_mod.patches;

import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.beyond.Falling;
import com.megacrit.cardcrawl.events.city.MaskedBandits;
import com.megacrit.cardcrawl.events.exordium.GoldenIdolEvent;
import com.megacrit.cardcrawl.events.exordium.ShiningLight;
import com.megacrit.cardcrawl.events.exordium.Sssserpent;
import com.megacrit.cardcrawl.random.Random;
import javassist.CtBehavior;
import mari_mod.events.*;

import java.util.ArrayList;

@Deprecated
//@SpirePatch(clz = AbstractDungeon.class, method = "getEvent")
public class MariExclusiveEventsPatch {

    //@SpireInsertPatch(locator = Locator.class, localvars = {"tmp"})
    public static void Insert(Random rng, ArrayList<String> tmp) {
        if (AbstractDungeon.player.chosenClass == PlayerClassEnum.MARI) {
            tmp.removeIf(d -> d.equals(Falling.ID));
            tmp.removeIf(d -> d.equals(MaskedBandits.ID));
            tmp.removeIf(d -> d.equals(ShiningLight.ID));
            tmp.removeIf(d -> d.equals(Sssserpent.ID));
            tmp.removeIf(d -> d.equals(GoldenIdolEvent.ID));
        } else {
            tmp.removeIf(d -> d.equals(MariFallingEvent.ID));
            tmp.removeIf(d -> d.equals(MariMaskedBandits.ID));
            tmp.removeIf(d -> d.equals(MariShiningLightEvent.ID));
            tmp.removeIf(d -> d.equals(MariSssserpent.ID));
            tmp.removeIf(d -> d.equals(MariGoldenIdolEvent.ID));
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(ArrayList.class, "isEmpty");
            return LineFinder.findInOrder(ctMethodToPatch, matcher);
        }
    }

}