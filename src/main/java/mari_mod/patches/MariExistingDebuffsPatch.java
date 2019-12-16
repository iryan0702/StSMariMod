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
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.CardGlowBorder;
import mari_mod.MariMod;
import mari_mod.cards.MariCustomTags;
import mari_mod.events.AllMariModEvents;
import mari_mod.events.MariSnackShackEvent;
import mari_mod.powers.No_Problem_Power;
import mari_mod.relics.MariStageDirections;
import mari_mod.relics.MariTheaterScript;


public class MariExistingDebuffsPatch {

    //VULNERABLE

    @SpirePatch(clz = VulnerablePower.class, method = "atDamageReceive",
            paramtypez = {
                    float.class, DamageInfo.DamageType.class})
    public static class MariVulnerableModifierPatch {

        @SpirePrefixPatch
        public static SpireReturn<Float> Prefix(VulnerablePower power, float damage, DamageInfo.DamageType damageType) {

            if (damageType == DamageInfo.DamageType.NORMAL && power.owner.isPlayer){
                if(AbstractDungeon.player.hasRelic(MariTheaterScript.ID) || AbstractDungeon.player.hasPower(No_Problem_Power.POWER_ID)) {
                    return SpireReturn.Return(damage);
                }
                if(AbstractDungeon.player.hasRelic(MariStageDirections.ID)){
                    return SpireReturn.Return(damage * (1 + power.amount * MariStageDirections.VULN_MULTIPLIER));
                }
            }
            return SpireReturn.Continue();

        }
    }

    @SpirePatch(clz = VulnerablePower.class, method = "atEndOfRound")
    public static class MariVulnerableDecayPatch {

        @SpirePrefixPatch
        public static SpireReturn Prefix(VulnerablePower power) {

            if (power.owner.isPlayer && (AbstractDungeon.player.hasRelic(MariTheaterScript.ID) || AbstractDungeon.player.hasRelic(MariStageDirections.ID) || AbstractDungeon.player.hasPower(No_Problem_Power.POWER_ID))) {
                return SpireReturn.Return(null);
            }

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = VulnerablePower.class, method = "updateDescription")
    public static class MariVulnerableDescriptionPatch {

        @SpirePostfixPatch
        public static void Postfix(VulnerablePower power) {
            if (power.owner.isPlayer && AbstractDungeon.player.hasRelic(MariStageDirections.ID)) {
                power.description = AbstractDungeon.player.getRelic(MariStageDirections.ID).DESCRIPTIONS[3] + (int) (power.amount * MariStageDirections.VULN_MULTIPLIER * 100f) + AbstractDungeon.player.getRelic(MariStageDirections.ID).DESCRIPTIONS[4];
            }

            if (power.owner.isPlayer && AbstractDungeon.player.hasRelic(MariTheaterScript.ID)) {
                power.description = AbstractDungeon.player.getRelic(MariTheaterScript.ID).DESCRIPTIONS[2];
            }
        }
    }

    //WEAK

    /*@SpirePatch(clz = WeakPower.class, method = "atDamageGive",
            paramtypez = {
                    float.class, DamageInfo.DamageType.class})
    public static class MariWeakModifierPatch {

        @SpirePrefixPatch
        public static SpireReturn<Float> Prefix(WeakPower power, float damage, DamageInfo.DamageType damageType) {


            if (power.owner.isPlayer && AbstractDungeon.player.hasPower(No_Problem_Power.POWER_ID) && damageType == DamageInfo.DamageType.NORMAL) {
                return SpireReturn.Return(damage);
            }

            return SpireReturn.Continue();
        }
    }*/

    //FRAIL

    @SpirePatch(clz = FrailPower.class, method = "modifyBlock",
            paramtypez = {
                    float.class})
    public static class MariFrailModifierPatch {

        @SpirePrefixPatch
        public static SpireReturn<Float> Prefix(FrailPower power, float block) {

            if (power.owner.isPlayer) {
                if (AbstractDungeon.player.hasRelic(MariTheaterScript.ID)) {
                    return SpireReturn.Return(block);
                }
                if (AbstractDungeon.player.hasRelic(MariStageDirections.ID)) {
                    return SpireReturn.Return(Math.max(0.0F, block * (1 - power.amount * MariStageDirections.FRAIL_MULTIPLIER)));
                }
            }
            return SpireReturn.Continue();

        }
    }

    @SpirePatch(clz = FrailPower.class, method = "atEndOfRound")
    public static class MariFrailDecayPatch {

        @SpirePrefixPatch
        public static SpireReturn Prefix(FrailPower power) {

            if (power.owner.isPlayer && (AbstractDungeon.player.hasRelic(MariTheaterScript.ID) || AbstractDungeon.player.hasRelic(MariStageDirections.ID))) {
                return SpireReturn.Return(null);
            }

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = FrailPower.class, method = "updateDescription")
    public static class MariFrailDescriptionPatch {

        @SpirePostfixPatch
        public static void Postfix(FrailPower power) {
            if (power.owner.isPlayer && AbstractDungeon.player.hasRelic(MariStageDirections.ID)) {
                power.description = AbstractDungeon.player.getRelic(MariStageDirections.ID).DESCRIPTIONS[1] + (int) (power.amount * MariStageDirections.FRAIL_MULTIPLIER * 100f) + AbstractDungeon.player.getRelic(MariStageDirections.ID).DESCRIPTIONS[2];
            }
            if (power.owner.isPlayer && AbstractDungeon.player.hasRelic(MariTheaterScript.ID)) {
                power.description = AbstractDungeon.player.getRelic(MariTheaterScript.ID).DESCRIPTIONS[1];
            }
        }
    }
}
