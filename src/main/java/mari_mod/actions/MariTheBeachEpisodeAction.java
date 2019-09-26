package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import mari_mod.MariMod;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class MariTheBeachEpisodeAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariTheBeachEpisodeAction.class.getName());
    public boolean upgraded;

    public MariTheBeachEpisodeAction(AbstractCreature target, boolean upgraded, int rewardAmount) {
        this.actionType = ActionType.BLOCK;
        this.target = target;
        this.amount = rewardAmount;
        this.upgraded = upgraded;
    }

    public void update() { //TODO: FIX ORDER AFTER CHANGING ADD "TO BOTTOM" TO "TO TOP"
        int rewardStacks = 0;
        int targetBlock = 0;
        int targetFrail = 0;
        AbstractPlayer p = AbstractDungeon.player;
        if(this.upgraded){
            for(AbstractMonster monster: AbstractDungeon.getCurrRoom().monsters.monsters){
                if(!(monster.isDead)) {
                    targetBlock += monster.currentBlock;
                    AbstractDungeon.actionManager.addToTop(new RemoveAllBlockAction(monster, p));
                    if (monster.hasPower(FrailPower.POWER_ID)) {
                        targetFrail += monster.getPower(FrailPower.POWER_ID).amount;
                        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(monster, p, FrailPower.POWER_ID));
                    }
                }
            }

            targetBlock += p.currentBlock;
            AbstractDungeon.actionManager.addToTop(new RemoveAllBlockAction(p, p));
            if (p.hasPower(FrailPower.POWER_ID)) {
                targetFrail += p.getPower(FrailPower.POWER_ID).amount;
                AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(p, p, FrailPower.POWER_ID));
            }
        }else {
            targetBlock += target.currentBlock;
            AbstractDungeon.actionManager.addToTop(new RemoveAllBlockAction(target, p));
            if (target.hasPower(FrailPower.POWER_ID)) {
                targetFrail += target.getPower(FrailPower.POWER_ID).amount;
                AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(target, p, FrailPower.POWER_ID));
            }
        }
        rewardStacks += targetBlock/5;
        rewardStacks += targetFrail;
        if(rewardStacks > 0) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new Radiance_Power(p, rewardStacks), rewardStacks));
        }
        this.isDone = true;
    }
}
