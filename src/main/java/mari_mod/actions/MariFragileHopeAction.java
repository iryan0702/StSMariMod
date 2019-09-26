package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariFragileHopeAction extends AbstractGameAction {
    private DamageInfo info;
    public static final Logger logger = LogManager.getLogger(MariDolphinStrikeAction.class.getName());
    private AttackEffect attackEffect;

    public MariFragileHopeAction(int radianceGain) {
        this.actionType = ActionType.POWER;
        this.amount = radianceGain;
    }

    public void update() {
        int radianceToGain = 0;
        for(AbstractPower power :AbstractDungeon.player.powers){
            if(power.type.equals(AbstractPower.PowerType.DEBUFF)) radianceToGain++;
        }
        AbstractPlayer p = AbstractDungeon.player;
        if(radianceToGain > 0) {
            AbstractDungeon.actionManager.addToTop(new DrawCardAction(p, radianceToGain));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new Radiance_Power(p, radianceToGain*this.amount), radianceToGain*this.amount));
        }
        this.isDone = true;
    }
}