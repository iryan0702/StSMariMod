package mari_mod.powers;


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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseBlockPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseTempHpPower;

public class Practice_Outfit_Buff_Power extends AbstractPower

{
    public static final String POWER_ID = "MariMod:Practice_Outfit_Buff_Power";
    private static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
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

    public void stackPower(int stackAmount)
    {
        logger.info("this stacks: " + stackAmount);
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        if(this.amount < 1) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
    }

    @Override
    public int onLoseHp(int damageAmount) {

        reducePower(damageAmount);
        for(AbstractMonster m: AbstractDungeon.getCurrRoom().monsters.monsters){
            Practice_Outfit_Buff_Power p = (Practice_Outfit_Buff_Power)m.getPower(this.ID);
            if(p != null){
                p.reducePower(damageAmount);
            }
        }
        return 0;
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if(!this.owner.isPlayer) {
            Practice_Outfit_Buff_Power power;
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                power = (Practice_Outfit_Buff_Power) m.getPower(this.ID);
                if (power != null) {
                    power.reducePower(damageAmount);
                }
            }
            power = (Practice_Outfit_Buff_Power) AbstractDungeon.player.getPower(this.ID);
            if (power != null) {
                power.reducePower(damageAmount);
            }
            return 0;
        }
        return damageAmount;
    }

    public void updateDescription() {
        this.description = DESCRIPTION[0] + this.amount + DESCRIPTION[1];
    }
}
