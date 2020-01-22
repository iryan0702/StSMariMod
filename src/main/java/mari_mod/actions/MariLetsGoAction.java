package mari_mod.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.patches.MariDrawnCardsTrackerPatch;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class MariLetsGoAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariLetsGoAction.class.getName());
    boolean upgraded;

    public MariLetsGoAction(int energyGain) {
        this.amount = energyGain;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {
        ArrayList<AbstractCard> cardsToFlash = new ArrayList<>();

        int energyGain = 0;
        for(AbstractCard c: MariDrawnCardsTrackerPatch.drawnCards){
            if(c.cost == 3) {
                energyGain += this.amount;
                cardsToFlash.add(c);
            }
        }
        if(energyGain > 0){
            AbstractDungeon.actionManager.addToTop(new GainEnergyAction(energyGain));
            AbstractDungeon.actionManager.addToTop(new CardFlashAction(cardsToFlash));
        }
        this.isDone = true;
    }
}
