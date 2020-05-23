package mari_mod.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.AnimatedSlashEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


//WIP
public class MariRepressedStrikeAction extends AbstractGameAction {
    private DamageInfo info;
    public static final Logger logger = LogManager.getLogger(MariRepressedStrikeAction.class.getName());

    public MariRepressedStrikeAction(AbstractCreature target, int damage) {
        this.target = target;
        this.amount = damage;
        this.actionType = ActionType.DAMAGE;
    }

    public void update() {

        //GainEnergyAction e = new GainEnergyAction(1);
        //e.update();

        AbstractPlayer p = AbstractDungeon.player;
        CardGroup g = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for(AbstractCard c: p.hand.group) {
            if(!c.canUse(p, null)) {
                AbstractDungeon.actionManager.addToTop(new DamageAction(this.target, new DamageInfo(p, this.amount, DamageInfo.DamageType.NORMAL), AttackEffect.SMASH));
                g.addToTop(c);
            }
        }
        for(AbstractCard c: g.group) {
            c.current_x = 0;
            c.current_y = Settings.HEIGHT;
            p.hand.moveToDiscardPile(c);
            c.triggerOnManualDiscard();
            GameActionManager.incrementDiscard(false);
            /*
            p.hand.removeCard(c);
            p.limbo.addToTop(c);
            */
        }

        AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(Settings.WIDTH/4f,Settings.HEIGHT/2f,0.0f,0.0f, 0.0f, 2.0f, Color.BLACK, Color.BLUE));
        AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(Settings.WIDTH/4f*3f,Settings.HEIGHT/2f,0.0f,0.0f, 45.0f, 4.0f, Color.WHITE, Color.RED));
        this.isDone = true;
    }
}
