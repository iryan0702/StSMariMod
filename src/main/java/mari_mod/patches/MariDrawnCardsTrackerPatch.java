package mari_mod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CtBehavior;
import mari_mod.relics.MariTheSpark;

import java.util.ArrayList;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "draw",
        paramtypez = {int.class}
)
public class MariDrawnCardsTrackerPatch {

    public static ArrayList<AbstractCard> drawnCards = new ArrayList<>();
    public static boolean tracking = false;

    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"c"}
    )
    public static void Insert(AbstractPlayer __instance, int numCards, AbstractCard c) {
        if(tracking){
            drawnCards.add(c);
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "hand");
            int[] found = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
            return new int[]{found[0]};
        }
    }

    public static void clearTracker(){
        drawnCards.clear();
    }

    public static void clearAndTrack(){
        drawnCards.clear();
        tracking = true;
    }
}