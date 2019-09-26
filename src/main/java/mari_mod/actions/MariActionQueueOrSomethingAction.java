package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.util.ArrayList;

public class MariActionQueueOrSomethingAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariActionQueueOrSomethingAction.class.getName());
    public ArrayList<AbstractGameAction> listOfActions;

    public MariActionQueueOrSomethingAction(ArrayList<AbstractGameAction> actions) {
        this.listOfActions = actions;
    }

    public void update() {

        logger.info("I AM " + this.getClass().getName());
        for(AbstractGameAction action: AbstractDungeon.actionManager.actions){
            logger.info("ACTIONS NOW: " + action.getClass().getName());
        }

        if(this.listOfActions.size() <= 0){
            this.isDone = true;
        }else{
            logger.info("MARI ACTION QUEUE! SIZE: " + this.listOfActions.size());

            AbstractDungeon.actionManager.addToBottom(this.listOfActions.get(0));
            AbstractDungeon.actionManager.addToBottom(new MariActionQueueOrSomethingAction(new ArrayList<>(this.listOfActions.subList(1, this.listOfActions.size()))));

        }

        this.isDone = true;
    }
}
