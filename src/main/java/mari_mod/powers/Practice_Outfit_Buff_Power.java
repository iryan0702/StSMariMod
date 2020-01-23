package mari_mod.powers;


import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnMyBlockBrokenPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.MariMod;
import mari_mod.actions.MariDelayedActionAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseBlockPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseTempHpPower;

public class Practice_Outfit_Buff_Power extends AbstractPower implements OnMyBlockBrokenPower

{
    public static final String POWER_ID = "MariMod:Practice_Outfit_Buff_Power";
    private static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public static final int BLOCK_GAIN = 5;
    public static final int RADIANCE_GAIN = 1;
    public Practice_Outfit_Buff_Power(AbstractCreature owner, int bufferAmt)
    {
        this.name = NAME;
        this.type = POWER_TYPE;
        this.ID = POWER_ID;
        this.amount = bufferAmt;
        this.owner = owner;
        this.isTurnBased = true;
        priority = -99999;
        this.updateDescription();
        MariMod.setPowerImages(this);
    }

    @Override
    public void onMyBlockBroken() {
        this.flash();
        for(int i = 0; i < this.amount; i++){
            if(this.owner.isPlayer){
                addToTop(new GainBlockAction(this.owner, BLOCK_GAIN));
            }else {
                addToTop(new MariDelayedActionAction(new GainBlockAction(this.owner, BLOCK_GAIN)));
            }
        }
        for(int i = 0; i < this.amount; i++) addToTop(new ApplyPowerAction(this.owner, this.owner, new Radiance_Power(this.owner, RADIANCE_GAIN), RADIANCE_GAIN));
    }

    public void stackPower(int stackAmount)
    {
        logger.info("this stacks: " + stackAmount);
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    public void updateDescription() {
        this.description = DESCRIPTION[0] + RADIANCE_GAIN + DESCRIPTION[1] + BLOCK_GAIN + DESCRIPTION[2] + this.amount + DESCRIPTION[3];
    }
}
