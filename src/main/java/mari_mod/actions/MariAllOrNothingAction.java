package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mari_mod.powers.Gold_Gain_End_Of_Combat_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariAllOrNothingAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariAllOrNothingAction.class.getName());
    private static final float DURATION = 0.01F;

    public MariAllOrNothingAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = 0.01F;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        addToTop(new ApplyPowerAction(p, p, new Gold_Gain_End_Of_Combat_Power(p, p.gold), p.gold));
        p.loseGold(p.gold);
        this.isDone = true;
    }
}
