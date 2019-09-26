package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariApplyRandomRadianceAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariApplyRandomRadianceAction.class.getName());
    private static final float DURATION = 0.01F;
    private static final float POST_ATTACK_WAIT_DUR = 0.1F;
    private DamageInfo tornadoDamage;
    private int damage;
    private int numTimes;
    private int amount;

    public MariApplyRandomRadianceAction(int amount) {
        this.actionType = ActionType.DEBUFF;
        this.duration = 0.01F;
        this.amount = amount;
    }

    public void update() {
        AbstractMonster randomMonster = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if(randomMonster != null) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(randomMonster, AbstractDungeon.player, new Radiance_Power(randomMonster, this.amount), this.amount));
        }
        this.isDone = true;
    }
}
