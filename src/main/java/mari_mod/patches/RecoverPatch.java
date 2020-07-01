package mari_mod.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CtBehavior;
import mari_mod.cards.AbstractMariCard;
import mari_mod.cards.MariCustomTags;

@Deprecated
public class RecoverPatch {
//    @SpirePatch(clz = AbstractPlayer.class, method = "useCard")
//    public static class MariRecoverPatch {
//
//        @SpireInsertPatch(
//                locator = Locator.class
//        )
//        public static void Insert(AbstractPlayer _instance, AbstractCard c, AbstractMonster monster, int energyOnUse) {
//            if(c.hasTag(MariCustomTags.RECOVER)){
//                int cost = c.costForTurn;
//                if(c.cost == -1) cost = energyOnUse;
//                if(c.freeToPlayOnce) cost = 0;
//                int gain = Math.max(cost-1, 0);
//                AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(gain));
//            }
//        }
//
//        public static class Locator extends SpireInsertLocator {
//            public Locator() {
//            }
//            @Override
//            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
//                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "use");
//                int[] found = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
//                return new int[]{found[0]};
//            }
//        }
//    }
}