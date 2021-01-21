package mari_mod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import mari_mod.MariMod;


// POST INITIALIZE STARTER DECK:
// Allows Mari's cards to start with upgraded cards

public class MariPostInitializeStarterDeckPatch {

    @SpirePatch(clz = AbstractPlayer.class, method = "initializeStarterDeck")
    public static class MariPostInitializeStarterDeck {

        @SpirePostfixPatch
        public static void Postfix(AbstractPlayer instance) {
            MariMod.receivePostInitializeStarterDeck();
        }
    }
}
