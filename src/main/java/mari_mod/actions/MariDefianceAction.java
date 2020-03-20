package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.FrailPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariDefianceAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariDefianceAction.class.getName());
    public AbstractCard card;
    public boolean success;

    public MariDefianceAction(AbstractCard defianceCard) {
        this.actionType = ActionType.BLOCK;
        this.card = defianceCard;
        this.success = AbstractDungeon.player.hasPower(FrailPower.POWER_ID);
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        if(success) {
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(p, p, this.card.block));
        }
        this.isDone = true;
    }
}
