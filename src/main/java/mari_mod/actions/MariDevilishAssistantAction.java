package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.cards.AbstractMariCard;
import mari_mod.effects.MariDevilishAssistantEffect;
import mari_mod.powers.Devilish_Assistant_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariDevilishAssistantAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariDevilishAssistantAction.class.getName());
    private static final float DURATION = 0.01F;
    private static final float POST_ATTACK_WAIT_DUR = 0.1F;
    private AbstractCard mostRecentCard;
    private int lastAmount;
    private AbstractMonster lastTarget;
    private Devilish_Assistant_Power resetPower;

    public MariDevilishAssistantAction(AbstractCard card, int amount, AbstractMonster target, Devilish_Assistant_Power resetPower) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = 0.01F;
        this.mostRecentCard = card;
        this.lastAmount = amount;
        this.lastTarget = target;
        this.resetPower = resetPower;
    }

    public void update() {
        for (int i = 0; i < this.lastAmount; i++) {
            AbstractCard tmp = mostRecentCard.makeSameInstanceOf();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.current_x = (float) Settings.WIDTH / 2.0F - 200.0F * Settings.scale;
            tmp.current_y = (float) Settings.HEIGHT / 2.0F;
            tmp.target_x = (float) Settings.WIDTH / 2.0F - 250.0F * Settings.scale;
            tmp.target_y = (float) Settings.HEIGHT / 2.0F;
            tmp.freeToPlayOnce = true;
            tmp.costForTurn = 0;

            //REPURPOSED LAST TARGET to be RANDOM TARGET
            this.lastTarget = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng);

            if (AbstractMariCard.class.isAssignableFrom(tmp.getClass())) {
                ((AbstractMariCard) tmp).goldCost = 0;
                if(((AbstractMariCard) tmp).isAnyTarget && AbstractDungeon.cardRandomRng.random(0,AbstractDungeon.getMonsters().monsters.size()) == 0){
                    this.lastTarget = null;
                }
            }

            if (this.lastTarget != null) {
                tmp.calculateCardDamage(this.lastTarget);
            }

            tmp.purgeOnUse = true;

            this.resetPower.cardsToNotGetTriggeredBy.add(tmp);
            AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(tmp, this.lastTarget));
            //this.cardsPlayed--;
        }

        AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new MariDevilishAssistantEffect(), 0.4F));

        this.isDone = true;
    }
}
