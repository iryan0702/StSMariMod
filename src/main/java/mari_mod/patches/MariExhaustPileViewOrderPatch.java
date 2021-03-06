package mari_mod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.ExhaustPileViewScreen;
import mari_mod.actions.MariRecallAction;
import mari_mod.ui.ExhaustPileViewOrderButton;


public class MariExhaustPileViewOrderPatch {

    static ExhaustPileViewOrderButton button;

    @SpirePatch(clz = ExhaustPileViewScreen.class, method = "update")
    public static class RemoveCardsFromInitializedPoolsPatch {

        @SpirePostfixPatch
        public static void Postfix(ExhaustPileViewScreen screen) {
            if(button == null){
                button = new ExhaustPileViewOrderButton();
            }
            button.update();
        }
    }

    @SpirePatch(clz = ExhaustPileViewScreen.class, method = "render", paramtypez = SpriteBatch.class)
    public static class renderButton {

        @SpirePostfixPatch
        public static void Postfix(ExhaustPileViewScreen screen, SpriteBatch sb) {
            button.render(sb);
        }
    }

    @SpirePatch(clz = ExhaustPileViewScreen.class, method = "open")
    public static class saveLists {

        @SpirePostfixPatch
        public static void Postfix(ExhaustPileViewScreen screen) {
            if(button == null){
                button = new ExhaustPileViewOrderButton();
            }
            if(button.orderedGroup == null){
                button.orderedGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            }
            CardGroup tempGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            button.orderedGroup.clear();

            for(AbstractCard c: AbstractDungeon.player.exhaustPile.group){
                AbstractCard toAdd = c.makeStatEquivalentCopy();
                toAdd.setAngle(0.0F, true);
                toAdd.targetDrawScale = 0.75F;
                toAdd.drawScale = 0.75F;
                toAdd.current_x = Settings.WIDTH/2f;
                toAdd.current_y = Settings.HEIGHT/2f;
                toAdd.lighten(true);
                tempGroup.addToTop(toAdd);
            }

            AbstractCard sortCard = MariRecallAction.findRecallTarget(tempGroup);;
            while(sortCard != null){
                tempGroup.removeCard(sortCard);
                button.orderedGroup.addToTop(sortCard);

                sortCard = MariRecallAction.findRecallTarget(tempGroup);
            }

            button.rarityGroup = new CardGroup(AbstractDungeon.player.exhaustPile, CardGroup.CardGroupType.UNSPECIFIED);
            button.rarityGroup.sortAlphabetically(true);
            button.rarityGroup.sortByRarityPlusStatusCardType(true);
            for(AbstractCard c: button.rarityGroup.group){
                c.setAngle(0.0F, true);
                c.targetDrawScale = 0.75F;
                c.drawScale = 0.75F;
                c.current_x = Settings.WIDTH/2f;
                c.current_y = Settings.HEIGHT/2f;
                c.lighten(true);
            }

            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                button.hideInstantly();
                button.show();
            }
        }
    }
}
