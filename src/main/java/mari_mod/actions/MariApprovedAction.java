package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariApprovedAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariApprovedAction.class.getName());
    private AbstractMonster targetMonster;
    public int radiance;
    private String failText;

    public MariApprovedAction(int damage, int radiance, AbstractMonster m, String failText) {
        this.duration = 0.0F;
        this.actionType = ActionType.WAIT;
        this.amount = damage;
        this.failText = failText;
        this.targetMonster = m;
        this.radiance = radiance;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        if (this.targetMonster != null && (targetMonster.getIntentBaseDmg() >= 0)) {
            AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0F, failText, true));
        } else {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.targetMonster, p, new Radiance_Power(this.targetMonster, this.radiance), this.radiance));
            AbstractDungeon.actionManager.addToTop(new DamageAction(this.targetMonster, new DamageInfo(p, this.amount, DamageInfo.DamageType.NORMAL), AttackEffect.BLUNT_LIGHT));
        }

        this.isDone = true;
    }
}
