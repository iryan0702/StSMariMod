//TODO: CREDIT SLIMEBOUND CREATOR

package mari_mod.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.CardGlowBorder;
import mari_mod.MariMod;
import mari_mod.cards.AbstractMariCard;
import mari_mod.cards.MariCustomTags;

@SpirePatch(clz= CardGlowBorder.class,method = SpirePatch.CONSTRUCTOR,
        paramtypez = {
                AbstractCard.class})
public class KindleGlowEffectPatch {

    @SpirePostfixPatch
    public static void Postfix(CardGlowBorder obj, AbstractCard card) {

            if (AbstractMariCard.currentlyKindledCard != null && AbstractMariCard.currentlyKindledCard.equals(card) && card.hasTag(MariCustomTags.KINDLE))
                ReflectionHacks.setPrivate(obj, AbstractGameEffect.class, "color", Color.GOLD.cpy());

            }
        }
