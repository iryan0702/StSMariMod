package mari_mod.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.helpers.EventHelper;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.CardGlowBorder;
import mari_mod.MariMod;
import mari_mod.cards.MariCustomTags;
import mari_mod.events.AllMariModEvents;
import mari_mod.events.MariSnackShackEvent;
import mari_mod.relics.MariStageDirections;


public class MariStageDirectionsPatch {
    @SpirePatch(clz = VulnerablePower.class, method = "atDamageReceive",
            paramtypez = {
                    float.class, DamageInfo.DamageType.class})
    public static class MariStageDirectionsVulnerableModifierPatch {

        @SpirePrefixPatch
        public static SpireReturn<Float> Prefix(VulnerablePower power, float damage, DamageInfo.DamageType damageType) {

            if (damageType == DamageInfo.DamageType.NORMAL && power.owner.isPlayer && AbstractDungeon.player.hasRelic(MariStageDirections.ID)) {

                return SpireReturn.Return(damage * 1.25F);
            }

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = VulnerablePower.class, method = "atEndOfRound")
    public static class MariStageDirectionsVulnerableDecayPatch {

        @SpirePrefixPatch
        public static SpireReturn Prefix(VulnerablePower power) {

            if (power.owner.isPlayer && AbstractDungeon.player.hasRelic(MariStageDirections.ID)) {
                return SpireReturn.Return(null);
            }

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = VulnerablePower.class, method = "updateDescription")
    public static class MariStageDirectionsVulnerableDescriptionPatch {

        @SpirePostfixPatch
        public static void Postfix(VulnerablePower power) {
            if (power.owner.isPlayer && AbstractDungeon.player.hasRelic(MariStageDirections.ID)) {
                power.description = AbstractDungeon.player.getRelic(MariStageDirections.ID).DESCRIPTIONS[2];
            }
        }
    }

    @SpirePatch(clz = FrailPower.class, method = "modifyBlock",
            paramtypez = {
                    float.class})
    public static class MariStageDirectionsFrailModifierPatch {

        @SpirePrefixPatch
        public static SpireReturn<Float> Prefix(FrailPower power, float block) {


            if (power.owner.isPlayer && AbstractDungeon.player.hasRelic(MariStageDirections.ID)) {
                return SpireReturn.Return(block * 0.9F);
            }

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = FrailPower.class, method = "atEndOfRound")
    public static class MariStageDirectionsFrailDecayPatch {

        @SpirePrefixPatch
        public static SpireReturn Prefix(FrailPower power) {

            if (power.owner.isPlayer && AbstractDungeon.player.hasRelic(MariStageDirections.ID)) {
                return SpireReturn.Return(null);
            }

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = FrailPower.class, method = "updateDescription")
    public static class MariStageDirectionsFrailDescriptionPatch {

        @SpirePostfixPatch
        public static void Postfix(FrailPower power) {
            if (power.owner.isPlayer && AbstractDungeon.player.hasRelic(MariStageDirections.ID)) {
                power.description = AbstractDungeon.player.getRelic(MariStageDirections.ID).DESCRIPTIONS[1];
            }
        }
    }
}
