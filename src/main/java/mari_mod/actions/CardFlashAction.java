package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.Iterator;
import java.util.UUID;

public class CardFlashAction extends AbstractGameAction {
    AbstractCard card;

    public CardFlashAction(AbstractCard c) {
        this.card = c;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        card.flash();

        this.isDone = true;
    }
}
