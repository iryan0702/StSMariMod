package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
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
    public AbstractCard card;

    public MariHeavyMetalAction(AbstractCreature target, int vulnCount, AbstractCard heavyMetalCard) {
        this.target = target;
        this.card = heavyMetalCard;
        this.amount = vulnCount;
        this.actionType = ActionType.DAMAGE;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;

        ArrayList<AbstractMonster> monstersList = new ArrayList<>(AbstractDungeon.getCurrRoom().monsters.monsters);
        Collections.reverse(monstersList);
        for(AbstractMonster m: monstersList) {
            if(!(m.isDead || m.halfDead)){
                this.info = new DamageInfo(p, this.amount, DamageInfo.DamageType.NORMAL);
                AbstractDungeon.actionManager.addToTop(new MariImmediatelyDealPowerAppliedDamageAction(m, this.card, MariMusicalAttackEffect.MUSICAL));
            }
        }

        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, p, new VulnerablePower(target, this.amount, false), this.amount));

        this.isDone = true;
    }
}
