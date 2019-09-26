package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariHandGlowCheckAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariHandGlowCheckAction.class.getName());
    private static final float DURATION = 0.01F;

    public MariHandGlowCheckAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = 0.01F;
    }

    public void update() {
        for(AbstractCard cardToCheck: AbstractDungeon.player.hand.group){
            if(cardToCheck.canUse(AbstractDungeon.player,null)){
                cardToCheck.beginGlowing();
            }else{
                cardToCheck.stopGlowing();
            }
        }
        this.isDone = true;
    }
}
