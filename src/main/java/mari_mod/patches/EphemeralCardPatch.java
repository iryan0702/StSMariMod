//TODO: CREDIT SLIMEBOUND CREATOR

package mari_mod.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.CardGlowBorder;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import mari_mod.MariMod;
import mari_mod.actions.EphemeralDelayedExhaustSpecificCardAction;
import mari_mod.actions.MariEphemeralWaitAction;
import mari_mod.actions.MariWaitAction;
import mari_mod.cards.*;
import mari_mod.effects.EphemeralCardRewardEffect;
import mari_mod.effects.MariEphemeralExhaustEffect;
import mari_mod.effects.MariEphemeralExhaustFlameEffect;

import javax.swing.*;
import java.util.ArrayList;

public class EphemeralCardPatch {

    //
    //IN-GAME MECHANICS AND EFFECTS
    //

    @SpirePatch(clz = CardGroup.class, method = "moveToExhaustPile",
            paramtypez = {
                    AbstractCard.class})
    public static class EphemeralExhaustPatch {

        @SpirePostfixPatch
        public static void Postfix(CardGroup group, AbstractCard card) {
            if(EphemeralField.ephemeralAnimation.get(card)) {
                AbstractDungeon.effectList.add(new MariEphemeralExhaustEffect());
                EphemeralField.ephemeralAnimation.set(card, false);
            }
        }
    }

    @SpirePatch(clz = GameActionManager.class, method = "getNextAction")
    public static class EphemeralAfterActionCheckPatch {

        public static boolean ephemeralJustTriggered = false;

        @SpirePostfixPatch
        public static void Postfix(GameActionManager manager) {
            //if(manager.currentAction != null && !(manager.currentAction instanceof MariEphemeralWaitAction)) {
                AbstractCard nextEphemeralCardToExhaust = null;
                for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                    if (EphemeralField.ephemeral.get(c)) {
                        nextEphemeralCardToExhaust = c;
                        break;
                    }
                }
                if (nextEphemeralCardToExhaust != null) {
                    EphemeralField.ephemeralAnimation.set(nextEphemeralCardToExhaust, true);
                    AbstractGameAction exhaustAction = new EphemeralDelayedExhaustSpecificCardAction(nextEphemeralCardToExhaust, AbstractDungeon.player.discardPile, ephemeralJustTriggered);
                    //ReflectionHacks.setPrivate(exhaustAction, ExhaustSpecificCardAction.class, "startingDuration", 0.01f);
                    //ReflectionHacks.setPrivate(exhaustAction, AbstractGameAction.class, "duration", 0.01f);
                    AbstractDungeon.actionManager.addToTop(exhaustAction);
                    //AbstractDungeon.actionManager.addToTop(new MariEphemeralWaitAction(0.25f));
                    ephemeralJustTriggered = true;
                }else{
                    ephemeralJustTriggered = false;
                }
            //}
        }
    }

    @SpirePatch(clz = ExhaustCardEffect.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {AbstractCard.class})
    public static class EphemeralDisableExhuastEffectPatch {

        @SpirePostfixPatch
        public static void Postfix(ExhaustCardEffect effect, AbstractCard card) {
            //System.out.println("should exhaust effect trigger?");
            if(EphemeralField.ephemeral.get(card) && EphemeralField.ephemeralAnimation.get(card)){
                //System.out.println("effect is done!");
                effect.duration = 0.0f;
                effect.isDone = true;
            }
        }
    }

    @SpirePatch(
            clz = AbstractCard.class,
            method = SpirePatch.CLASS
    )
    public static class EphemeralField {
        public static SpireField<Boolean> ephemeral = new SpireField<>(() -> false);
        public static SpireField<Boolean> ephemeralAnimation = new SpireField<>(() -> false);
    }

    //
    //EPHEMERAL GENERATION
    //

    public static ArrayList<AbstractCard> cardsToRemove = new ArrayList<>();
    public static ArrayList<Integer> indexOfRemoveCards = new ArrayList<>();
    public static float mostRecentRewardSelectDelay = 0.0f;

    @SpirePatch(
            clz = RewardItem.class,
            method = "claimReward"
    )
    public static class MariEphemeralCardRewardOpenCheck {

        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert(RewardItem __instance) {
            if(!EphemeralRewardCheckField.ephemeralChecked.get(__instance)){

                int ephemeralOdds = 100;
                for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                    if (EphemeralField.ephemeral.get(c)) {
                        ephemeralOdds -= 5;
                    }
                }
                cardsToRemove.clear();
                indexOfRemoveCards.clear();
                System.out.println("EPHEMERAL CHECK?");
                boolean ephemeralTriggered = false;

                for(int i = 0; i < __instance.cards.size(); i++) {
                    if(__instance.cards.get(i) instanceof Mari_Aspiration){
                        cardsToRemove.add(__instance.cards.get(i));
                        indexOfRemoveCards.add(i);
                        __instance.cards.set(i, new Mari_Supervision());
                        ephemeralTriggered = true;

                    }else if(EphemeralField.ephemeral.get(__instance.cards.get(i)) && ephemeralOdds <= AbstractDungeon.cardRng.random(99)){

                        AbstractCard oldCard = __instance.cards.get(i);
                        cardsToRemove.add(oldCard);
                        indexOfRemoveCards.add(i);
                        System.out.println("EPHEMERAL FOUND AND TRIGGERED AT INDEX " + i + ". CARD WAS RARITY " + oldCard.rarity + ", COLOR " + oldCard.color);
                        AbstractCard newCard = CardLibrary.getAnyColorCard(oldCard.rarity);
                        boolean dupe = false;
                        while(EphemeralField.ephemeral.get(newCard) || newCard.color != oldCard.color || dupe){
                            newCard = CardLibrary.getAnyColorCard(oldCard.rarity);
                            dupe = false;
                            for(AbstractCard c: __instance.cards){
                                if(c.cardID.equals(newCard.cardID)){
                                    dupe = true;
                                }
                            }
                        }
                        __instance.cards.set(i, newCard);
                        ephemeralTriggered = true;
                    }
                }
                if(ephemeralTriggered){
                    mostRecentRewardSelectDelay = 1.5f;
                }else{
                    mostRecentRewardSelectDelay = 0f;
                }

                EphemeralRewardCheckField.ephemeralChecked.set(__instance, true);
            }else {
                mostRecentRewardSelectDelay = 0f;
            }
        }

        public static class Locator extends SpireInsertLocator {
            public Locator() {
            }
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(CardRewardScreen.class, "open");
                int[] found = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
                return new int[]{found[0]};
            }
        }
    }

    @SpirePatch(clz = CardRewardScreen.class, method = "renderCardReward", paramtypez = {SpriteBatch.class})
    public static class MariEphemeralCardRewardAnimation {

        @SpirePostfixPatch
        public static void Postfix(CardRewardScreen __instance, SpriteBatch sb) {
            if(cardsToRemove.size() > 0){
                for(int i = 0; i < cardsToRemove.size(); i++){
                    AbstractCard oldCard = cardsToRemove.get(i);
                    AbstractCard newCard = __instance.rewardGroup.get(indexOfRemoveCards.get(i));

                    oldCard.current_x = newCard.current_x;
                    oldCard.current_y = newCard.current_y;
                    oldCard.target_x = newCard.target_x;
                    oldCard.target_y = newCard.target_y;
                    oldCard.drawScale = newCard.drawScale * 1.1f;
                    oldCard.targetDrawScale = newCard.targetDrawScale * 1.1f;

                    AbstractDungeon.topLevelEffects.add(new EphemeralCardRewardEffect(oldCard));
                }

                cardsToRemove.clear();
                indexOfRemoveCards.clear();
            }
        }

    }

    @SpirePatch(clz = CardRewardScreen.class, method = "cardSelectUpdate")
    public static class MariEphemeralCardRewardDelaySelect {

        @SpirePrefixPatch
        public static SpireReturn<Boolean> Prefix(CardRewardScreen __instance) {
            if(mostRecentRewardSelectDelay > 0){
                mostRecentRewardSelectDelay -= Gdx.graphics.getDeltaTime();
                for(AbstractCard c: __instance.rewardGroup){
                    c.update();
                }
                return SpireReturn.Return(true);
            }
            return SpireReturn.Continue();
        }

    }

    @SpirePatch(
            clz = RewardItem.class,
            method = SpirePatch.CLASS
    )
    public static class EphemeralRewardCheckField {
        public static SpireField<Boolean> ephemeralChecked = new SpireField<>(() -> false);
    }

    /*@SpirePatch(
            clz = AbstractDungeon.class,
            method = "getRewardCards"
    )
    public static class EphemeralCardRewardRate {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getMethodName().equals("getCard")) {
                        m.replace("{" +
                                "$_ = $proceed($$);" +
                                "$_ = " + EphemeralCardPatch.class.getName() + ".normalReroll($_);" +
                            "}");
                    }else if (m.getMethodName().equals("getAnyColorCard")) {
                        m.replace("{" +
                                "$_ = $proceed($$);" +
                                "$_ = " + EphemeralCardPatch.class.getName() + ".anyColorReroll($_);" +
                            "}");
                    }
                }
            };
        }
    }

    public static AbstractCard normalReroll(AbstractCard generated){
        if(EphemeralField.ephemeral.get(generated)) {
            int ephemeralOdds = 100;
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                if (EphemeralField.ephemeral.get(c)) {
                    ephemeralOdds -= 5;
                }
            }
            if (ephemeralOdds <= AbstractDungeon.cardRng.random(99)) {
                AbstractCard retVal;
                while (true) {
                    retVal = AbstractDungeon.getCard(generated.rarity);
                    if (!EphemeralField.ephemeral.get(retVal)) {
                        return retVal;
                    }
                }
            }
        }
        return generated;
    }

    public static AbstractCard anyColorReroll(AbstractCard generated){
        if(EphemeralField.ephemeral.get(generated)) {
            int ephemeralOdds = 100;
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                if (EphemeralField.ephemeral.get(c)) {
                    ephemeralOdds -= 5;
                }
            }
            if (ephemeralOdds <= AbstractDungeon.cardRng.random(99)) {
                AbstractCard retVal;
                while (true) {
                    retVal = CardLibrary.getAnyColorCard(generated.rarity);
                    if (!EphemeralField.ephemeral.get(retVal)) {
                        return retVal;
                    }
                }
            }
        }
        return generated;
    }*/

}
