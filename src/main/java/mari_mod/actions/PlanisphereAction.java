//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import mari_mod.cards.Mari_The_Planisphere;
import mari_mod.powers.Radiance_Power;

public class PlanisphereAction extends AbstractGameAction {
    private boolean retrieveCard = false;
    private CardType cardType = null;

    public PlanisphereAction(AbstractMonster targetMonster) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.target = targetMonster;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.cardType == null) {
                AbstractDungeon.cardRewardScreen.discoveryOpen();
            } else {
                AbstractDungeon.cardRewardScreen.discoveryOpen(this.cardType);
            }

            this.tickDuration();
        } else {
            if (!this.retrieveCard) {
                if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                    AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                    disCard.current_x = -1000.0F * Settings.scale;
                    if (AbstractDungeon.player.hand.size() < 10) {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    } else {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    }

                    int damage = (disCard.cost == -1) ? EnergyPanel.getCurrentEnergy() : disCard.cost;

                    if(damage >= 0) {
                        if (damage > 0) {
                            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, AbstractDungeon.player, new Radiance_Power(target, damage), damage));
                        }
                        AbstractDungeon.actionManager.addToTop(new MariImmediatelyDealPowerAppliedDamageAction(target, new DamageInfo(AbstractDungeon.player, damage), AttackEffect.FIRE));
                    }
                    AbstractDungeon.cardRewardScreen.discoveryCard = null;
                }

                this.retrieveCard = true;
            }

            this.tickDuration();
        }
    }
}
