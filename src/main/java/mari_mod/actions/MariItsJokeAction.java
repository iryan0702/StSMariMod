package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariItsJokeAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariItsJokeAction.class.getName());
    public AbstractMonster monster;
    public int radiance;
    public int hpThresh;

    public MariItsJokeAction(AbstractMonster monster, int originalHP, int hpThreshExclusive, int radiance) {
        this.actionType = ActionType.HEAL;
        this.monster = monster;
        this.amount = originalHP;
        this.radiance = radiance;
        this.hpThresh = hpThreshExclusive;
    }

    public void update() {
        if(!monster.halfDead && !monster.isDead && amount - monster.currentHealth < hpThresh) {
            AbstractPlayer p = AbstractDungeon.player;
            addToTop(new ApplyPowerAction(monster, p, new Radiance_Power(monster, this.radiance), this.radiance));
            addToTop(new ApplyPowerAction(monster, p, new Radiance_Power(monster, this.radiance), this.radiance));
        }
        this.isDone = true;
    }
}
