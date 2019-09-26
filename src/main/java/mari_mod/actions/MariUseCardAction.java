package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariUseCardAction extends AbstractGameAction {
    private DamageInfo info;
    public static final Logger logger = LogManager.getLogger(MariUseCardAction.class.getName());
    private AbstractCard card;
    private AbstractPlayer p;
    private AbstractMonster m;
    private int energyOnUse;
    private boolean stewshineVersion;

    public MariUseCardAction(AbstractCard card, AbstractPlayer p, AbstractMonster m, int energyOnUse, boolean stewshineVersion) {
        this.actionType = ActionType.SPECIAL;
        this.card = card;
        this.p = p;
        this.m = m;
        this.energyOnUse = energyOnUse;
        this.stewshineVersion = stewshineVersion;
    }

    public void update() {

        logger.info("I AM " + this.getClass().getName());
        for(AbstractGameAction action: AbstractDungeon.actionManager.actions){
            logger.info("ACTIONS NOW: " + action.getClass().getName());
        }

        this.card.energyOnUse = this.energyOnUse;
        this.card.use(p,m);

        if(this.stewshineVersion){
            AbstractDungeon.actionManager.addToBottom(new MariAddNewActionsToSavedActionsAction());
        }

        this.isDone = true;
    }
}
