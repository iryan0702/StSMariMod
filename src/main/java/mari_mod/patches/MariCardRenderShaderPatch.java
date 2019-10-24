package mari_mod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ShaderHelper;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import mari_mod.MariMod;
import mari_mod.powers.No_Problem_Power;
import mari_mod.relics.MariStageDirections;
import mari_mod.relics.MariTheaterScript;


public class MariCardRenderShaderPatch {

    @SpirePatch(clz = AbstractCard.class, method = "renderPortrait", paramtypez = {SpriteBatch.class})
    public static class MariPortraitShaderPatch {

        @SpirePrefixPatch
        public static void Prefix(AbstractCard instance, SpriteBatch sb) {
            if(instance.color == CardColorEnum.MARI && MariMod.currentlyKindledCard == instance) {
                sb.end();
                sb.setShader(MariMod.goldShader);
                sb.begin();
            }
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "renderPortrait", paramtypez = {SpriteBatch.class})
    public static class MariPortraitShaderResetPatch {

        @SpirePostfixPatch
        public static void Postfix(AbstractCard instance, SpriteBatch sb) {
            ShaderHelper.setShader(sb, ShaderHelper.Shader.DEFAULT);
        }
    }
}
