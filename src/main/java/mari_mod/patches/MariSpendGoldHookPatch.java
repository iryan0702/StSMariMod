package mari_mod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import mari_mod.MariMod;

@SpirePatch(clz= AbstractPlayer.class,method = "loseGold",
        paramtypez = {
                int.class})
public class MariSpendGoldHookPatch {

    @SpirePrefixPatch
    public static void PreSpendGold(AbstractPlayer player, int amount) {
        MariMod.loseGold(amount);
    }
}
