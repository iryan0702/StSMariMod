package mari_mod.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import javassist.CtBehavior;
import mari_mod.MariMod;

public class MariMusicalAttackEffect {
    @SpireEnum
    public static AbstractGameAction.AttackEffect MUSICAL;

    @SpirePatch(
            clz = DamageAction.class,
            method = "update"
    )
    public static class MusicalAttackEffectTintPatch {

        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert(DamageAction __instance) {
            if(__instance.attackEffect == MUSICAL){
                __instance.target.tint.color.set(Color.PURPLE.cpy());
                __instance.target.tint.changeColor(Color.WHITE.cpy());
            }
        }

        public static class Locator extends SpireInsertLocator {
            public Locator() {
            }
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCreature.class, "damage");
                int[] found = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
                return new int[]{found[0]};
            }
        }

    }

    @SpirePatch(
            clz= FlashAtkImgEffect.class,
            method="loadImage"
    )
    public static class MusicalAttackEffectImagePatch{
        @SpirePrefixPatch
        public static SpireReturn<TextureAtlas.AtlasRegion> Prefix(FlashAtkImgEffect __instance)
        {
            if(ReflectionHacks.getPrivate(__instance, FlashAtkImgEffect.class, "effect") == MUSICAL){
                return SpireReturn.Return(MariMod.musicNoteAttack);
            }else{
                return SpireReturn.Continue();
            }
        }
    }

    @SpirePatch(
            clz= FlashAtkImgEffect.class,
            method="playSound"
    )
    public static class MusicalAttackEffectSoundPatch{
        @SpirePrefixPatch
        public static SpireReturn Prefix(FlashAtkImgEffect __instance)
        {
            if(ReflectionHacks.getPrivate(__instance, FlashAtkImgEffect.class, "effect") == MUSICAL){
                CardCrawlGame.sound.play("MariMod:MariMusicalAttack");
                CardCrawlGame.sound.play("BLUNT_FAST");
                return SpireReturn.Return(null);
            }else{
                return SpireReturn.Continue();
            }
        }
    }
}