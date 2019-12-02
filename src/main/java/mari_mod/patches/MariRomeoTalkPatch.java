package mari_mod.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.monsters.city.BanditLeader;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.EventRoom;
import javassist.CtBehavior;
import mari_mod.events.MariMaskedBandits;
import mari_mod.relics.MariShiningIdol;

public class MariRomeoTalkPatch {

    @SpirePatch(
            clz = BanditLeader.class,
            method = "takeTurn"
    )
    public static class MariRomeoPatch {
        public static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getEventString("MariMod:Masked Bandits").DESCRIPTIONS;

        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert(BanditLeader __instance) {
            System.out.println("insert triggers");
            GameActionManager g = AbstractDungeon.actionManager;
            EventRoom e = null;
            if(AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom() instanceof EventRoom) {
                System.out.println("Room found!");
                 e = (EventRoom) AbstractDungeon.getCurrRoom();
            }

            if(g.actions.size() > 0 && g.actions.get(g.actions.size()-1) instanceof TalkAction && e != null && e.event instanceof MariMaskedBandits && ((MariMaskedBandits) e.event).isAmbushMode){
                System.out.println("setting talk");
                TalkAction t = (TalkAction) g.actions.get(g.actions.size()-1);
                ReflectionHacks.setPrivate(t, TalkAction.class, "msg", DESCRIPTIONS[4]);
            }
        }

        public static class Locator extends SpireInsertLocator {
            public Locator() {
            }
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.NewExprMatcher(SetMoveAction.class);
                int[] found = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
                return new int[]{found[0]};
            }
        }

    }


}
