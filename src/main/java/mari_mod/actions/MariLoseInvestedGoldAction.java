package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import mari_mod.MariMod;

public class MariLoseInvestedGoldAction extends AbstractGameAction {

    int percentLoss;

    public MariLoseInvestedGoldAction(int percentLoss) {
        this.percentLoss = percentLoss;
    }

    public void update() {
        MariMod.saveableKeeper.investedGold -= this.percentLoss * MariMod.saveableKeeper.investedGold * 0.01f;
        if(MariMod.saveableKeeper.investedGold < 0){
            MariMod.saveableKeeper.investedGold = 0;
        }
        this.isDone = true;
    }
}
