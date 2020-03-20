package mari_mod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;

@SpirePatch(clz = CharacterOption.class, method = "renderInfo", paramtypez = {SpriteBatch.class})
public class CharSelectionScreenInfoLayerPatch {

    @SpirePostfixPatch
    public static SpireReturn Prefix(CharacterOption obj, SpriteBatch sb) {//, AbstractCard card

        if(obj.c.chosenClass.equals(PlayerClassEnum.MARI)) {
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }

}