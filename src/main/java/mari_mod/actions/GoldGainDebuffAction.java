package mari_mod.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;
import mari_mod.MariMod;

import java.util.ArrayList;

public class GoldGainDebuffAction extends AbstractGameAction {
    private static final UIStrings fail = CardCrawlGame.languagePack.getUIString("GoldGainConditionFails");
    private static String failText = fail.TEXT[1];
    public GoldGainDebuffAction(int amount) {
        this.amount = amount;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.target = AbstractDungeon.player;
    }

    public void update() {
        boolean hasDebuff = false;
        ArrayList<AbstractPower> powerCheck = AbstractDungeon.player.powers;
        for(AbstractPower powerToCheck : powerCheck){
            if(powerToCheck.type == AbstractPower.PowerType.DEBUFF) hasDebuff = true;
        }
        if(hasDebuff){
            MariMod.gainGold(this.amount);
        }else{
            AbstractDungeon.effectList.add(new TextAboveCreatureEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 1.25F, failText, Color.RED.cpy()));
        }
        AbstractDungeon.player.hand.applyPowers();
        AbstractDungeon.player.hand.glowCheck();

        this.isDone = true;
    }
}
