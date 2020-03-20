package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mari_mod.cards.AbstractMariCard;

import java.util.UUID;

public class MariIncreaseRadianceInHandAction extends AbstractGameAction {
    UUID uuid;

    public MariIncreaseRadianceInHandAction(int amount) {
        this.amount = amount;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {

        for(AbstractCard c: AbstractDungeon.player.hand.group){
            if(c instanceof AbstractMariCard){
                ((AbstractMariCard)c).radiance += this.amount;
                ((AbstractMariCard)c).upgradedRadiance = true;
                c.flash();
            }
        }

        this.isDone = true;
    }
}
