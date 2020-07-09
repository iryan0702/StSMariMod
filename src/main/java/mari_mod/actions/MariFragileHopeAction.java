package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import mari_mod.cards.MariCustomTags;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariFragileHopeAction extends AbstractGameAction {
    private DamageInfo info;
    public static final Logger logger = LogManager.getLogger(MariDolphinStrikeAction.class.getName());
    private AttackEffect attackEffect;

    public MariFragileHopeAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {
        if(MariRecallAction.recalledCard != null && MariRecallAction.recalledCard.hasTag(MariCustomTags.GLARING)){
            addToTop(new MariRecallAction(new MariFragileHopeAction()));
        }
        this.isDone = true;
    }
}