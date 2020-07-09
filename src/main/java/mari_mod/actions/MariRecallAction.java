package mari_mod.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import mari_mod.cards.AbstractMariCard;
import mari_mod.cards.MariCustomTags;
import mari_mod.cards.OnRecallCard;
import mari_mod.cards.PurgeOnRecallCard;
import mari_mod.patches.EphemeralCardPatch;
import mari_mod.powers.OnRecallPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariRecallAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariRecallAction.class.getName());
    public static AbstractCard recalledCard;
    public AbstractGameAction followUpAction;
    private AbstractPlayer p;

    public MariRecallAction() {
        this(null);
    }

    public MariRecallAction(AbstractGameAction followUpAction) {
        this.actionType = ActionType.CARD_MANIPULATION;
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
                    p.createHandIsFullDialog();
                    recalledCard = null;
                } else {
                    p.hand.addToHand(c);

                    c.fadingOut = false;
                    p.exhaustPile.removeCard(c);

                    p.hand.refreshHandLayout();
                    p.hand.applyPowers();

                    for(AbstractRelic r: p.relics){
                        if(r instanceof OnRecallPower){
                            ((OnRecallPower)r).onRecallCard(c);
                        }
                    }

                    if (c instanceof OnRecallCard) {
                        ((OnRecallCard) c).onRecall();
                    }
                }
            }
        }

        if(followUpAction != null){
            addToTop(followUpAction);
        }

        this.isDone = true;
    }

    public static AbstractCard findRecallTarget(){
        return findRecallTarget(AbstractDungeon.player.exhaustPile, 1);
    }

    public static AbstractCard findRecallTarget(CardGroup group){
        return findRecallTarget(group, 1);
    }

    public static AbstractCard findRecallTarget(int targetCard){
        return findRecallTarget(AbstractDungeon.player.exhaustPile, targetCard);
    }

    // TODO: Recall target currently assumes GLARING/FADING RADIANCE/FADED RADIANCE are mutually exclusive sections, watch out!
    public static AbstractCard findRecallTarget(CardGroup group, int targetCard){
        int ithCardFound = 0;

        for (int i = 0; i < group.size(); i++) {
            AbstractCard c = group.group.get(i);
            if(c.hasTag(MariCustomTags.GLARING)){
                ithCardFound++;
                if (ithCardFound == targetCard) {
                    return c;
                }
            }
        }
        for (int i = 0; i < group.size(); i++) {
            AbstractCard c = group.group.get(i);
            if(EphemeralCardPatch.EphemeralField.ephemeral.get(c) && c.hasTag(MariCustomTags.RADIANCE) && c instanceof AbstractMariCard && !((AbstractMariCard)c).faded){
                ithCardFound++;
                if (ithCardFound == targetCard) {
                    return c;
                }
            }
        }
        for (int i = 0; i < group.size(); i++) {
            AbstractCard c = group.group.get(i);
            if(EphemeralCardPatch.EphemeralField.ephemeral.get(c) && c.hasTag(MariCustomTags.RADIANCE) && c instanceof AbstractMariCard && ((AbstractMariCard)c).faded){
                ithCardFound++;
                if (ithCardFound == targetCard) {
                    return c;
                }
            }
        }
        return null;
    }
}
