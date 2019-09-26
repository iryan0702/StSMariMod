package mari_mod.powers;


import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.MariMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseBlockPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseTempHpPower;

public class Practice_Outfit_Buff_Power extends AbstractPower implements OnLoseTempHpPower, OnLoseBlockPower

{
    public static final String POWER_ID = "MariMod:Practice_Outfit_Buff_Power";
    private static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    private boolean aboutToExpire;
    private boolean playerTurn;
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
        playerTurn = true;
        if(this.amount == 1){
            aboutToExpire = true;
        }else{
            aboutToExpire = false;
        }
    }

    public void stackPower(int stackAmount)
    {
        logger.info("this stacks: " + stackAmount);
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if(this.amount == 1){
            aboutToExpire = true;
        }else{
            aboutToExpire = false;
        }
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        if(this.amount == 1 && playerTurn){
            aboutToExpire = true;
        }else{
            aboutToExpire = false;
        }
    }

    @Override
    public void atStartOfTurn() {
        playerTurn = true;
        if(this.amount == 1){
            aboutToExpire = true;
        }else{
            aboutToExpire = false;
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        playerTurn = false;
        AbstractPlayer p = AbstractDungeon.player;
        if(isPlayer && p.hasPower(this.ID)){
            if(this.amount <= 1) {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, this.ID));
            }else {
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
            }
        }
    }


    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        if(aboutToExpire){
            return damage;
        }
        return 0;
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        return 0;
    }

    @Override
    public int onLoseTempHp(DamageInfo damageInfo, int damageAmount) {
        return 0;
    }

    @Override
    public int onLoseBlock(DamageInfo damageInfo, int damageAmount) {
        return 0;
    }

    public void updateDescription() {
        if(this.amount == 1) {
            this.description = DESCRIPTION[0];
        }else{
            this.description = DESCRIPTION[1] + this.amount + DESCRIPTION[2];
        }
    }
}
