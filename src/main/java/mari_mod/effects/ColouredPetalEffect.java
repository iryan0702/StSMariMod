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
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
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
    private float rotationOffset;
    private float rotationMagnitude;
    private float swaySpeed;
    private float xAfterSway;
    private float yAfterSway;
    private boolean flipH;
    private boolean landedOnFloor;
    private float floorY;
    private float scaleY;
    private int frame = 0;
    private float animTimer = 0.05F;
    private static final int W = 32;

    public ColouredPetalEffect(Color color) {
        this.x = MathUtils.random(-100.0F * Settings.scale, 1820.0F * Settings.scale);
        this.scale = MathUtils.random(0.25f,MathUtils.random(0.75f,MathUtils.random(1F, MathUtils.random(1F, MathUtils.random(1F, 10F)))));
        this.y = (float)Settings.HEIGHT + MathUtils.random(20.0F, 200.0F) * Settings.scale * this.scale;
        this.frame = MathUtils.random(8);
        this.rotationOffset = MathUtils.random(-10.0F, 10.0F);
        this.rotationMagnitude = MathUtils.random(40.0F, 60.0F);
        this.scaleY = 1.0F;
        if (this.scale < 0.75F) {
            this.renderBehind = true;
        }

        this.vY = MathUtils.random(100.0F, 200.0F) * this.scale * Settings.scale + 40f * Settings.scale;
        this.vX = MathUtils.random(5.0F, 50.0F) * this.scale * Settings.scale;

        this.swayTime = MathUtils.random(0.0f, 360.0F);
        this.swaySpeed = MathUtils.random(1.8f, 2.2F);
        this.xSwayMagnitude = MathUtils.random(20.0f, 40.0F) * this.scale * Settings.scale;
        this.ySwayMagnitude = MathUtils.random(10.0f, 20.0F) * this.scale * Settings.scale;
        this.flipH = true;

        this.scale *= Settings.scale;

        this.xAfterSway = Settings.scale * -1000f;
        this.yAfterSway = Settings.scale * -1000f;

        this.landedOnFloor = false;
        this.floorY = Math.min(AbstractDungeon.floorY + Settings.scale * 25f, AbstractDungeon.floorY - (this.scale - 0.75f) * Settings.scale * 300f);

        this.color = color;
        this.duration = 8.0F;
    }

    public void update() {
        if(!landedOnFloor) {
            this.y -= this.vY * Gdx.graphics.getDeltaTime();
            this.x += this.vX * Gdx.graphics.getDeltaTime();

            this.swayTime += Gdx.graphics.getDeltaTime();
            this.xAfterSway = this.x + MathUtils.sin(this.swayTime * this.swaySpeed) * this.xSwayMagnitude;
            this.yAfterSway = this.y + MathUtils.sin(this.swayTime * this.swaySpeed * 2) * this.ySwayMagnitude;

            float roto = MathUtils.sin(this.swayTime * this.swaySpeed);
            float rotoSign = Math.signum(roto);
            this.rotation = (float) (Math.pow(roto * rotoSign, 0.7) * rotoSign) * this.rotationMagnitude;

            float absRoto = Math.abs(this.rotation);
            //this.scaleY = absRoto/this.rotationMagnitude;
            this.flipH = Math.signum(roto) < 0;
            if (absRoto < this.rotationMagnitude * 0.20) {
                this.frame = 3;
            } else if (absRoto < this.rotationMagnitude * 0.40) {
                this.frame = 2;
            } else if (absRoto < this.rotationMagnitude * 0.80) {
                this.frame = 1;
            } else {
                this.frame = 0;
            }

            if(this.y < floorY){
                this.landedOnFloor = true;
                this.rotation = MathUtils.random(-5.0f, 5.0F);
                this.frame = MathUtils.random(2, 3);
            }
        /*this.animTimer -= Gdx.graphics.getDeltaTime();
        if (this.animTimer < 0.0F) {
            this.animTimer += 0.1F;
            ++this.frame;
            if (this.frame > 11) {
                this.frame = 0;
            }
        }*/
        }
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        } else if (this.duration < 1.0F) {
            this.color.a = this.duration;
        }

    }

    public void earlyFade(){
        this.duration = Math.min(this.duration, MathUtils.random(0.75f, 1.5F));
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        //this.renderImg(sb, MariMod.featherVfx1, false, false);

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
        }
    }

    public void dispose() {
    }

    private void renderImg(SpriteBatch sb, Texture img, boolean flipH, boolean flipV) {
        sb.setBlendFunction(770, 1);
        sb.draw(img, this.xAfterSway, this.yAfterSway, 24f, 24f, 48.0F, 48.0F, this.scale, this.scale * this.scaleY, this.rotationOffset + this.rotation, 0, 0, 48, 48, this.flipH, flipV);
        sb.setBlendFunction(770, 771);
    }
}
