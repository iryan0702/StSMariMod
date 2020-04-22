package mari_mod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.ShaderHelper;
import mari_mod.MariMod;
import mari_mod.cards.AbstractMariCard;


public class MariCardRenderShaderPatch {

    @SpirePatch(clz = AbstractCard.class, method = "renderPortrait", paramtypez = {SpriteBatch.class})
    public static class MariPortraitShaderPatch {

        @SpirePrefixPatch
        public static void Prefix(AbstractCard instance, SpriteBatch sb) {
            if(instance.color == CardColorEnum.MARI && AbstractMariCard.currentlyKindledCard == instance && AbstractMariCard.kindleTimer > MariKindleArrowPatch.MariKindleArrowTailPatch.kindleTime) {
                sb.end();
                sb.setShader(MariMod.goldShader);
                sb.begin();
            }else if(instance instanceof AbstractMariCard && ((AbstractMariCard)instance).faded){
                sb.end();
                sb.setShader(MariMod.greyShader);
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
