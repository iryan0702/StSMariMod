package mari_mod.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import mari_mod.MariMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

public class MariSetCostReductionOnDrawAction extends AbstractGameAction {
    UUID uuid;
    private static final int MAX = BaseMod.MAX_HAND_SIZE;
    private static final Logger logger = LogManager.getLogger(DrawCardAction.class.getName());
    public int minCost;

    public MariSetCostReductionOnDrawAction(int amount, int minCost) {
        this.amount = amount;
        this.minCost = minCost;
    }

    public void update() {

        MariMod.minimumCostAfterReductionOnDraw = this.minCost;
        MariMod.costReductionOnDraw = this.amount;
        this.isDone = true;
    }
}
