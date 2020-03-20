//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MariReducePowerIfHavePowerAction extends AbstractGameAction {
    private String powerID;
    private AbstractCreature source;

    public MariReducePowerIfHavePowerAction(AbstractCreature target, AbstractCreature source, String powerID, int amount) {

        this.target = target;
        this.source = source;
        this.amount = amount;
        this.powerID = powerID;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        if(this.target.hasPower(this.powerID)){
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.target, this.source, this.powerID, this.amount));
        }
        this.isDone = true;
    }
}
