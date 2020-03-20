package mari_mod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.helpers.EventHelper;
import mari_mod.MariMod;
import mari_mod.events.MariSnackShackEvent;

@SpirePatch(clz= EventHelper.class,method = "getEvent",
        paramtypez = {
                String.class})
public class MariForceEventPatch {

    @SpirePostfixPatch
    public static SpireReturn<AbstractEvent> Prefix(String key) {

            if (!MariMod.saveableKeeper.snackShackVisited && AbstractDungeon.player.chosenClass == PlayerClassEnum.MARI && AbstractDungeon.player.gold < 25) {
                return SpireReturn.Return(new MariSnackShackEvent());
            }

            return SpireReturn.Continue();
    }
}
