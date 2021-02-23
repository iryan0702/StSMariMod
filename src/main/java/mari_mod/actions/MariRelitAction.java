package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.powers.Grand_Scheme_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariRelitAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariRelitAction.class.getName());


    public MariRelitAction() {
        this.actionType = ActionType.POWER;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;

        procCharacter(p);
        for(AbstractMonster m: AbstractDungeon.getCurrRoom().monsters.monsters){
            procCharacter(m);
        }

        this.isDone = true;
    }

    public static void procCharacter(AbstractCreature c){
        AbstractPower p = c.getPower(Grand_Scheme_Power.POWER_ID);
        if(p != null){
            p.onSpecificTrigger();
        }
    }
}
