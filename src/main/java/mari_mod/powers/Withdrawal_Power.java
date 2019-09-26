package mari_mod.powers;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import mari_mod.MariMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Withdrawal_Power extends AbstractPower
{
    public static final String POWER_ID = "MariMod:Withdrawal_Power";
    private static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public Withdrawal_Power(AbstractCreature owner)
    {
        this.name = NAME;
        this.type = POWER_TYPE;
        this.ID = POWER_ID;
        this.owner = owner;
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

    public float modifyBlock(float blockAmount) {
        AbstractPlayer p = AbstractDungeon.player;
        if(p.hasPower(VulnerablePower.POWER_ID)) {
            return blockAmount + p.getPower(VulnerablePower.POWER_ID).amount;
        }
        return blockAmount;
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        AbstractPlayer p = AbstractDungeon.player;
        if(p.hasPower(VulnerablePower.POWER_ID) && type == DamageInfo.DamageType.NORMAL) {
            return damage + p.getPower(VulnerablePower.POWER_ID).amount;
        }
        return damage;
    }

    public void updateDescription() {
        AbstractPlayer p = AbstractDungeon.player;
        int V = 0;
        if(p.hasPower(VulnerablePower.POWER_ID)) {
            V = AbstractDungeon.player.getPower(VulnerablePower.POWER_ID).amount;
        }
        this.description = DESCRIPTION[0] + V + DESCRIPTION[1];
    }
}
