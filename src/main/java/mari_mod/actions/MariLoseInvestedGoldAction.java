package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import mari_mod.MariMod;

import java.util.ArrayList;

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
