package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class MariExpensiveTastesAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariExpensiveTastesAction.class.getName());
    private static final float DURATION = 0.1F;
    public ArrayList<AbstractCard> animationDone = new ArrayList<>();
    public int updateTick = 0;

    public MariExpensiveTastesAction() {
        this.duration = DURATION;
    }

    public void update() {

        if(AbstractDungeon.player.drawPile.size() > 0){
            AbstractCard c = AbstractDungeon.player.drawPile.getTopCard();
            if (c.cost > 0) {
                c.freeToPlayOnce = true;
            }
        }

        this.tickDuration();
    }
}
