package mari_mod.powers;


import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;
import com.megacrit.cardcrawl.vfx.combat.PowerDebuffEffect;
import mari_mod.MariMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Gold_Gain_Not_Damaged_Power extends AbstractPower
{
    public static final String POWER_ID = "MariMod:Gold_Gain_Not_Damaged_Power";
    private static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    private static final UIStrings fail = CardCrawlGame.languagePack.getUIString("GoldGainConditionFails");
    private static String failText = fail.TEXT[0];
    public Gold_Gain_Not_Damaged_Power(AbstractCreature owner, int bufferAmt)
    {
        this.name = NAME;
        this.type = POWER_TYPE;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = bufferAmt;
        this.isTurnBased = false;
        this.updateDescription();
        MariMod.setPowerImages(this);
    }

    public void stackPower(int stackAmount)
    {
        logger.info("this stacks: " + stackAmount);
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    @Override
    public int onLoseHp(int damageAmount) {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        AbstractDungeon.effectList.add(new TextAboveCreatureEffect(this.owner.hb.cX - this.owner.animX, this.owner.hb.cY + this.owner.hb.height / 1.25F, failText, Color.RED.cpy()));
        return super.onLoseHp(damageAmount);
    }

    /*public int onAttacked(DamageInfo damageInfo, int damage) {
        if(damage > AbstractDungeon.player.currentBlock || (damage > 0 && damageInfo.type == DamageInfo.DamageType.HP_LOSS)) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
        return super.onAttacked(damageInfo, damage);
    }*/

    @Override
    public void atStartOfTurnPostDraw() {
        MariMod.gainGold(this.amount);
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }

    public void updateDescription() {
        this.description = DESCRIPTION[0] + this.amount + DESCRIPTION[1];
    }
}
