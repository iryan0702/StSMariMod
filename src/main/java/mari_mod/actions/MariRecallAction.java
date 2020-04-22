package mari_mod.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mari_mod.cards.AbstractMariCard;
import mari_mod.cards.MariCustomTags;
import mari_mod.cards.OnRecallCard;
import mari_mod.cards.PurgeOnRecallCard;
import mari_mod.patches.EphemeralCardPatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariRecallAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariRecallAction.class.getName());
    public static AbstractCard recalledCard;
    public AbstractGameAction followUpAction;
    public AbstractCard recallCard;
    private AbstractPlayer p;

    public MariRecallAction(AbstractCard card) {
        this(card, null);
    }

    public MariRecallAction(AbstractCard card, AbstractGameAction followUpAction) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.recallCard = card;
        this.p = AbstractDungeon.player;
        this.followUpAction = followUpAction;
    }

    public void update() {
        AbstractCard c = findRecallTarget();
        recalledCard = c;

        if(c != null) {
            c.unfadeOut();
            c.current_x = Settings.WIDTH * 2;
            c.current_y = Settings.HEIGHT / 2f;

            if( c instanceof PurgeOnRecallCard){
                c.target_x = Settings.WIDTH / 4f;
                c.target_y = Settings.HEIGHT / 2f;
                c.current_x = Settings.WIDTH / 4f;
                c.current_y = Settings.HEIGHT / 2f;
                p.limbo.addToTop(c);
                if (c instanceof OnRecallCard) {
                    ((OnRecallCard) c).onRecall();
                }
                addToTop(new MariPurgeSpecificCardAction(c,AbstractDungeon.player.limbo,false));
            }else {
                if (p.hand.size() >= BaseMod.MAX_HAND_SIZE) {
                    p.drawPile.moveToDiscardPile(c);
                    p.createHandIsFullDialog();
                } else {
                    p.hand.addToHand(c);
                }

                c.fadingOut = false;
                p.exhaustPile.removeCard(c);

                p.hand.refreshHandLayout();
                p.hand.applyPowers();

                if (c instanceof OnRecallCard) {
                    ((OnRecallCard) c).onRecall();
                }
            }
        }

        if(followUpAction != null){
            addToTop(followUpAction);
        }

        this.isDone = true;
    }

    public static AbstractCard findRecallTarget(){
        AbstractPlayer p = AbstractDungeon.player;
        boolean cardFound = false;
        AbstractCard retVal = null;
        for (int i = 0; i < p.exhaustPile.size() && !cardFound; i++) {
            AbstractCard c = p.exhaustPile.group.get(i);
            if(c.hasTag(MariCustomTags.GLARING)){
                cardFound = true;
            }
            if (cardFound) {
                retVal = c;
            }
        }
        for (int i = 0; i < p.exhaustPile.size() && !cardFound; i++) {
            AbstractCard c = p.exhaustPile.group.get(i);
            if(c.hasTag(MariCustomTags.RADIANCE)){
                cardFound = true;
            }
            if (cardFound) {
                retVal = c;
            }
        }
        return retVal;
    }

    public static enum RecallType{
    }
}
