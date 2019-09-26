package mari_mod.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.AutoplayCardAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import mari_mod.MariMod;
import mari_mod.cards.AbstractMariCard;
import mari_mod.cards.Mari_Oh_My_God;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class MariOhMyGodAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariOhMyGodAction.class.getName());
    public String failMessage;
    public AbstractMonster monster;

    public MariOhMyGodAction(int playAmount, AbstractMonster m, String failMessage) {
        this.amount = playAmount;
        this.actionType = ActionType.USE;
        this.failMessage = failMessage;
        this.monster = m;
    }

    public void update() {
        boolean isAnyoneAttacking = false;
        for(AbstractMonster aMonster : AbstractDungeon.getMonsters().monsters) {
            if (aMonster == null || aMonster.getIntentBaseDmg() < 0) {

            }else{
                isAnyoneAttacking = true;
            }
        }

        if(!isAnyoneAttacking) {
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, this.failMessage, true));
        }else {
            MariMod.timesOMGUsedThisTurn++; //Edge case infinite protection
            if (MariMod.timesOMGUsedThisTurn < 30) {
                ArrayList<AbstractCard> handCards = (ArrayList<AbstractCard>) AbstractDungeon.player.hand.group.clone();
                ArrayList<AbstractCard> handCardsWithoutOMG = new ArrayList<>();
                for (AbstractCard isItOMG : handCards) {
                    if (!isItOMG.cardID.equals(Mari_Oh_My_God.ID)) handCardsWithoutOMG.add(isItOMG);
                }
                ArrayList<AbstractCard> cardsToPlay = new ArrayList<>();
                while (handCardsWithoutOMG.size() > 0 && this.amount > 0) {
                    AbstractCard cardToAdd = handCardsWithoutOMG.get(AbstractDungeon.cardRandomRng.random(0, handCardsWithoutOMG.size() - 1));
                    cardsToPlay.add(cardToAdd);
                    handCardsWithoutOMG.remove(cardToAdd);
                    this.amount--;
                }

                for (AbstractCard cardToPlay : cardsToPlay) {
                    AbstractCard tmp = cardToPlay.makeStatEquivalentCopy();
                    AbstractDungeon.player.limbo.addToBottom(tmp);
                    tmp.current_x = (float) cardToPlay.current_x;
                    tmp.current_y = (float) cardToPlay.current_y;
                    tmp.target_x = (float) cardToPlay.current_x;
                    tmp.target_y = (float) Settings.HEIGHT / 4.0F;
                    tmp.freeToPlayOnce = true;
                    tmp.costForTurn = 0;

                    AbstractMonster target = this.monster;

                    if (target != null) {
                        tmp.calculateCardDamage(target);
                    }

                    if (AbstractMariCard.class.isAssignableFrom(tmp.getClass())) {
                        ((AbstractMariCard) tmp).goldCost = 0;
                    }

                    tmp.purgeOnUse = true;
                    logger.info("OH MY GOD! ADDING " + tmp.cardID + " TO CARD QUEUE!!!");
                    AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(tmp, target));
                }
            }
        }


        this.isDone = true;
    }
}
