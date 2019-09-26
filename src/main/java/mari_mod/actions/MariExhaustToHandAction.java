//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariExhaustToHandAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariExhaustToHandAction.class.getName());
    AbstractCard c;

    public MariExhaustToHandAction(AbstractCard c) {
        this.c = c;
    }

    public void update() {

        if(AbstractDungeon.player.exhaustPile.contains(c)) {
            c.unfadeOut();
            AbstractDungeon.player.hand.addToHand(c);
            AbstractDungeon.player.exhaustPile.removeCard(c);
            AbstractDungeon.player.hand.refreshHandLayout();
            c.unhover();
            c.fadingOut = false;
        }
        this.isDone = true;
    }
}
