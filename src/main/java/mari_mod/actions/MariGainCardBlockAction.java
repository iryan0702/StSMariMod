package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariGainCardBlockAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariGainCardBlockAction.class.getName());
    public AbstractCard card;

    public MariGainCardBlockAction(AbstractCard card) {
        this.actionType = ActionType.BLOCK;
        this.card = card;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        addToTop(new GainBlockAction(p, p, card.block));
        this.isDone = true;
    }
}
