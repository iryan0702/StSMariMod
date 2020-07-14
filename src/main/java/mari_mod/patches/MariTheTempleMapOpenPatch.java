package mari_mod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.DungeonMapScreen;
import mari_mod.events.MariTheTemple;

public class MariTheTempleMapOpenPatch {

    @SpirePatch(
            clz = DungeonMapScreen.class,
            method = "open",
            paramtypez = boolean.class
    )
    public static class MariStopTempleEventPatch {

        @SpirePrefixPatch
        public static void Insert(DungeonMapScreen __instance, boolean nob00li) {
            AbstractRoom room = AbstractDungeon.getCurrRoom();
            if(room != null) {
                if (room.event instanceof MariTheTemple) {
                    ((MariTheTemple) room.event).stopMusicalSequence();
                }
            }
        }
    }


}
