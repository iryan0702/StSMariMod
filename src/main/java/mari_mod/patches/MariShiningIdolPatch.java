package mari_mod.patches;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import javassist.CtBehavior;
import mari_mod.MariMod;
import mari_mod.powers.Debuff_Energy_Power;
import mari_mod.relics.MariShiningIdol;

public class MariShiningIdolPatch {

    @SpirePatch(
            clz = RewardItem.class,
            method = "applyGoldBonus",
            paramtypez = {boolean.class}
    )
    public static class MariShiningIdolGoldBonusPatch {

        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"tmp"}
        )
        public static void Insert(RewardItem __instance, boolean theft, int tmp) {
            if(AbstractDungeon.player.hasRelic(MariShiningIdol.ID)) {
                __instance.bonusGold += Math.max(0,MathUtils.round(tmp * (MariShiningIdol.STARTING_BOOST - AbstractDungeon.player.gold * MariShiningIdol.BOOST_DECAY)));
            }
        }

        public static class Locator extends SpireInsertLocator {
            public Locator() {
            }
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(ModHelper.class, "isModEnabled");
                int[] found = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
                return new int[]{found[0]};
            }
        }

    }


}
