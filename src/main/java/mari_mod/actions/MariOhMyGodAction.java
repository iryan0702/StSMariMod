package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import mari_mod.cards.OnRecallCard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class MariOhMyGodAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariOhMyGodAction.class.getName());
    public AbstractMonster monster;
    public boolean upgraded;

    public MariOhMyGodAction() {
        this.actionType = ActionType.SPECIAL;
    }

    public void update() {
        for(AbstractCard c: AbstractDungeon.player.exhaustPile.group){
            if(c instanceof OnRecallCard){
                ((OnRecallCard)c).onRecall();
            }
        }
        this.isDone = true;
    }
}
