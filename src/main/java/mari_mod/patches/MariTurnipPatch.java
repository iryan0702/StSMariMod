package mari_mod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.Turnip;
import mari_mod.MariMod;


public class MariTurnipPatch {
    @SpirePatch(
            clz = Turnip.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class MariCardEnergyRestrictionPatch {

        private static final UIStrings tipStrings = CardCrawlGame.languagePack.getUIString("MariTurnipTip");
        @SpirePostfixPatch
        public static void Postfix(Turnip __instance) {
            if (AbstractDungeon.player != null && AbstractDungeon.player.chosenClass == PlayerClassEnum.MARI) {
                __instance.tips.add(0, new PowerTip(tipStrings.TEXT[0], tipStrings.TEXT[1]));
                __instance.img = MariMod.badTurnipTexture;
            }
        }
    }
}