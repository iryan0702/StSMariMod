package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import mari_mod.MariMod;

public class MariLoseBrillianceAction extends AbstractGameAction {

    int percentLoss;

    public MariLoseBrillianceAction(int percentLoss) {
        this.percentLoss = percentLoss;
    }

    public void update() {
        MariMod.saveableKeeper.brilliance -= this.percentLoss * MariMod.saveableKeeper.brilliance * 0.01f;
        if(MariMod.saveableKeeper.brilliance < 0){
            MariMod.saveableKeeper.brilliance = 0;
        }
        this.isDone = true;
    }
}
