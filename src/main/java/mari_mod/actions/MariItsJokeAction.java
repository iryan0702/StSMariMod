package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariItsJokeAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariItsJokeAction.class.getName());
    public AbstractMonster monster;
    public int hpLossThreashold;
    public int healAmount;

    public MariItsJokeAction(AbstractMonster monster, int originalHP, int hpLossThreashold, int healAmount) {
        this.actionType = ActionType.HEAL;
        this.monster = monster;
        this.amount = originalHP;
        this.hpLossThreashold = hpLossThreashold;
        this.healAmount = healAmount;
    }

    public void update() {
        if(!monster.halfDead && !monster.isDead && this.amount - this.monster.currentHealth >= hpLossThreashold) {
            AbstractDungeon.actionManager.addToTop(new HealAction(this.monster, AbstractDungeon.player, healAmount));
        }
        this.isDone = true;
    }
}
