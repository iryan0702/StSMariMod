package mari_mod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import mari_mod.cards.AbstractMariCard;

import java.util.ArrayList;

@SpirePatch(
        clz=AbstractCard.class,
        method="makeStatEquivalentCopy"
)
public class StatEquivalentCopyCardTagsPatch {

    @SpireInsertPatch(
            locator=Locator.class,
            localvars={"card"}
    )
    public static void Insert(AbstractCard __instance,  AbstractCard card) {
        if(__instance instanceof AbstractMariCard && card instanceof AbstractMariCard){
            AbstractMariCard castInstance = (AbstractMariCard)__instance;
            AbstractMariCard castCard = (AbstractMariCard)card;
            castCard.baseRadiance = castInstance.baseRadiance;
            castCard.baseGoldCost = castInstance.baseGoldCost;
            castCard.radiance = castInstance.baseRadiance;
            castCard.goldCost = castInstance.baseGoldCost;
            System.out.println("NEW CARD TRIGGERED: it has " + castCard.baseRadiance + " " + castCard.radiance + " " + castCard.baseGoldCost + " " + castCard.goldCost);
            EphemeralCardPatch.EphemeralField.ephemeral.set(castCard,EphemeralCardPatch.EphemeralField.ephemeral.get(castInstance));
        }
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "name");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
        }
    }

}