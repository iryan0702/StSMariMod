package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class MariLetsGoAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariLetsGoAction.class.getName());
    boolean upgraded;
    int energyOnUse;
    boolean freeToPlayOnce;
    AbstractPlayer p;

    public MariLetsGoAction(boolean upgraded, boolean freeToPlayOnce, int energyOnUse) {
        this.upgraded = upgraded;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        this.freeToPlayOnce = freeToPlayOnce;
        this.p = AbstractDungeon.player;
    }

    public void update() {
        int draw = EnergyPanel.totalCount;
        int energy;

        if (this.energyOnUse != -1) {
            draw = this.energyOnUse;
        }

        if (this.p.hasRelic("Chemical X")) {
            draw += 2;
            this.p.getRelic("Chemical X").flash();
        }

        energy = Math.max(0, draw - 1);
        if (this.upgraded) {
            ++draw;
        }

        if (draw > 0) {
            addToBot(new DrawCardAction(draw));
            addToBot(new GainEnergyAction(energy));

            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }

}
