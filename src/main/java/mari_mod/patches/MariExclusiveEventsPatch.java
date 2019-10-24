package mari_mod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.beyond.Falling;
import com.megacrit.cardcrawl.events.city.MaskedBandits;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.events.exordium.ShiningLight;
import com.megacrit.cardcrawl.events.exordium.Sssserpent;
import com.megacrit.cardcrawl.random.Random;
import javassist.CtBehavior;
import mari_mod.events.MariFallingEvent;
import mari_mod.events.MariMaskedBandits;
import mari_mod.events.MariShiningLightEvent;
import mari_mod.events.MariSssserpent;

import java.util.ArrayList;

@SpirePatch(clz = AbstractDungeon.class, method = "getEvent")
public class MariExclusiveEventsPatch {

    @SpireInsertPatch(locator = Locator.class, localvars = {"tmp"})
    public static void Insert(Random rng, ArrayList<String> tmp) {
        if (AbstractDungeon.player.chosenClass == PlayerClassEnum.MARI) {
            tmp.removeIf(d -> d.equals(Falling.ID));
            tmp.removeIf(d -> d.equals(MaskedBandits.ID));
            tmp.removeIf(d -> d.equals(ShiningLight.ID));
            tmp.removeIf(d -> d.equals(Sssserpent.ID));
        } else {
            tmp.removeIf(d -> d.equals(MariFallingEvent.ID));
            tmp.removeIf(d -> d.equals(MariMaskedBandits.ID));
            tmp.removeIf(d -> d.equals(MariShiningLightEvent.ID));
            tmp.removeIf(d -> d.equals(MariSssserpent.ID));
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