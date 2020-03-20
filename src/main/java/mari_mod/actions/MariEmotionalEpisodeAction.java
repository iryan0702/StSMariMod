package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariEmotionalEpisodeAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariEmotionalEpisodeAction.class.getName());
    private AbstractPower powerToApply;

    public MariEmotionalEpisodeAction(int goldPerDebuff) {
        this.actionType = ActionType.POWER;
        this.amount = goldPerDebuff;
    }

    public void update() {
        for(AbstractPower p: AbstractDungeon.player.powers){
            if(p.type == AbstractPower.PowerType.DEBUFF){
                addToTop(new MariGainGoldAction(this.amount));
            }
        }
        this.isDone = true;
    }
}
