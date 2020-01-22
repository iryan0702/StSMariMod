package mari_mod.patches;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.SingleRelicViewPopup;
import mari_mod.relics.AbstractMariRelic;

@SpirePatch(
        clz=SingleRelicViewPopup.class,
        method="initializeLargeImg"
)
public class UseLargeRelicArtPatch
{
    @SpireInsertPatch(
            rloc=0,
            localvars={"relic", "largeImg"}
    )
    public static SpireReturn<Void> Insert(SingleRelicViewPopup __instance, AbstractRelic relic, @ByRef Texture[] largeImg)
    {
        if (relic instanceof AbstractMariRelic) {
            largeImg[0] = ((AbstractMariRelic) relic).largeImg;
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }
}