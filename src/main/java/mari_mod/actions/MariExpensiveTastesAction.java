package mari_mod.actions;

import basemod.ReflectionHacks;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Iterator;

import static com.megacrit.cardcrawl.cards.CardGroup.DRAW_PILE_X;
import static com.megacrit.cardcrawl.cards.CardGroup.DRAW_PILE_Y;

public class MariExpensiveTastesAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariExpensiveTastesAction.class.getName());
    private static final float DURATION = 0.5F;
    public ArrayList<AbstractCard> animationDone = new ArrayList<>();
    public int updateTick = 0;
    public boolean kindled;

    public MariExpensiveTastesAction(boolean kindled) {
        this.duration = DURATION;
        this.kindled = kindled;
    }

    public void update() {

        AbstractDungeon.player.drawPile.sortByCost(true);

        if(updateTick++ % 15 == 0) {
            CardCrawlGame.sound.play("CARD_DRAW_8");
        }
        for(AbstractCard c: AbstractDungeon.player.drawPile.group) {
            if(!animationDone.contains(c) && MathUtils.randomBoolean(0.1F)) {
                animationDone.add(c);
                doASoul(c);
            }
        }

        if(kindled && AbstractDungeon.player.drawPile.size() > 0){
            AbstractCard c = AbstractDungeon.player.drawPile.getTopCard();
            if (c.cost > 0) {
                c.freeToPlayOnce = true;
            }
        }

        this.tickDuration();
    }

    public void doASoul(AbstractCard card){

        ArrayList<Soul> souls = (ArrayList<Soul>) ReflectionHacks.getPrivate(AbstractDungeon.getCurrRoom().souls,SoulGroup.class,"souls");

        boolean needMoreSouls = true;
        Iterator var4 = souls.iterator();

        while(var4.hasNext()) {
            Soul s = (Soul)var4.next();
            if (s.isReadyForReuse) {
                card.untip();
                card.unhover();


                soulDoThing(card, s);

                needMoreSouls = false;
                break;
            }
        }

        if (needMoreSouls) {
            logger.info("Not enough souls, creating...");
            Soul s = new Soul();


            soulDoThing(card, s);

            souls.add(s);
        }

    }

    public void soulDoThing(AbstractCard c, Soul s){

        s.onToDeck(c, true, true);
        Float random = MathUtils.random(MathUtils.PI * 0.5F);
        Vector2 v = new Vector2(DRAW_PILE_X + (float) Math.cos(random)* Settings.scale * 200, DRAW_PILE_Y + (float) Math.sin(random)* Settings.scale * 200);
        ReflectionHacks.setPrivate(s, Soul.class, "pos", v);
        ReflectionHacks.setPrivate(s, Soul.class, "rotation", MathUtils.random(random));


        c.shrink(true);
    }
}
