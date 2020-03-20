package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariFragileHopeAction extends AbstractGameAction {
    private DamageInfo info;
    public static final Logger logger = LogManager.getLogger(MariDolphinStrikeAction.class.getName());
    private AttackEffect attackEffect;

    public MariFragileHopeAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {
        int recalls = 0;
        for(AbstractPower power :AbstractDungeon.player.powers){
            if(power.type.equals(AbstractPower.PowerType.DEBUFF)) recalls++;
        }
        AbstractPlayer p = AbstractDungeon.player;
        if(recalls > 0) {
            AbstractDungeon.actionManager.addToTop(new MariRecallAction(MariRecallAction.RecallType.RADIANCE));
        }
        this.isDone = true;
    }
}