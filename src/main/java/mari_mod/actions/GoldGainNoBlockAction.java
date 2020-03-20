package mari_mod.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;
import mari_mod.MariMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GoldGainNoBlockAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(GoldGainNoBlockAction.class.getName());

    private static final UIStrings fail = CardCrawlGame.languagePack.getUIString("GoldGainConditionFails");
    private static String failText = fail.TEXT[2];
    private boolean success;
    public GoldGainNoBlockAction(int amount, boolean successful) {
        this.amount = amount;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.target = AbstractDungeon.player;
        this.success = successful;
    }

    public void update() {
        logger.info("YOUR BLOCK (in action): " + AbstractDungeon.player.currentBlock);
        if (this.success){
            MariMod.gainGold(this.amount);
        }else{
            AbstractDungeon.effectList.add(new TextAboveCreatureEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 1.25F, failText, Color.RED.cpy()));
        }
        AbstractDungeon.player.hand.applyPowers();
        AbstractDungeon.player.hand.glowCheck();

        this.isDone = true;
    }
}
