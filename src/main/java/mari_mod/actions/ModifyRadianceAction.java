package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import mari_mod.cards.AbstractMariCard;
import mari_mod.relics.AbstractMariRelic;

import java.util.Iterator;
import java.util.UUID;

public class ModifyRadianceAction extends AbstractGameAction {
    UUID uuid;

    public ModifyRadianceAction(UUID targetUUID, int amount) {
        this.uuid = targetUUID;
        this.amount = amount;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        boolean firstCastCheck = true;
        for(AbstractCard c : GetAllInBattleInstances.get(this.uuid)){
            if(firstCastCheck && ! (c instanceof AbstractMariCard)){
                break;
            }
            firstCastCheck = false;
            ((AbstractMariCard) c).baseRadiance += this.amount;
            if(((AbstractMariCard) c).baseRadiance < 0){
                ((AbstractMariCard) c).baseRadiance = 0;
            }
        }

        this.isDone = true;
    }
}
