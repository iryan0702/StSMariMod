package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import mari_mod.MariMod;
import mari_mod.powers.Radiance_Power;
import mari_mod.powers.Research_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class MariSuccessfulKindleAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariSuccessfulKindleAction.class.getName());
    public AbstractCreature target;
    public ArrayList<AbstractGameAction> actions;

    public MariSuccessfulKindleAction(AbstractCreature target, ArrayList<AbstractGameAction> actions) {
        this.actionType = ActionType.SPECIAL;
        this.target = target;
        this.actions = actions;
    }

    public MariSuccessfulKindleAction(AbstractCreature target, AbstractGameAction action) {
        this.actionType = ActionType.SPECIAL;
        this.target = target;
        this.actions = new ArrayList<>();
        actions.add(action);
    }

    public void update() {
        if(target.hasPower(Radiance_Power.POWER_ID) && target.getPower(Radiance_Power.POWER_ID).amount >= 1){
            for(AbstractGameAction a: actions) {
                AbstractDungeon.actionManager.addToTop(a);
            }

            ///

            AbstractPower power = AbstractDungeon.player.getPower(Research_Power.POWER_ID);
            if(power != null && !target.isPlayer) {
                AbstractPlayer p = AbstractDungeon.player;
                AbstractMonster m = (AbstractMonster)target;
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new StrengthPower(m, -power.amount), -power.amount));
                if (!m.hasPower(ArtifactPower.POWER_ID)) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new GainStrengthPower(m, power.amount), power.amount));
                }
            }

            ///

            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(target, AbstractDungeon.player, target.getPower(Radiance_Power.POWER_ID),1));
        }
        this.isDone = true;
    }
}
