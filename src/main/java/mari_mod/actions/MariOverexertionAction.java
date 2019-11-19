package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariOverexertionAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariOverexertionAction.class.getName());

    public MariOverexertionAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        for(AbstractCard c: p.hand.group){
            if(c.costForTurn >= 2){
                c.costForTurn--;
            }

            if(c.cost >= 2){
                c.cost--;
                c.isCostModified = true;
            }
        }
        this.isDone = true;
    }
}
