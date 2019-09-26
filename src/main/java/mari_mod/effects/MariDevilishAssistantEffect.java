//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

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
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariDevilishAssistantEffect extends AbstractGameEffect {
    public static final Logger logger = LogManager.getLogger(MariDevilishAssistantEffect.class.getName());
    private static final float EFFECT_DUR = 0.5F;
    private float cX;
    private float cY;
    private float sX;
    private float sY;
    private float dX;
    private float dY;
    private static final float MAX_DURATION = 0.4F;
    private Vector2 speedVector;
    private float speed;
    private AtlasRegion yoshiko;
    private AtlasRegion imgBeam;
    private float beamLengthScale;
    private float beamHeightScale;
    private float beamScale;

    private float originX;
    private float originY;
    private float scaledOriginX;
    private float scaledOriginY;

    private boolean playedSound;
    public MariDevilishAssistantEffect() {

        Texture yoshikoImg = ImageMaster.loadImage("mari_mod/images/effects/MariYoshiko.png");
        yoshiko = ImageMaster.vfxAtlas.addRegion("MariTheFlyingCar", yoshikoImg, 0,0,104,283);
        //Texture beamImg = ImageMaster.loadImage("mari_mod/images/effects/MariTheFlyingCarBeam.png");
        //imgBeam = ImageMaster.vfxAtlas.addRegion("MariTheFlyingCarBeam", beamImg, 0,0,286,286);

        this.duration = MAX_DURATION;
        this.startingDuration = MAX_DURATION;
        this.sX = AbstractDungeon.player.hb.x;
        this.sY = AbstractDungeon.player.hb.y-(AbstractDungeon.player.hb.height/2.0F);
        this.cX = sX;
        this.cY = sY;
        this.dX = this.sX + 80.0F * Settings.scale;
        this.dY = this.sY;
        this.speed = MathUtils.random(20.0F * Settings.scale, 40.0F * Settings.scale);
        this.speedVector = new Vector2(dX-sX, dY-sY);
        this.speedVector.nor();
        this.speedVector.angle();
        this.rotation = 0.0F;
        this.beamLengthScale = 1.0F;
        this.beamScale = 0.0F;
        Vector2 var10000 = this.speedVector;
        var10000.x *= this.speed;
        var10000 = this.speedVector;
        var10000.y *= this.speed;
        this.scale = Settings.scale * 1.5F;

        this.originX = (float)this.yoshiko.packedWidth*0.8227F;
        this.originY = (float)this.yoshiko.packedHeight*0.3525F;

        this.playedSound = false;

    }

    public void update() {

        /*if(!this.playedSound){
            CardCrawlGame.screenShake.rumble(2.0F);
            CardCrawlGame.sound.play("MariMod:MariYoshiko", 0);
            this.playedSound = true;
        }*/

        if(this.duration <= MAX_DURATION) {

            this.cX = Interpolation.Pow.pow3Out.apply(this.dX, this.sX, this.duration / MAX_DURATION);
            this.cY = Interpolation.Pow.pow3Out.apply(this.dY, this.sY, this.duration / MAX_DURATION);

        }
        this.speed -= Gdx.graphics.getDeltaTime() * 60.0F;
        this.speedVector.nor();
        //this.beamLengthScale = this.duration / MAX_DURATION * 1.2F + 0.6F;
        //this.beamHeightScale = 1.0F - this.duration / MAX_DURATION * 0.5F;
        Vector2 var10000 = this.speedVector;
        var10000.x *= this.speed * Gdx.graphics.getDeltaTime() * 60.0F;
        var10000 = this.speedVector;
        var10000.y *= this.speed * Gdx.graphics.getDeltaTime() * 60.0F;
        scaledOriginX = originX * beamLengthScale;
        scaledOriginY = originY * beamHeightScale;
        super.update();
        this.color = new Color(1.0F,1.0F,1.0F,1.0F);
    }

    public void render(SpriteBatch sb) {
        if (!this.isDone) {
            //sb.setColor(this.color);
            sb.setColor(this.color);
            //sb.draw(this.yoshiko, this.cX, this.cY, (float)this.yoshiko.packedWidth * 0.5F, (float)this.yoshiko.packedHeight * 0.5F, (float)this.yoshiko.packedWidth, (float)this.yoshiko.packedHeight, this.scale, this.scale, this.rotation);
            sb.draw(this.yoshiko, this.cX, this.cY, 0, 0, (float)this.yoshiko.packedWidth, (float)this.yoshiko.packedHeight, this.scale, this.scale, this.rotation);

            //sb.draw(this.imgBeam, this.dX-(float)this.imgBeam.packedWidth * 0.5F, this.dY-(float)this.imgBeam.packedHeight * 0.5F, (float)this.imgBeam.packedWidth * 0.5F, (float)this.imgBeam.packedHeight * 0.5F, (float)this.imgBeam.packedWidth, (float)this.imgBeam.packedHeight, this.beamScale, this.beamScale, this.rotation);
            //sb.setColor(this.color);
            //sb.draw(this.imgBeam, (float)(this.dX - Math.cos(360.0F-this.rotation)*this.scaledOriginX - Math.sin(360.0F-this.rotation)*this.scaledOriginY*2)-this.scaledOriginX/2.0F, (float)(this.dY + Math.sin(360.0F - this.rotation)*this.scaledOriginX - Math.cos(360.0F - this.rotation)*this.scaledOriginY), this.scaledOriginX , this.scaledOriginY, (float)this.imgBeam.packedWidth, (float)this.imgBeam.packedHeight, this.beamLengthScale, this.beamHeightScale, this.rotation);
            //logger.info("destination: " + this.dX + "/// first section: " + (Math.cos(360.0F-this.rotation)*this.scaledOriginX) + "/// second section: " + (Math.sin(360.0F-this.rotation)*this.scaledOriginY));

        }

    }

    public void dispose() {
    }
}
