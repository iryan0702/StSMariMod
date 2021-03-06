package mari_mod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import javassist.CtBehavior;
import mari_mod.MariMod;
import mari_mod.cards.MariCustomTags;
import mari_mod.cards.Mari_Aspiration;

import java.util.Iterator;


public class MariCardPoolPatches {

    public static CardGroup fadingCards;

    @SpirePatch(clz = AbstractDungeon.class, method = "initializeCardPools")
    public static class RemoveCardsFromInitializedPoolsPatch {

        @SpirePostfixPatch
        public static void Postfix(AbstractDungeon ad) {

            fadingCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

            System.out.println("REMOVE CARDS FROM POOLS AFTER INITIALIZE");
            System.out.println("REMOVE CARDS FROM POOLS AFTER INITIALIZE");
            System.out.println("REMOVE CARDS FROM POOLS AFTER INITIALIZE");
            System.out.println("REMOVE CARDS FROM POOLS AFTER INITIALIZE");
            System.out.println("REMOVE CARDS FROM POOLS AFTER INITIALIZE");
            System.out.println("REMOVE CARDS FROM POOLS AFTER INITIALIZE");
            System.out.println("REMOVE CARDS FROM POOLS AFTER INITIALIZE");
            removeMedalFromPool(AbstractDungeon.commonCardPool);
            removeMedalFromPool(AbstractDungeon.uncommonCardPool);
            removeMedalFromPool(AbstractDungeon.rareCardPool);
            removeMedalFromPool(AbstractDungeon.srcCommonCardPool);
            removeMedalFromPool(AbstractDungeon.srcUncommonCardPool);
            removeMedalFromPool(AbstractDungeon.srcRareCardPool);

            moveFadingFromPool(AbstractDungeon.commonCardPool);
            moveFadingFromPool(AbstractDungeon.uncommonCardPool);
            moveFadingFromPool(AbstractDungeon.rareCardPool);
            moveFadingFromPool(AbstractDungeon.srcCommonCardPool);
            moveFadingFromPool(AbstractDungeon.srcUncommonCardPool);
            moveFadingFromPool(AbstractDungeon.srcRareCardPool);
        }

        public static void moveFadingFromPool(CardGroup g){
            Iterator cards = g.group.iterator();
            while(cards.hasNext()){
                AbstractCard c = (AbstractCard)cards.next();
                if(EphemeralCardPatch.EphemeralField.ephemeral.get(c)){
                    fadingCards.addToTop(c);
                    cards.remove();
                }
            }
        }

        public static void removeMedalFromPool(CardGroup g){
            Iterator cards = g.group.iterator();
            while(cards.hasNext()){
                AbstractCard c = (AbstractCard)cards.next();
                if(shouldBeRemoved(c)){
                    cards.remove();
                }
            }
        }

        public static boolean shouldBeRemoved(AbstractCard c){
            if(c instanceof Mari_Aspiration) return true;
            if(c.hasTag(MariCustomTags.DRAMA) && MariMod.saveableKeeper.currentClass != MariCustomTags.DRAMA){
                System.out.println("REMOVING DRAMA CARD: " + c.name);
                return true;
            }
            if(c.hasTag(MariCustomTags.ENERGY) && MariMod.saveableKeeper.currentClass != MariCustomTags.ENERGY){
                System.out.println("REMOVING ENERGY: " + c.name);
                return true;
            }
            return false;
        }
    }

    @SpirePatch(
            clz = CharacterSelectScreen.class,
            method = "updateButtons"
    )
    public static class MariSetClassOnGameStartPatch {

        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert(CharacterSelectScreen __instance) {
            System.out.println("GAME START!!");
            MariMod.saveableKeeper.currentClass = MariMod.selectScreen.getSelectedClass();
            System.out.println("Current class: " + MariMod.saveableKeeper.currentClass);
        }

        public static class Locator extends SpireInsertLocator {
            public Locator() {
            }
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractDungeon.class, "generateSeeds");
                int[] found = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
                return new int[]{found[0]};
            }
        }

    }
}
