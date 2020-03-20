package mari_mod.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import javassist.CtBehavior;
import mari_mod.cards.AbstractMariCard;


public class MariKindleArrowPatch {
    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "drawCurvedLine",
            paramtypez = {SpriteBatch.class, Vector2.class, Vector2.class, Vector2.class}
    )
    public static class MariKindleArrowTailPatch {

        public static Color arrowColor = new Color(1f,0.85f,0.1f,1.0f);
        public static final float kindleTime = 0.4f;
        public static final int arrowNodes = 20;

        @SpireInsertPatch(
                locator = Locator.class, localvars = {"i"}
        )
        public static void Insert(AbstractPlayer __instance, SpriteBatch sb, Vector2 start, Vector2 end, Vector2 control, int i) {
            if(__instance.hoveredCard == AbstractMariCard.currentlyKindledCard && (arrowNodes - i) * kindleTime/arrowNodes < AbstractMariCard.kindleTimer){
                sb.setColor(arrowColor);
            }
        }

        public static class Locator extends SpireInsertLocator {
            public Locator() {
            }
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(Bezier.class, "quadratic");
                int[] found = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
                return new int[]{found[0]};
            }
        }
    }
}