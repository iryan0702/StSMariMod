package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

import java.util.ArrayList;

public class CardFlashAction extends AbstractGameAction {
    ArrayList<AbstractCard> cards;

    public CardFlashAction(ArrayList<AbstractCard> cards) {
        this.cards = new ArrayList<>(cards);
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public CardFlashAction(AbstractCard c) {
        this.cards = new ArrayList<>();
        cards.add(c);
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        for(AbstractCard c: cards) {
            c.flash();
        }
        this.isDone = true;
    }
}
