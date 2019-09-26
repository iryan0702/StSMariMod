package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import mari_mod.MariMod;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class GoldGainDebuffAction extends AbstractGameAction {

    public GoldGainDebuffAction(int amount) {
        this.amount = amount;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        boolean hasDebuff = false;
        ArrayList<AbstractPower> powerCheck = AbstractDungeon.player.powers;
        for(AbstractPower powerToCheck : powerCheck){
            if(powerToCheck.type == AbstractPower.PowerType.DEBUFF) hasDebuff = true;
        }
        if(hasDebuff){
            MariMod.gainGold(this.amount);
        }
        AbstractDungeon.player.hand.applyPowers();
        AbstractDungeon.player.hand.glowCheck();

        this.isDone = true;
    }
}
