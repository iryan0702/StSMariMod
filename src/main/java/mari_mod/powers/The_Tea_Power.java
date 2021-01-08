package mari_mod.powers;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.MariMod;
import mari_mod.actions.MariTeaTimeAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;


public class The_Tea_Power extends AbstractPower
{
    public static final String POWER_ID = "MariMod:The_Tea_Power";
    private static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static PowerType POWER_TYPE = PowerType.DEBUFF;
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.DESCRIPTIONS;
    public ArrayList<AbstractPower> storedPowers;
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public The_Tea_Power(AbstractCreature owner)
    {
        this.name = NAME;
        this.type = POWER_TYPE;
        this.ID = POWER_ID;
        this.owner = owner;
        this.isTurnBased = false;
        this.storedPowers = new ArrayList<>();
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
    public void onInitialApplication() {
        super.onInitialApplication();
        storedPowers.clear();
        addToTop(new MariTeaTimeAction(this));
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        AbstractPlayer p = AbstractDungeon.player;
        if(isPlayer){
            for(AbstractPower power: storedPowers){
                if(power.amount != 0){
                    addToBot(new ApplyPowerAction(p, p, power, power.amount));
                }else {
                    addToBot(new ApplyPowerAction(p, p, power));
                }
            }
            storedPowers.clear();
        }
        addToBot(new RemoveSpecificPowerAction(p, p, this));
    }

    public void updateDescription() {
        if(storedPowers.size() <= 0){
            this.description = DESCRIPTION[5]; //None string
        }else {
            this.description = DESCRIPTION[0]; //Intro string
            AbstractPower power = storedPowers.get(0);
            if(power.amount >= 0 || power.canGoNegative){
                this.description += DESCRIPTION[1]; //Number coloring
                this.description += power.amount;
                this.description += DESCRIPTION[2]; //Space
            }
            this.description += power.name;

            for(int i = 1; i < storedPowers.size(); i++){
                power = storedPowers.get(i);
                this.description += DESCRIPTION[3]; //Comma string

                if(power.amount >= 0 || power.canGoNegative){
                    this.description += DESCRIPTION[1];
                    this.description += power.amount;
                    this.description += DESCRIPTION[2];
                }
                this.description += power.name;
            }

            this.description += DESCRIPTION[4]; //Period string
        }
    }
}
