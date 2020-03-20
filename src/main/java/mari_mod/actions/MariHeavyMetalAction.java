package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import mari_mod.patches.MariMusicalAttackEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;

public class MariHeavyMetalAction extends AbstractGameAction {
    private DamageInfo info;
    public static final Logger logger = LogManager.getLogger(MariHeavyMetalAction.class.getName());

    public MariHeavyMetalAction(AbstractCreature target, int damage) {
        this.target = target;
        this.amount = damage;
        this.actionType = ActionType.DAMAGE;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        int yourVulnerable = -1;
        if(p.hasPower(VulnerablePower.POWER_ID)){
            yourVulnerable = p.getPower(VulnerablePower.POWER_ID).amount;
        }

        ArrayList<AbstractMonster> monstersList = new ArrayList<>(AbstractDungeon.getCurrRoom().monsters.monsters);
        Collections.reverse(monstersList);
        for(AbstractMonster m: monstersList) {
            if(!(m.isDead || m.halfDead)){
                this.info = new DamageInfo(p, this.amount, DamageInfo.DamageType.NORMAL);
                AbstractDungeon.actionManager.addToTop(new MariImmediatelyDealPowerAppliedDamageAction(m, this.info, MariMusicalAttackEffect.MUSICAL));
            }
        }

        for(AbstractMonster m: monstersList) {
            if(!(m.isDead || m.halfDead)){
                if (yourVulnerable >= 0) {
                    AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, p, new VulnerablePower(m, yourVulnerable, false), yourVulnerable));
                }
            }
        }

        if (yourVulnerable >= 0) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(p, p, VulnerablePower.POWER_ID));
        }
        this.isDone = true;
    }
}
