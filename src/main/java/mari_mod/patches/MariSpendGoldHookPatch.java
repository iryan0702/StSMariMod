package mari_mod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.helpers.EventHelper;
import mari_mod.MariMod;
import mari_mod.events.MariSnackShackEvent;

@SpirePatch(clz= AbstractPlayer.class,method = "loseGold",
        paramtypez = {
                int.class})
public class MariSpendGoldHookPatch {

    @SpirePrefixPatch
    public static void PreSpendGold(AbstractPlayer player, int amount) {
        MariMod.loseGold(amount);
    }
}
