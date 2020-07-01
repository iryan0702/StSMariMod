package mari_mod.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import com.megacrit.cardcrawl.vfx.combat.AnimatedSlashEffect;
import mari_mod.cards.Mari_Spark;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


//WIP
public class MariRepressedStrikeAction extends AbstractGameAction {
    private DamageInfo info;
    public static final Logger logger = LogManager.getLogger(MariRepressedStrikeAction.class.getName());

    public MariRepressedStrikeAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {

        //GainEnergyAction e = new GainEnergyAction(1);
        //e.update();

        AbstractPlayer p = AbstractDungeon.player;
        CardGroup g = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for(AbstractCard c: p.hand.group) {
            if(!c.canUse(p, null)) {
                addToTop(new MakeTempCardInHandAction(new Mari_Spark()));
                g.addToTop(c);
            }
        }

        for(AbstractCard c: g.group) {
            if(p.hand.contains(c)){
                p.hand.removeCard(c);
                AbstractDungeon.effectsQueue.add(new ExhaustCardEffect(c));
            }
        }

        this.isDone = true;
    }
}
