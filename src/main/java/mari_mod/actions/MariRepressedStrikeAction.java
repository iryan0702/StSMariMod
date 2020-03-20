package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariRepressedStrikeAction extends AbstractGameAction {
    private DamageInfo info;
    public static final Logger logger = LogManager.getLogger(MariRepressedStrikeAction.class.getName());

    public MariRepressedStrikeAction(AbstractCreature target, int damage) {
        this.target = target;
        this.amount = damage;
        this.actionType = ActionType.DAMAGE;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        for(AbstractCard c: p.hand.group) {
            if(!c.canUse(p, null)) {
                AbstractDungeon.actionManager.addToTop(new DamageAction(this.target, new DamageInfo(p, this.amount, DamageInfo.DamageType.NORMAL), AttackEffect.SMASH));
                addToTop(new DiscardSpecificCardAction(c, p.hand));
            }
        }
        this.isDone = true;
    }
}
