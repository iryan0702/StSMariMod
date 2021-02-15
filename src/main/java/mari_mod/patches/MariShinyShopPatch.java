package mari_mod.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.DungeonMapScreen;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import mari_mod.MariMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MariShinyShopPatch {
    public static final Logger logger = LogManager.getLogger(MariShinyShopPatch.class.getName());

    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "render"
    )
    public static class ShinyShopRenderPatch {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall a) throws CannotCompileException {
                    if (a.getClassName().equals(AbstractDungeon.CurrentScreen.class.getName()) && a.getMethodName().equals("name")) {
                        a.replace(
                "{" +
                            "if(" + AbstractDungeon.class.getName() +  ".screen == " + MariShinyShopPatch.class.getName() + ".SHINY_SHOP){" +
                                MariShinyShopPatch.class.getName() + ".render(sb);" +
                                "$_ = $proceed($$);" +
                            "}" +
                        "}");
                    }
                }
            };
        }
    }

    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "update"
    )
    public static class ShinyShopUpdatePatch {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall a) throws CannotCompileException {
                    if (a.getClassName().equals(AbstractDungeon.CurrentScreen.class.getName()) && a.getMethodName().equals("name")) {
                        a.replace(
                                "{" +
                                            "if(" + AbstractDungeon.class.getName() +  ".screen == " + MariShinyShopPatch.class.getName() + ".SHINY_SHOP){" +
                                                MariShinyShopPatch.class.getName() + ".update();" +
                                                "$_ = $proceed($$);" +
                                            "}" +
                                        "}");
                    }
                }
            };
        }
    }

    public static void update(){
        MariMod.shinyShopScreen.update();
    }

    public static void render(SpriteBatch sb){
        MariMod.shinyShopScreen.render(sb);
    }

    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "openPreviousScreen",
            paramtypez = {AbstractDungeon.CurrentScreen.class}
    )
    public static class ShinyShopReopenPatch {
        @SpirePrefixPatch
        public static void callReopen(AbstractDungeon.CurrentScreen screen){
            if(screen == SHINY_SHOP){
                MariMod.shinyShopScreen.reopen();
            }
        }
    }

    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "closeCurrentScreen"
    )
    public static class ShinyShopCloseScreenPatch {
        @SpireInsertPatch(locator = Locator1.class)
        public static void insert1() {
            if (AbstractDungeon.screen == SHINY_SHOP) {
                AbstractDungeon.overlayMenu.cancelButton.hide();// 2866
                ReflectionHacks.RStaticMethod m=ReflectionHacks.privateStaticMethod(AbstractDungeon.class, "genericScreenOverlayReset");
                m.invoke();
            }
        }

        private static class Locator1 extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractDungeon.class, "screen");
                return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[0]};
            }
        }
    }

    //////////////////
    //TOP PANEL LOGIC
    //////////////////

    //DECK VIEW BUTTON
    @SpirePatch(clz = TopPanel.class, method = "updateDeckViewButtonLogic")
    public static class DeckViewButtonUpdate {
        public static boolean wasHovered = false;

        @SpireInsertPatch(locator = Locator1.class)
        public static void insert1(TopPanel __instance) {
            if (AbstractDungeon.screen == SHINY_SHOP) {
                ReflectionHacks.setPrivate(__instance, TopPanel.class, "deckButtonDisabled", false);
                __instance.deckHb.update();

                if(__instance.deckHb.hovered && !wasHovered){
                    wasHovered = true;
                    __instance.deckHb.justHovered = true;
                }else if(__instance.deckHb.hovered){
                    __instance.deckHb.justHovered = false;
                }else{
                    wasHovered = false;
                }
            }
        }

        private static class Locator1 extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(InputHelper.class.getName(), "justClickedLeft");
                return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[0]};
            }
        }


        //Should patch to the line right after the master deck button is clicked
        @SpireInsertPatch(locator = Locator3.class)
        public static SpireReturn insert3(TopPanel __instance) {
            if(AbstractDungeon.screen == SHINY_SHOP) {

                AbstractDungeon.previousScreen = SHINY_SHOP;// 849
                AbstractDungeon.deckViewScreen.open();
                AbstractDungeon.dynamicBanner.hide();

                InputHelper.justClickedLeft = false;
                return SpireReturn.Return(null);

            }
            return SpireReturn.Continue();
        }

        private static class Locator3 extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractDungeon.CurrentScreen.class.getName(), "COMBAT_REWARD");
                return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[1]};
            }
        }
    }

    //MAP VIEW BUTTON
    @SpirePatch(clz = TopPanel.class, method = "updateMapButtonLogic")
    public static class MapViewButtonUpdate {
        public static boolean wasMapHovered = false;

        @SpireInsertPatch(locator = Locator1.class)
        public static void insert1(TopPanel __instance) {
            if (AbstractDungeon.screen == SHINY_SHOP) {
                ReflectionHacks.setPrivate(__instance, TopPanel.class, "mapButtonDisabled", false);
                __instance.mapHb.update();

                if(__instance.mapHb.hovered && !wasMapHovered){
                    wasMapHovered = true;
                    __instance.mapHb.justHovered = true;
                }else if(__instance.mapHb.hovered){
                    __instance.mapHb.justHovered = false;
                }else{
                    wasMapHovered = false;
                }
            }
        }

        private static class Locator1 extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(InputHelper.class.getName(), "justClickedLeft");
                return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[0]};
            }
        }


        //Should patch to the line right after the map button is clicked
        @SpireInsertPatch(locator = Locator3.class)
        public static SpireReturn insert3(TopPanel __instance) {
            if(AbstractDungeon.screen == SHINY_SHOP) {

                AbstractDungeon.previousScreen = SHINY_SHOP;// 849
                AbstractDungeon.dungeonMapScreen.open(false);
                AbstractDungeon.dynamicBanner.hide();

                InputHelper.justClickedLeft = false;
                return SpireReturn.Return(null);

            }
            return SpireReturn.Continue();
        }

        private static class Locator3 extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(DungeonMapScreen.class.getName(), "dismissable");
                return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[0]};
            }
        }
    }

    //SETTINGS BUTTON
    @SpirePatch(clz = TopPanel.class, method = "updateSettingsButtonLogic")
    public static class SettingsButtonUpdate {
        //Should patch to the line right after the settings button is clicked
        @SpireInsertPatch(locator = Locator3.class)
        public static SpireReturn insert3(TopPanel __instance) {
            if(AbstractDungeon.screen == SHINY_SHOP) {

                AbstractDungeon.previousScreen = SHINY_SHOP;// 849
                AbstractDungeon.settingsScreen.open();
                AbstractDungeon.dynamicBanner.hide();

                InputHelper.justClickedLeft = false;
                return SpireReturn.Return(null);

            }
            return SpireReturn.Continue();
        }

        private static class Locator3 extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractDungeon.CurrentScreen.class.getName(), "DOOR_UNLOCK");
                return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[0]};
            }
        }
    }


    @SpireEnum
    public static AbstractDungeon.CurrentScreen SHINY_SHOP;
}