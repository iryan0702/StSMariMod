package mari_mod.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.cutscenes.Cutscene;

import java.util.ArrayList;

@SpirePatch(clz= Cutscene.class,method = SpirePatch.CONSTRUCTOR,
        paramtypez = {
                AbstractPlayer.PlayerClass.class})
public class MariHeartCutscenePatch {

    @SpirePostfixPatch
    public static void Postfix(Cutscene obj, AbstractPlayer.PlayerClass chosenClass) {

            if(chosenClass.equals(PlayerClassEnum.MARI)){
                ReflectionHacks.setPrivate(obj, Cutscene.class, "isDone", true);
                ReflectionHacks.setPrivate(obj, Cutscene.class, "panels", new ArrayList());
            }

    }
}
