//TODO: CREDIT SLIMEBOUND CREATOR

package mari_mod.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.CardGlowBorder;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import mari_mod.cards.AbstractMariCard;
import mari_mod.cards.MariCustomTags;
import mari_mod.effects.MariEphemeralExhaustEffect;
import mari_mod.effects.MariEphemeralExhaustFlameEffect;

import javax.swing.*;
import java.util.ArrayList;

public class EphemeralCardPatch {

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

        @SpirePostfixPatch
        public static void Postfix(GameActionManager manager) {
            AbstractCard nextEphemeralCardToExhaust = null;
            for(AbstractCard c: AbstractDungeon.player.discardPile.group){
                if(EphemeralField.ephemeral.get(c)){
                    nextEphemeralCardToExhaust = c;
                    break;
                }
            }
            if(nextEphemeralCardToExhaust != null){
                EphemeralField.ephemeralAnimation.set(nextEphemeralCardToExhaust, true);
                AbstractGameAction exhaustAction = new ExhaustSpecificCardAction(nextEphemeralCardToExhaust,AbstractDungeon.player.discardPile,true);
                ReflectionHacks.setPrivate(exhaustAction, ExhaustSpecificCardAction.class, "startingDuration", 0.01f);
                ReflectionHacks.setPrivate(exhaustAction, AbstractGameAction.class, "duration", 0.01f);
                AbstractDungeon.actionManager.addToTop(exhaustAction);
            }
        }
    }

    @SpirePatch(clz = ExhaustCardEffect.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {AbstractCard.class})
    public static class EphemeralDisableExhuastEffectPatch {

        @SpirePostfixPatch
        public static void Postfix(ExhaustCardEffect effect, AbstractCard card) {
            System.out.println("should exhaust effect trigger?");
            if(EphemeralField.ephemeral.get(card) && EphemeralField.ephemeralAnimation.get(card)){
                System.out.println("effect is done!");
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
}
