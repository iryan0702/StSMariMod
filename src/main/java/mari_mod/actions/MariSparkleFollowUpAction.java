package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Deprecated
public class MariSparkleFollowUpAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariSparkleFollowUpAction.class.getName());
    private static final float DURATION = 0.01F;
    public AbstractCreature target;
    public AbstractMonster m;
    public boolean faded;

    public MariSparkleFollowUpAction(AbstractMonster target, boolean faded) {
        this.target = target;
        this.m = target;
        this.faded = faded;
    }

    public void update() {
        int totalCost = 0;
        AbstractPlayer p = AbstractDungeon.player;

        for(AbstractCard c: DrawCardAction.drawnCards){
            int cost = c.costForTurn;
            if(c.cost == -1) cost = c.energyOnUse;
            if(c.freeToPlayOnce) cost = 0;

            totalCost += cost;
        }
        if(faded){
            totalCost = Math.min(1, totalCost);
        }

        if(totalCost > 0) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new Radiance_Power(m, totalCost), totalCost));
        }
        this.isDone = true;
    }
}
