package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class MariOhMyGodAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariOhMyGodAction.class.getName());
    public String failMessage;
    public AbstractMonster monster;
    public boolean upgraded;

    public MariOhMyGodAction(int playAmount, AbstractMonster m, boolean upgraded, String failMessage) {
        this.amount = playAmount;
        this.actionType = ActionType.USE;
        this.failMessage = failMessage;
        this.upgraded = upgraded;
        this.monster = m;
    }

    public void update() {
        ArrayList<AbstractMonster> monstersToAttack = new ArrayList<>();

        for(AbstractMonster m: AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (m != null && (m.getIntentBaseDmg() > 0 || this.upgraded) && !m.isDead && !m.halfDead) {
                monstersToAttack.add(m);
            }
        }
        ArrayList<AbstractCard> cardsToPlay = new ArrayList<>();
        CardGroup group = AbstractDungeon.player.masterDeck;
        for(int i = 0; i < group.size() && cardsToPlay.size() < this.amount; i++){
            AbstractCard c = group.getNCardFromTop(i);
            if(c.type == AbstractCard.CardType.ATTACK){
                cardsToPlay.add(c);
            }
        }

        if(monstersToAttack.size() == 0){
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, this.failMessage, true));
        }else{
            for (AbstractCard cardToPlay : cardsToPlay) {
                for(AbstractMonster m: monstersToAttack) {
                    AbstractCard tmp = cardToPlay.makeStatEquivalentCopy();
                    AbstractDungeon.player.limbo.addToBottom(tmp);
                    tmp.current_x = (float) Settings.WIDTH / 2.0F - 300.f * Settings.scale;
                    tmp.current_y = (float) Settings.HEIGHT / 2.0F;
                    tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.f * Settings.scale;
                    tmp.target_y = (float) Settings.HEIGHT / 2.0F;
                    tmp.freeToPlayOnce = true;
                    tmp.costForTurn = 0;

                    if (m != null) {
                        tmp.calculateCardDamage(m);
                    }

                    tmp.exhaustOnUseOnce = true;
                    logger.info("OH MY GOD! ADDING " + tmp.cardID + " TO CARD QUEUE!!!");
                    AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(tmp, m));
                }
            }
        }

        this.isDone = true;
    }
}
