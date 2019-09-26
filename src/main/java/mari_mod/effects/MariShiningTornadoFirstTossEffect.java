package mari_mod.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;

public class MariShiningTornadoFirstTossEffect extends AbstractGameEffect {
    private static AtlasRegion img;
    private float sX;
    private float sY;
    private float cX;
    private float cY;
    private float dX;
    private float dY;
    private float totalDuration;
    private float yOffset;
    private float bounceHeight;
    private static final float DUR = 0.6F;
    private boolean playedSfx = false;
    private ArrayList<Vector2> previousPos = new ArrayList();

    public MariShiningTornadoFirstTossEffect(float srcX, float srcY, float destX, float destY) {
        if (img == null) {
            Texture ballImg = ImageMaster.loadImage("mari_mod/images/effects/MariShiningTornado.png");
            img = ImageMaster.vfxAtlas.addRegion("MariShiningTornado", ballImg, 0,0,75,75);
        }

        this.sX = srcX;
        this.sY = srcY;
        this.cX = this.sX;
        this.cY = this.sY;
        this.dX = destX;
        this.dY = srcY;
        this.rotation = 0.0F;
        this.duration = 0.2F;
        this.totalDuration = duration;
        this.color = new Color(1.0F, 0.9F, 0.0F, 1.0F);
        if (this.sY > this.dY) {
            this.bounceHeight = 400.0F * Settings.scale;
        } else {
            this.bounceHeight = this.dY - this.sY + 400.0F * Settings.scale;
        }

    }

    public void update() {
        if (!this.playedSfx && this.duration < 0.0F) {
            this.playedSfx = true;
            CardCrawlGame.sound.playA("BLUNT_FAST", MathUtils.random(-0.3F, -0.2F));
        }

        this.cX = Interpolation.linear.apply(this.dX, this.sX, this.duration / 0.2F);
        this.cY = Interpolation.linear.apply(this.dY, this.sY, this.duration / 0.2F);
        this.previousPos.add(new Vector2(this.cX + MathUtils.random(-30.0F, 30.0F) * Settings.scale, this.cY + this.yOffset + MathUtils.random(-30.0F, 30.0F) * Settings.scale));
        if (this.previousPos.size() > 20) {
            this.previousPos.remove(this.previousPos.get(0));
        }

        if (this.dX > this.sX) {
            this.rotation -= Gdx.graphics.getDeltaTime() * 1000.0F;
        } else {
            this.rotation += Gdx.graphics.getDeltaTime() * 1000.0F;
        }

        //this.color.a = Interpolation.exp5In.apply(1.0F, 0.0F, (this.duration - totalDuration/10.0F) / totalDuration/10.0F) * Settings.scale;


            this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        sb.setColor(new Color(1.0F, 0.6F, 0.0F, this.color.a));

        for(int i = 2; i < this.previousPos.size(); ++i) {
            sb.draw(ImageMaster.GLOW_SPARK_2, ((Vector2)this.previousPos.get(i)).x - (float)(img.packedWidth / 2), ((Vector2)this.previousPos.get(i)).y - (float)(img.packedHeight / 2), (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight, this.scale / (20.0F / (float)i), this.scale / (20.0F / (float)i), this.rotation);
        }

        sb.setColor(this.color);
        sb.draw(img, this.cX - (float)(img.packedWidth / 2), this.cY - (float)(img.packedHeight / 2) + this.yOffset, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight, this.scale, this.scale, this.rotation);
        sb.draw(img, this.cX - (float)(img.packedWidth / 2), this.cY - (float)(img.packedHeight / 2) + this.yOffset, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight, this.scale, this.scale, this.rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}
