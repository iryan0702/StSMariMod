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
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import mari_mod.powers.Radiance_Power;

public class MariFireStrikeAction extends AbstractGameAction {
    private boolean freeToPlayOnce;
    private AbstractPlayer p;
    private int energyOnUse;
    private boolean upgraded;
    private int radiancePerX;

    public MariFireStrikeAction(AbstractPlayer source, AbstractCreature target, int energyOnUse, boolean upgraded, boolean freeToPlayOnce, int radiancePerX) {
        this.p = source;
        this.target = target;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.DEBUFF;
        this.energyOnUse = energyOnUse;
        this.upgraded = upgraded;
        this.source = AbstractDungeon.player;
        this.freeToPlayOnce = freeToPlayOnce;
        this.radiancePerX = radiancePerX;
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
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.target, this.source, new Radiance_Power(this.target, this.radiancePerX),this.radiancePerX));
            }

            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}
