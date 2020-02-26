//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class MariSpotlightEffect extends AbstractGameEffect {
    float maxDuration;
    boolean doSound;
    boolean flashFade;
    float xPos;
    float fastIn;
    float slowOut;
    float width;

    public MariSpotlightEffect(float duration, float xPos, float width, boolean doSound) {
        this(duration,xPos,width,doSound, 0.2f, new Color(1.0F, 1.0F, 0.8F, 0.5F));
    }

    public MariSpotlightEffect(float duration, float xPos, float width, boolean doSound, float fastIn, Color c) {
        this.maxDuration = duration;
        this.duration = this.maxDuration;
        this.xPos = xPos;
        this.width = width;
        this.doSound = doSound;
        this.color = c;
        this.flashFade = flashFade;
        this.fastIn = fastIn;
        this.slowOut = duration - this.fastIn;
    }

    public void update() {
        if (this.duration == this.maxDuration && this.doSound) {
            CardCrawlGame.sound.playA("GHOST_ORB_IGNITE_1", -0.6F);
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration > this.maxDuration - this.fastIn) {
            this.color.a = Interpolation.pow5In.apply(0.5F, 0.0F, (this.duration - (this.maxDuration - this.fastIn)) / fastIn);
        } else if (this.duration < this.slowOut) {
            this.color.a = Interpolation.exp10In.apply(0.0F, 0.5F, this.duration / this.slowOut);
        }

        if (this.duration < 0.0F) {
            this.color.a = 0.0F;
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.setBlendFunction(770, 1);
        sb.draw(ImageMaster.SPOTLIGHT_VFX, this.xPos - this.width/2f, 0.0F, this.width, (float)Settings.HEIGHT);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}
