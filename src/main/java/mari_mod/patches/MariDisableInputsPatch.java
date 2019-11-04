package mari_mod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.GameCursor;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.input.InputAction;
import com.megacrit.cardcrawl.helpers.input.InputHelper;


public class MariDisableInputsPatch
{
    public static boolean disableOn = false;
    @SpirePatch(
            clz= InputAction.class,
            method="isPressed"
    )
    @SpirePatch(
            clz=InputAction.class,
            method="isJustPressed"
    )
    @SpirePatch(
            clz= CInputAction.class,
            method="isPressed"
    )
    @SpirePatch(
            clz=CInputAction.class,
            method="isJustPressed"
    )
    public static class disableKeyboardAndController{
        @SpirePrefixPatch
        public static SpireReturn<Boolean> Prefix(InputAction __instance)
        {
            if(disableOn)
                return SpireReturn.Return(false);
            else
                return SpireReturn.Continue();
        }
        @SpirePrefixPatch
        public static SpireReturn<Boolean> Prefix(CInputAction __instance)
        {
            if(disableOn)
                return SpireReturn.Return(false);
            else
                return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz=InputHelper.class,
            method="updateFirst"
    )
    public static class disableMousePositionAndClick {
        @SpirePostfixPatch
        public static void Postfix() {
            if(disableOn){
                InputHelper.mX = -999;
                InputHelper.mY = -999;
                InputHelper.updateLast();
            }
        }
    }

    @SpirePatch(
            clz= GameCursor.class,
            method="render",
            paramtypez = {SpriteBatch.class}
    )
    public static class disableMouseRender {
        @SpirePrefixPatch
        public static SpireReturn<Boolean> Prefix(GameCursor __instance, SpriteBatch sb) {
            if(disableOn) return SpireReturn.Return(false);
            else return SpireReturn.Continue();
        }
    }
}