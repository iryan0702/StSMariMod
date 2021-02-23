package mari_mod.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import javassist.CtBehavior;
import mari_mod.powers.Grand_Scheme_Power;
import mari_mod.powers.Radiance_Power;

public class MariGrandSchemePatch {

    @SpirePatch(
            clz = ApplyPowerAction.class,
            method = "update"
    )
    public static class PreventRadianceApplicationPatch {

        public static ReflectionHacks.RMethod m = null;

        @SpireInsertPatch(
                locator = Locator.class
        )
        public static SpireReturn Insert(ApplyPowerAction __instance) {
            if(m == null) {
                m = ReflectionHacks.privateMethod(AbstractGameAction.class, "tickDuration");
            }
            m.invoke(__instance);

            AbstractPower grandScheme = __instance.target.getPower(Grand_Scheme_Power.POWER_ID);
            AbstractPower applyPower = ReflectionHacks.getPrivate(__instance, ApplyPowerAction.class, "powerToApply");

            if(grandScheme != null && applyPower.ID.equals(Radiance_Power.POWER_ID)){
                if(grandScheme instanceof Grand_Scheme_Power){
                    ((Grand_Scheme_Power)grandScheme).newRadiance(applyPower.amount);
                    grandScheme.flash();
                    return SpireReturn.Return(null);
                }
            }
            return SpireReturn.Continue();
        }

        public static class Locator extends SpireInsertLocator {
            public Locator() {
            }
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.NewExprMatcher(FlashAtkImgEffect.class);
                int[] found = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
                return new int[]{found[0]};
            }
        }

    }
}