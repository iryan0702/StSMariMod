package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mari_mod.MariMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariLetsGoAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariLetsGoAction.class.getName());
    boolean upgraded;
    AbstractPlayer p;

    public MariLetsGoAction() {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.p = AbstractDungeon.player;
    }

    public void update() {
        addToTop(new DrawCardAction(AbstractDungeon.player, MariMod.energySpentThisTurn));
        this.isDone = true;
    }

}
