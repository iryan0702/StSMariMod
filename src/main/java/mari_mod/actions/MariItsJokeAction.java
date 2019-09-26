package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariItsJokeAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariItsJokeAction.class.getName());
    public AbstractMonster monster;

    public MariItsJokeAction(AbstractMonster monster, int originalHP) {
        this.actionType = ActionType.HEAL;
        this.monster = monster;
        this.amount = originalHP;
    }

    public void update() {
        AbstractDungeon.actionManager.addToTop(new HealAction(this.monster, AbstractDungeon.player, this.amount - this.monster.currentHealth));
        this.isDone = true;
    }
}
