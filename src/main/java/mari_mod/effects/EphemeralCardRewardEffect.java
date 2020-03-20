//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.ExhaustBlurEffect;
import com.megacrit.cardcrawl.vfx.ExhaustEmberEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;

public class EphemeralCardRewardEffect extends AbstractGameEffect {
    public static final float MOVE_DUR = 1.0f;
    public static final float FLASH_DUR = 0.5f;
    public static final float FADE_DUR = 1.0f;
    public boolean flashTriggered;
    public boolean exhaustTriggered;

    private AbstractCard card;

    public EphemeralCardRewardEffect(AbstractCard card) {
        this.duration = MOVE_DUR + FADE_DUR;
        this.card = card;
        flashTriggered = false;
        exhaustTriggered = false;
    }

    public void update() {
        this.card.update();

        this.duration -= Gdx.graphics.getDeltaTime();
        if(this.duration < FADE_DUR + FLASH_DUR && !flashTriggered){
            this.card.flash(new Color(0.5f, 0.0f, 0.0f, 0.75f));
            this.flashTriggered = true;
        }

        if (this.duration < FADE_DUR && !exhaustTriggered) {
            AbstractDungeon.topLevelEffectsQueue.add(new ExhaustCardEffect(this.card));
            CardCrawlGame.sound.play("CARD_EXHAUST", 0.2F);

            int i;
            for(i = 0; i < 90; ++i) {
                AbstractDungeon.topLevelEffectsQueue.add(new ExhaustBlurEffect(this.card.current_x, this.card.current_y));
            }

            for(i = 0; i < 50; ++i) {
                AbstractDungeon.topLevelEffectsQueue.add(new ExhaustEmberEffect(this.card.current_x, this.card.current_y));
            }
            exhaustTriggered = true;
        }

        if (!this.card.fadingOut && this.duration < 0.7F) {
            this.card.fadingOut = true;
        }
        if(this.duration < 0.0f){
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
        this.card.render(sb);
    }

    public void dispose() {
    }
}
