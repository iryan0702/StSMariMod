package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import mari_mod.MariMod;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class MariCharacterDevelopmentPowerCheckAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariCharacterDevelopmentPowerCheckAction.class.getName());
    private static final float DURATION = 0.01F;
    private AbstractPower sourcePower;
    private int amount;

    public MariCharacterDevelopmentPowerCheckAction(int strengthGain, AbstractPower sourcePower) {
        this.actionType = ActionType.SPECIAL;
        this.duration = DURATION;
        this.amount = strengthGain;
        this.sourcePower = sourcePower;
    }

    public void update() {
        ArrayList<AbstractPower> currPowers = new ArrayList<>(AbstractDungeon.player.powers);
        for(AbstractPower usedToHave : MariMod.recentPowers){
            if(!currPowers.contains(usedToHave) && usedToHave.type == AbstractPower.PowerType.DEBUFF && !((usedToHave.ID.equals(StrengthPower.POWER_ID) || usedToHave.ID.equals(DexterityPower.POWER_ID)) && usedToHave.amount == 0)){
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, this.amount), this.amount));
                this.sourcePower.flashWithoutSound();
            }
        }
        this.isDone = true;
    }
}
