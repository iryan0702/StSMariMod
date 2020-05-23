package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class MariApplyRandomRadianceAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariApplyRandomRadianceAction.class.getName());
    private int amount;
    private boolean targetPlayer;

    public MariApplyRandomRadianceAction(int amount) {
        this(amount, false);
    }

    public MariApplyRandomRadianceAction(int amount, boolean targetPlayer) {
        this.actionType = ActionType.DEBUFF;
        this.duration = 0.01F;
        this.amount = amount;
        this.targetPlayer = targetPlayer;
    }

    public void update() {
        AbstractMonster randomMonster = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if(targetPlayer && AbstractDungeon.cardRandomRng.random(AbstractDungeon.getMonsters().monsters.size()) == 0){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new Radiance_Power(randomMonster, this.amount), this.amount));
        }else if(randomMonster != null) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(randomMonster, AbstractDungeon.player, new Radiance_Power(randomMonster, this.amount), this.amount));
        }
        this.isDone = true;
    }
}
