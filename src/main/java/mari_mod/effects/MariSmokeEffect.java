//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import mari_mod.MariMod;

public class MariSmokeEffect extends AbstractGameEffect {
    public static final float MAX_DURATION = 7.0f;
    float maxDuration;
    float fadeOutTime;
    float fadeInTime;
    float yPos;
    float xPos;
    float xPosStart;
    float xPosDest;
    float scale;
    float scaleStart;
    float scaleEnd;
    float targetAlpha;


    public MariSmokeEffect() {
        this.maxDuration = MAX_DURATION;
        this.duration = this.maxDuration;
        this.rotation = MathUtils.random(0.0f,360.0f);

        float yPosScale;
        if(MathUtils.randomBoolean()){
            this.xPosStart = MathUtils.random(0.0f,1.25f) * Settings.WIDTH;
            yPosScale = MathUtils.random(-0.1f,0.3f);
            this.yPos = yPosScale * Settings.HEIGHT;
            this.xPosDest = this.xPosStart - MathUtils.random(0.0f,0.25f) * Settings.WIDTH - yPosScale/1.5f;
        }else{
            this.xPosStart = MathUtils.random(-0.25f,1.0f) * Settings.WIDTH;
            yPosScale = MathUtils.random(-0.1f,0.3f);
            this.yPos = yPosScale * Settings.HEIGHT;
            this.xPosDest = this.xPosStart + MathUtils.random(0.0f,0.25f) * Settings.WIDTH + yPosScale/1.5f;
        }

        this.color = new Color(1,1,1,0.0f);

        this.fadeOutTime = MathUtils.random(2.5f,3.5f);
        this.fadeInTime = 1.0f;
        this.scaleStart = MathUtils.random(0.4f,0.5f);
        this.scale = this.scaleStart;
        this.scaleEnd = this.scaleStart + MathUtils.random(0.1f,0.2f);
        this.targetAlpha = MathUtils.random(0.2f,0.8f - (2*yPosScale));
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.xPos = Interpolation.pow2In.apply(xPosDest,xPosStart,this.duration/this.maxDuration);

        this.scale = Interpolation.pow3Out.apply(scaleEnd,scaleStart,this.duration/this.maxDuration);

        if(this.duration > this.maxDuration - this.fadeInTime){
            this.color.a = Interpolation.linear.apply(this.targetAlpha,0f,(this.duration - maxDuration + fadeInTime)/(this.fadeInTime));
        }else if(this.duration < this.fadeOutTime){
            this.color.a = Interpolation.linear.apply(0.0f,this.targetAlpha,this.duration/this.fadeOutTime);
        }



        if (this.duration < 0.0F) {
            this.color.a = 0.0F;
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(MariMod.smokeVfx, this.xPos - 256f, this.yPos - 256f, 256f, 256f, 512f, 512f, this.scale, this.scale, this.rotation, 0, 0, 512, 512, false, false);
    }

    public void dispose() {
    }
}
