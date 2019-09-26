//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import mari_mod.MariMod;
import mari_mod.powers.Gold_Gain_Start_Of_Turn_Power;
import mari_mod.powers.Radiance_Power;

public class MariGrandSchemeAction extends AbstractGameAction {
    private boolean freeToPlayOnce;
    private AbstractPlayer p;
    private int energyOnUse;
    private int goldCost;
    private int goldReturn;
    private boolean upgraded;

    public MariGrandSchemeAction(AbstractPlayer source, int goldCost, int goldReturn, int energyOnUse, boolean upgraded, boolean freeToPlayOnce) {
        this.p = source;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.DEBUFF;
        this.energyOnUse = energyOnUse;
        this.goldCost = goldCost;
        this.goldReturn = goldReturn;
        this.upgraded = upgraded;
        this.freeToPlayOnce = freeToPlayOnce;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }

        if (this.upgraded) {
            ++effect;
        }

        if (effect > 0) {
            for(int i = 0; i < effect; ++i) {
                if(!this.upgraded || i != 0) AbstractDungeon.actionManager.addToBottom(new MariSpendGoldAction(this.goldCost));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Gold_Gain_Start_Of_Turn_Power(p, this.goldReturn),this.goldReturn));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EnergizedPower(p, 1),1));
            }

            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}
