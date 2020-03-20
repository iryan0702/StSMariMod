package mari_mod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import javassist.CtBehavior;
import mari_mod.MariMod;


public class MariRenderDanceIndicatorPatch {
    @SpirePatch(
            clz = AbstractCard.class,
            method = "renderEnergy",
            paramtypez = {SpriteBatch.class}
    )
    public static class MariRenderBeforeCostPatch {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert(AbstractCard __instance, SpriteBatch sb) {
            if(AbstractDungeon.player != null && AbstractDungeon.player.hand.group.contains(__instance)) {
                int cost = __instance.costForTurn;
                if (__instance.cost == -1) cost = EnergyPanel.getCurrentEnergy();
                if (__instance.freeToPlayOnce) cost = 0;

                if (MariMod.choreographyAmount > 0 && cost < MariMod.previousCardCost && MariMod.previousCardCost != 9999) {
                    sb.draw(MariMod.choreographyFormIndicatorTexture, __instance.current_x - 256.0F, __instance.current_y - 256.0F, 256.0F, 256.0F, 512.0F, 512.0F, __instance.drawScale * Settings.scale, __instance.drawScale * Settings.scale, __instance.angle, 0, 0, 512, 512, false, false);
                }
                if (MariMod.flawlessFormAmount > 0 && cost > MariMod.previousCardCost && MariMod.previousCardCost != 9999) {
                    sb.draw(MariMod.flawlessFormIndicatorTexture, __instance.current_x - 256.0F, __instance.current_y - 256.0F, 256.0F, 256.0F, 512.0F, 512.0F, __instance.drawScale * Settings.scale, __instance.drawScale * Settings.scale, __instance.angle, 0, 0, 512, 512, false, false);
                }
            }
        }

        public static class Locator extends SpireInsertLocator {
            public Locator() {
            }
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "transparency");
                int[] found = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
                return new int[]{found[0]};
            }
        }
    }
}