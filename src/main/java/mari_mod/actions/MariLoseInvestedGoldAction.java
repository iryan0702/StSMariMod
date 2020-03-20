package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import mari_mod.MariMod;

public class MariLoseInvestedGoldAction extends AbstractGameAction {

    int percentLoss;

    public MariLoseInvestedGoldAction(int percentLoss) {
        this.percentLoss = percentLoss;
    }

    public void update() {
        MariMod.saveableKeeper.goldInvested -= this.percentLoss * MariMod.saveableKeeper.goldInvested * 0.01f;
        if(MariMod.saveableKeeper.goldInvested < 0){
            MariMod.saveableKeeper.goldInvested = 0;
        }
        this.isDone = true;
    }
}
