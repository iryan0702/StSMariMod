package mari_mod.powers;


import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
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


public class Lock_On_Power extends AbstractPower
{
    public static final String POWER_ID = "MariMod:Lock_On_Power";
    private static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    private AbstractMonster target;
    public Lock_On_Power(AbstractCreature owner, int bufferAmt, AbstractMonster target, PowerType powerType)
    {
        this.name = NAME;
        this.type = powerType;
        this.ID = POWER_ID;
        this.owner = owner;
        this.isTurnBased = false;
        this.target = target;
        this.amount = bufferAmt;
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
    public void atEndOfRound() {
        super.atEndOfRound();
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }


    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if(m != null && m.equals(this.target)){
            AbstractDungeon.actionManager.addToBottom(new MariDelayedActionAction(new GainEnergyAction(this.amount)));
            this.flash();
        }
    }

    @Override
    public void onDeath() {
        super.onDeath();
        AbstractPlayer p = AbstractDungeon.player;
        if(!this.owner.isPlayer && p.hasPower(this.ID)){
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, this.ID));
        }
    }

    public void updateDescription() {
        if(this.owner.isPlayer) {
            this.description = DESCRIPTION[0] + this.target.name + DESCRIPTION[1] + this.amount + DESCRIPTION[2];
        }else{
            this.description = DESCRIPTION[3];
        }
    }
}
