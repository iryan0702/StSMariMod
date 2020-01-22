package mari_mod.actions;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import mari_mod.MariMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class MariCollectThoughtsAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariCollectThoughtsAction.class.getName());
    private static final float DURATION_PER_CARD = 0.25F;
    private static final float UNDEFINED_DUR = 9999F;
    private AbstractPlayer p = AbstractDungeon.player;
    private ArrayList<AbstractCard> cardCopies = new ArrayList<>();
    private ArrayList<AbstractCard> inLimbo = new ArrayList<>();
    private int triggers;
    private float maxDuration;
    private boolean upgraded;
    private float offset;
    private int handSize;
    private float offsetX;
    private float offsetY;
    private float cloudW;
    private float cloudH;

    public MariCollectThoughtsAction(boolean upgraded) {
        this.duration = UNDEFINED_DUR;
        this.upgraded = upgraded;
        this.triggers = 0;
        this.offsetX = (float) ReflectionHacks.getPrivateStatic(ThoughtBubble.class, "OFFSET_X");
        this.offsetY = (float) ReflectionHacks.getPrivateStatic(ThoughtBubble.class, "OFFSET_Y");
        this.cloudW = (float) ReflectionHacks.getPrivateStatic(ThoughtBubble.class, "CLOUD_W");
        this.cloudH = (float) ReflectionHacks.getPrivateStatic(ThoughtBubble.class, "CLOUD_H");
    }

    public void update() {
        if(this.duration == UNDEFINED_DUR){
            this.handSize = p.hand.size();
            this.duration = DURATION_PER_CARD * handSize;
            this.maxDuration = this.duration;
            this.offset = Settings.scale * 200 / handSize;
            AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX,p.dialogY,this.maxDuration + DURATION_PER_CARD,"",true));
            for(AbstractCard cardToCopy : p.hand.group){
                if(this.upgraded && cardToCopy.canUpgrade()){
                    cardToCopy.upgrade();
                    cardToCopy.flash(Color.GOLD.cpy());
                }
                AbstractCard c = cardToCopy.makeStatEquivalentCopy();
                cardCopies.add(0, c);
            }
        }
        this.tickDuration();
        if(this.maxDuration - this.duration > triggers * DURATION_PER_CARD && cardCopies.size() > 0){
            AbstractCard c = cardCopies.remove(0);
            c.current_x = offsetX + (cloudW / 2f) + (-50f * Settings.scale) + p.dialogX - (handSize * offset / 2f) + triggers * offset;
            c.current_y = offsetY + (cloudH / 2f) + (-40f * Settings.scale) + p.dialogY;
            c.target_x = c.current_x;
            c.target_y = c.current_y;
            c.drawScale = 0;
            c.targetDrawScale = 0.2f;
            p.limbo.addToTop(c);
            inLimbo.add(c);
            triggers++;
        }

        if(this.isDone){
            for(AbstractCard c: inLimbo){
                p.limbo.moveToDeck(c,true);
            }
        }
    }
}
