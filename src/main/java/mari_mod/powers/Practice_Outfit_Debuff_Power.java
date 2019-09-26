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


public class Practice_Outfit_Debuff_Power extends AbstractPower
{
    public static final String POWER_ID = "MariMod:Practice_Outfit_Debuff_Power";
    private static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static PowerType POWER_TYPE = PowerType.DEBUFF;
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public Practice_Outfit_Debuff_Power(AbstractCreature owner, int bufferAmt)
    {
        this.name = NAME;
        this.type = POWER_TYPE;
        this.ID = POWER_ID;
        this.amount = bufferAmt;
        this.owner = owner;
        this.isTurnBased = true;
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
    public void atEndOfTurn(boolean isPlayer) {
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
    public float atDamageFinalGive(float damage, DamageInfo.DamageType type) {
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
