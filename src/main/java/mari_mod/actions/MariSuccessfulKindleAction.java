package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.cards.AbstractMariCard;
import mari_mod.powers.OnSuccessfulKindlePower;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class MariSuccessfulKindleAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariSuccessfulKindleAction.class.getName());
    public AbstractCreature target;
    public AbstractMariCard kindleCard;
    public ArrayList<AbstractGameAction> actions;

    public MariSuccessfulKindleAction(AbstractCreature target, ArrayList<AbstractGameAction> actions, AbstractMariCard card) {
        this.actionType = ActionType.SPECIAL;
        this.target = target;
        this.actions = actions;
        this.kindleCard = card;
    }

    public MariSuccessfulKindleAction(AbstractCreature target, AbstractGameAction action, AbstractMariCard card) {
        this.actionType = ActionType.SPECIAL;
        this.target = target;
        this.actions = new ArrayList<>();
        actions.add(action);
        this.kindleCard = card;
    }

    public void update() {
        if(target != null && target.hasPower(Radiance_Power.POWER_ID) && target.getPower(Radiance_Power.POWER_ID).amount >= 1){
            for(AbstractGameAction a: actions) {
                AbstractDungeon.actionManager.addToTop(a);
            }
            this.kindleCard.successfulKindle(target);


            ///

            AbstractPlayer p = AbstractDungeon.player;

            for(AbstractPower power: p.powers) {
                if (power instanceof OnSuccessfulKindlePower) {
                    ((OnSuccessfulKindlePower) power).onSuccessfulKindle(p, this.target);
                }
            }

            ///

            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(target, AbstractDungeon.player, target.getPower(Radiance_Power.POWER_ID),1));
        }
        this.isDone = true;
    }
}
