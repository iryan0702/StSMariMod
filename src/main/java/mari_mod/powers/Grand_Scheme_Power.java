package mari_mod.powers;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import mari_mod.MariMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;


public class Grand_Scheme_Power extends AbstractPower
{
    public static final String POWER_ID = "MariMod:Grand_Scheme_Power";
    private static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public ArrayList<Integer> radianceAmounts = new ArrayList<>();
    public int expectedDamage = 0;
    public int expectedCount = 0;
    public int expectedPermanentCount = 0;
    public Grand_Scheme_Power(AbstractCreature owner)
    {
        this.name = NAME;
        this.type = POWER_TYPE;
        this.ID = POWER_ID;
        this.owner = owner;
        this.isTurnBased = false;
        MariMod.setPowerImages(this);
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();

        AbstractPower p = owner.getPower(Radiance_Power.POWER_ID);
        if(p != null){
            radianceAmounts.add(p.amount);
            addToBot(new RemoveSpecificPowerAction(owner, AbstractDungeon.player, p));
        }

        this.updatedStats();
    }

    @Override
    public void onSpecificTrigger() {
        super.onSpecificTrigger();

        addToBot(new RemoveSpecificPowerAction(owner, AbstractDungeon.player, this));
        for(Integer i: radianceAmounts){
            addToBot(new ApplyPowerAction(owner, AbstractDungeon.player, new Radiance_Power(owner, i), i));
        }
    }

    public void newRadiance(int amount){
        radianceAmounts.add(amount);
        this.updatedStats();
    }

    public void updatedStats(){

        AbstractPower p = owner.getPower(ArtifactPower.POWER_ID);
        int artifactAmount = p != null ? p.amount : 0;

        expectedDamage = 0;
        expectedCount = 0;
        int applyTimes = 0;
        expectedPermanentCount = 0;

        for(Integer i: radianceAmounts){
            if(artifactAmount > 0){
                artifactAmount--;
            }else{
                applyTimes++;
                expectedCount += i;
                expectedDamage += expectedCount;
            }
        }
        expectedPermanentCount = expectedCount - applyTimes;

        if(this.owner.isPlayer){
            this.amount = this.expectedCount;
        }else{
            this.amount = this.expectedDamage;
        }

        updateDescription();
    }

    @Override
    public void updateDescription() {
        int size = radianceAmounts.size();

        StringBuilder builder = new StringBuilder(DESCRIPTION[0]);

        if(size <= 0){
            builder.append(0);
        }else{
            for(int i = 0; i < size; i++){
                builder.append(radianceAmounts.get(i));
                if(i != size - 1) builder.append(DESCRIPTION[1]);
            }
        }
        builder.append(DESCRIPTION[2]);

        if(!this.owner.isPlayer){
            builder.append(DESCRIPTION[3]);
            builder.append(expectedDamage);
        }
        builder.append(DESCRIPTION[4]);
        builder.append(expectedCount);
        builder.append(DESCRIPTION[5]);
        builder.append(expectedPermanentCount);
        builder.append(DESCRIPTION[6]);

        this.description = builder.toString();
    }
}
