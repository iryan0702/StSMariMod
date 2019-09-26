package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class MariIdolizeAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariIdolizeAction.class.getName());

    public MariIdolizeAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {
        for(AbstractCard cardToCheckAndMakeShiny : AbstractDungeon.player.drawPile.group){
            for(AbstractCard cardToCheckAgainstShiny : AbstractDungeon.player.drawPile.group){
                if(cardToCheckAndMakeShiny != cardToCheckAgainstShiny && cardToCheckAndMakeShiny.cardID.equals(cardToCheckAgainstShiny.cardID)){
                    if (cardToCheckAndMakeShiny.canUpgrade() && cardToCheckAndMakeShiny.canUpgrade()) {
                        cardToCheckAndMakeShiny.upgrade();
                        cardToCheckAndMakeShiny.superFlash();
                    }
                }
            }
        }
        this.isDone = true;
    }
}
