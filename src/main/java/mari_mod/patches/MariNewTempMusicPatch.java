package mari_mod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.audio.TempMusic;


@SpirePatch(clz= TempMusic.class, method="getSong", paramtypez = String.class)
public class MariNewTempMusicPatch
{
    @SpirePrefixPatch
    public static SpireReturn Prefix(TempMusic __instance, String key)
    {
        switch (key)
        {
            case "MariMod:MariTheSoundOfRain":
                return SpireReturn.Return(MainMusic.newMusic("mari_mod/audio/MariTheSoundOfRain.ogg"));
        }
        return SpireReturn.Continue();
    }
}