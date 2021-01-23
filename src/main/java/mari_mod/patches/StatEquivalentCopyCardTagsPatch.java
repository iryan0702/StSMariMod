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
            castCard.goldCost = castInstance.baseGoldCost;
            castCard.baseGoldCost = castInstance.baseGoldCost;
            EphemeralCardPatch.EphemeralField.ephemeral.set(castCard,EphemeralCardPatch.EphemeralField.ephemeral.get(castInstance));

            //Extra initializeDescription to ensure that the copy has the keyword Fading replaced with Faded.
            if(castInstance.faded){
                castCard.setFadedStats();
                castCard.initializeDescription();
            }else{
                castCard.baseRadiance = castInstance.baseRadiance;
                castCard.radiance = castInstance.baseRadiance;
            }
        }
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "name");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
        }
    }

}