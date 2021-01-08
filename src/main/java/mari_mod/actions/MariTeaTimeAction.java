package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.powers.The_Tea_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;

public class MariTeaTimeAction extends AbstractGameAction {
    private DamageInfo info;
    public static final Logger logger = LogManager.getLogger(MariTeaTimeAction.class.getName());
    public The_Tea_Power tea;

    public MariTeaTimeAction(The_Tea_Power tea) {
        this.actionType = ActionType.POWER;
        this.tea = tea;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        Iterator itr = p.powers.iterator();
        while(itr.hasNext()){
            AbstractPower power = (AbstractPower)itr.next();
            if(power.type == AbstractPower.PowerType.DEBUFF && !power.ID.equals(The_Tea_Power.POWER_ID)){
                tea.storedPowers.add(power);
                itr.remove();
            }
        }
        tea.updateDescription();
        this.isDone = true;
    }
}
