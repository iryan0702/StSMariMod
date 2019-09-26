package mari_mod.powers;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import mari_mod.MariMod;
import mari_mod.actions.DrawPileToHandByTagAction;
import mari_mod.cards.MariCustomTags;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class The_Light_We_Chase_Power extends AbstractPower
{
    public static final String POWER_ID = "MariMod:The_Light_We_Chase_Power";
    private static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public The_Light_We_Chase_Power(AbstractCreature owner, int bufferAmt)
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
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        AbstractPlayer p = AbstractDungeon.player;
        if(power.ID.equals(Radiance_Power.POWER_ID) && !target.isPlayer){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new Radiance_Power(p,1),1));
        }
    }

    /*
    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (damageAmount >= this.amount && target != this.owner && info.type == DamageInfo.DamageType.NORMAL) {
            this.flashWithoutSound();
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, this.owner, new Radiance_Power(target, 1), 1, true));
        }
        super.onAttack(info, damageAmount, target);
    }*/

    public void updateDescription() {
        /*if(this.amount == 1) {
            this.description = DESCRIPTION[0];
        }else{
            this.description = DESCRIPTION[1] + this.amount + DESCRIPTION[2];
        }*/
        this.description = DESCRIPTION[0] + this.amount + DESCRIPTION[1];
    }
}
