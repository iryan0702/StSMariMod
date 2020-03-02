//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import mari_mod.MariMod;

public class ColouredPetalEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private float vY;
    private float vX;
    private float swayTime;
    private float xSwayMagnitude;
    private float ySwayMagnitude;
    private float swaySpeed;
    private float xAfterSway;
    private float yAfterSway;
    private boolean flipH;
    private float scaleY;
    private int frame = 0;
    private float animTimer = 0.05F;
    private static final int W = 32;

    public ColouredPetalEffect(Color color) {
        this.x = MathUtils.random(-100.0F * Settings.scale, 1820.0F * Settings.scale);
        this.scale = MathUtils.random(0.5f,MathUtils.random(1f,MathUtils.random(1F, MathUtils.random(1F, 8F))));
        this.y = (float)Settings.HEIGHT + MathUtils.random(20.0F, 200.0F) * Settings.scale * this.scale;
        this.frame = MathUtils.random(8);
        this.rotation = MathUtils.random(-10.0F, 10.0F);
        this.scaleY = MathUtils.random(1.0F, 1.2F);
        if (this.scale < 1.5F) {
            this.renderBehind = true;
        }

        this.vY = MathUtils.random(140.0F, 200.0F) * this.scale * Settings.scale;
        this.vX = MathUtils.random(5.0F, 50.0F) * this.scale * Settings.scale;

        this.swayTime = MathUtils.random(0.0f, 360.0F);
        this.swaySpeed = MathUtils.random(1.8f, 2.2F);
        this.xSwayMagnitude = MathUtils.random(20.0f, 40.0F) * this.scale * Settings.scale;
        this.ySwayMagnitude = MathUtils.random(10.0f, 20.0F) * this.scale * Settings.scale;
        this.flipH = true;

        this.scale *= Settings.scale;
        if (MathUtils.randomBoolean()) {
            this.rotation += 180.0F;
        }

        this.xAfterSway = Settings.scale * -1000f;
        this.yAfterSway = Settings.scale * -1000f;

        this.color = color;
        this.duration = 8.0F;
    }

    public void update() {
        this.y -= this.vY * Gdx.graphics.getDeltaTime();
        this.x += this.vX * Gdx.graphics.getDeltaTime();

        this.swayTime += Gdx.graphics.getDeltaTime();
        this.xAfterSway = this.x + MathUtils.sin(this.swayTime * this.swaySpeed) * this.xSwayMagnitude;
        this.yAfterSway = this.y + MathUtils.sin(this.swayTime * this.swaySpeed * 2) * this.ySwayMagnitude;

        if(MathUtils.sin(this.swayTime * this.swaySpeed) >= 0){
            flipH = true;
        }else{
            flipH = false;
        }

        this.animTimer -= Gdx.graphics.getDeltaTime();
        if (this.animTimer < 0.0F) {
            this.animTimer += 0.1F;
            ++this.frame;
            if (this.frame > 11) {
                this.frame = 0;
            }
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        } else if (this.duration < 1.0F) {
            this.color.a = this.duration;
        }

    }

    public void earlyFade(){
        this.duration = 1.0f;
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        switch(this.frame) {
        case 0:
            this.renderImg(sb, MariMod.featherVfx1, false, false);
            break;
        case 1:
            this.renderImg(sb, MariMod.featherVfx2, false, false);
            break;
        case 2:
            this.renderImg(sb, MariMod.featherVfx3, false, false);
            break;
        case 3:
            this.renderImg(sb, MariMod.featherVfx4, false, false);
            break;
        case 4:
            this.renderImg(sb, MariMod.featherVfx3, false, false);
            break;
        case 5:
            this.renderImg(sb, MariMod.featherVfx2, false, false);
            break;
        case 6:
            this.renderImg(sb, MariMod.featherVfx1, false, false);
            break;
        case 7:
            this.renderImg(sb, MariMod.featherVfx2, false, false);
            break;
        case 8:
            this.renderImg(sb, MariMod.featherVfx3, false, false);
            break;
        case 9:
            this.renderImg(sb, MariMod.featherVfx4, false, false);
            break;
        case 10:
            this.renderImg(sb, MariMod.featherVfx3, false, false);
            break;
        case 11:
            this.renderImg(sb, MariMod.featherVfx2, false, false);
        }

    }

    public void dispose() {
    }

    private void renderImg(SpriteBatch sb, Texture img, boolean flipH, boolean flipV) {
        sb.setBlendFunction(770, 1);
        sb.draw(img, this.xAfterSway, this.yAfterSway, 16.0F, 16.0F, 32.0F, 32.0F, this.scale, this.scale * this.scaleY, this.rotation, 0, 0, 32, 32, this.flipH, flipV);
        sb.setBlendFunction(770, 771);
    }
}
