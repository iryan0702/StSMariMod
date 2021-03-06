package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import mari_mod.cards.Mari_Spark;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


//WIP
public class MariRepressedStrikeAction extends AbstractGameAction {
    private DamageInfo info;
    public static final Logger logger = LogManager.getLogger(MariRepressedStrikeAction.class.getName());
    boolean upgraded;

    public MariRepressedStrikeAction(boolean upgraded) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.upgraded = upgraded;
    }

    public void update() {

        //GainEnergyAction e = new GainEnergyAction(1);
        //e.update();

        AbstractPlayer p = AbstractDungeon.player;
        CardGroup g = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for(AbstractCard c: p.hand.group) {
            if(!c.canUse(p, null)) {
                AbstractCard cardToAdd = new Mari_Spark();
                if(upgraded){
                    cardToAdd.upgrade();
                }
                addToTop(new MakeTempCardInHandAction(cardToAdd));
                g.addToTop(c);
            }
        }

        for(AbstractCard c: g.group) {
            if(p.hand.contains(c)){
                addToTop(new ExhaustSpecificCardAction(c, p.hand));
                AbstractDungeon.effectsQueue.add(new ExhaustCardEffect(c));
            }
        }

        this.isDone = true;
    }
}
