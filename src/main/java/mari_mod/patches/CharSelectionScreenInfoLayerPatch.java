package mari_mod.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
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