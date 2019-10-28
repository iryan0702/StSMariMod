package mari_mod.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import mari_mod.MariMod;
import mari_mod.cards.MariCustomTags;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DisplayCardMedalPatches {
    @SpirePatch(clz = AbstractCard.class, method = "renderCardBg", paramtypez = {SpriteBatch.class, float.class, float.class})
    public static class DisplayCardMedalPatch {

        public static final Logger logger = LogManager.getLogger(DisplayCardMedalPatch.class.getName());

        @SpirePostfixPatch
        public static void Postfix(AbstractCard obj, SpriteBatch sb, float x, float y) {
            Color renderColor = (Color)ReflectionHacks.getPrivate(obj, AbstractCard.class, "renderColor");
            sb.setColor(renderColor);
            if(obj.hasTag(MariCustomTags.DRAMA)) sb.draw(MariMod.cardMedalDramaTexture, x-256.0F, y-256.0F, 256.0F, 256.0F, 512.0F, 512.0F, obj.drawScale * Settings.scale, obj.drawScale * Settings.scale, obj.angle, 0, 0, 512, 512, false, false);
            if(obj.hasTag(MariCustomTags.ENERGY)) sb.draw(MariMod.cardMedalEnergyTexture, x-256.0F, y-256.0F, 256.0F, 256.0F, 512.0F, 512.0F, obj.drawScale * Settings.scale, obj.drawScale * Settings.scale, obj.angle, 0, 0, 512, 512, false, false);
        }
    }
    @SpirePatch(clz = SingleCardViewPopup.class, method = "renderCardBack", paramtypez = {SpriteBatch.class})
    public static class DisplayCardMedalSinglePopUpPatch {

        public static final Logger logger = LogManager.getLogger(DisplayCardMedalSinglePopUpPatch.class.getName());

        @SpirePostfixPatch
        public static void Postfix(SingleCardViewPopup obj, SpriteBatch sb) {
            sb.setColor(Color.WHITE.cpy());
            AbstractCard card = (AbstractCard)ReflectionHacks.getPrivate(obj, SingleCardViewPopup.class, "card");
            if(card.hasTag(MariCustomTags.DRAMA)) sb.draw(MariMod.cardMedalDramaLargeTexture, (float)Settings.WIDTH / 2.0F - 512.0F, (float)Settings.HEIGHT / 2.0F - 512.0F, 512.0F, 512.0F, 1024.0F, 1024.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 1024, 1024, false, false);
            if(card.hasTag(MariCustomTags.ENERGY)) sb.draw(MariMod.cardMedalEnergyLargeTexture, (float)Settings.WIDTH / 2.0F - 512.0F, (float)Settings.HEIGHT / 2.0F - 512.0F, 512.0F, 512.0F, 1024.0F, 1024.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 1024, 1024, false, false);
        }
    }
}

