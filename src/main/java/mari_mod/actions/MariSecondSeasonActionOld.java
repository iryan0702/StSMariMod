//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.cards.Mari_$Second_Light;
import mari_mod.powers.Radiance_Power;

import java.util.ArrayList;

public class MariSecondSeasonActionOld extends AbstractGameAction {
    private ArrayList<AbstractCard> list = new ArrayList<AbstractCard>();

    public MariSecondSeasonActionOld() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {

        AbstractPlayer p = AbstractDungeon.player;

        int totalRadiance = 0;
        for(AbstractMonster target: AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (target.hasPower(Radiance_Power.POWER_ID)) {
                totalRadiance +=target.getPower(Radiance_Power.POWER_ID).amount;
                AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(target, p, Radiance_Power.POWER_ID));
            }
        }
        if (p.hasPower(Radiance_Power.POWER_ID)) {
            totalRadiance +=p.getPower(Radiance_Power.POWER_ID).amount;
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(p, p, Radiance_Power.POWER_ID));
        }


        AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(new Mari_$Second_Light(totalRadiance)));

        this.isDone = true;
    }
}
