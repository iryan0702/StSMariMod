package mari_mod.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mari_mod.cards.MariCustomTags;
import mari_mod.cards.OnRecallCard;
import mari_mod.patches.EphemeralCardPatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariRecallAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariRecallAction.class.getName());
    public RecallType recallType;
    private AbstractPlayer p;

    public MariRecallAction(RecallType rT) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.recallType = rT;
        this.p = AbstractDungeon.player;
    }

    public void update() {
        AbstractCard c = findRecallTarget(recallType);

        if(c != null) {
            c.unfadeOut();
            c.current_x = Settings.WIDTH * 2;
            c.current_y = Settings.HEIGHT / 2f;
            if (c instanceof OnRecallCard) {
                ((OnRecallCard) c).onRecall();
            }
            if (p.hand.size() == BaseMod.MAX_HAND_SIZE) {
                p.drawPile.moveToDiscardPile(c);
                p.createHandIsFullDialog();
            } else {
                p.hand.addToHand(c);
            }

            c.fadingOut = false;
            p.exhaustPile.removeCard(c);

            p.hand.refreshHandLayout();
            p.hand.applyPowers();
        }

        this.isDone = true;
    }

    public static AbstractCard findRecallTarget(RecallType recallType){
        AbstractPlayer p = AbstractDungeon.player;
        boolean cardFound = false;
        AbstractCard retVal = null;
        for (int i = 0; i < p.exhaustPile.size() && !cardFound; i++) {
            AbstractCard c = p.exhaustPile.group.get(i);
            if(c.hasTag(MariCustomTags.GLARING)){
                cardFound = true;
            }
            if (recallType == RecallType.RADIANCE && c.tags.contains(MariCustomTags.RADIANCE)) {
                cardFound = true;
            } else if (recallType == RecallType.SPEND && c.tags.contains(MariCustomTags.SPEND)) {
                cardFound = true;
            } else if (recallType == RecallType.QUOTATIONS && c.tags.contains(MariCustomTags.QUOTATIONS)) {
                cardFound = true;
            } else if (recallType == RecallType.EPHEMERAL && EphemeralCardPatch.EphemeralField.ephemeral.get(c)) {
                cardFound = true;
            } else if (recallType == RecallType.EXHAUST && c.exhaust) {
                cardFound = true;
            }
            if (cardFound) {
                retVal = c;
            }
        }
        return retVal;
    }

    public static enum RecallType{
        RADIANCE,
        SPEND,
        QUOTATIONS,
        EXHAUST,
        EPHEMERAL,
        GLARING
    }
}
