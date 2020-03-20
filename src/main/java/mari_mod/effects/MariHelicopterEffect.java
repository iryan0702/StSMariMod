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
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class MariHelicopterEffect extends AbstractGameEffect {
    public static final Logger logger = LogManager.getLogger(MariHelicopterEffect.class.getName());
    private static final float EFFECT_DUR = 0.5F;
    private float cX;
    private float cY;
    private float sX;
    private float sY;
    private float dX1;
    private float dY1;
    private float dX2;
    private float dY2;
    private static final float SOUND_INTRO_DURATION = 0.5F;
    private static final float DOWN_ANIMATION_DURATION = 2.0F;
    private static final float STALL_ANIMATION_DURATION = 4.0F;
    private static final float UP_ANIMATION_DURATION = 2.0F;
    public static final float MAX_DURATION = UP_ANIMATION_DURATION + STALL_ANIMATION_DURATION + DOWN_ANIMATION_DURATION + SOUND_INTRO_DURATION;
    public static final float UP_START = UP_ANIMATION_DURATION;
    public static final float STALL_START = UP_START + STALL_ANIMATION_DURATION;
    public static final float DOWN_START = STALL_START + DOWN_ANIMATION_DURATION;
    public static final float SOUND_START = DOWN_START + SOUND_INTRO_DURATION;
    private Vector2 speedVector;
    private float speed;
    //private AtlasRegion imgCar;
    private AtlasRegion imgBeam;
    private ArrayList<AtlasRegion> imgHeli = new ArrayList<>();
    private float beamLengthScale;
    private float beamHeightScale;
    private float beamScale;
    private float delayTillNextSound;
    public boolean inStall;
    public boolean manuallyEnded;
    public float randomDuration;


    private boolean playedSound;
    public MariHelicopterEffect(float x, float y) {

        //TextureAtlas textureRegion = new TextureAtlas("mari_mod/images/effects/mariHelicopter.atlas");
        //imgCar = textureRegion.findRegion("mariHelicopter1");
        /*
        Texture busImg = ImageMaster.loadImage("mari_mod/images/effects/MariTheFlyingCar.png");
        imgCar = ImageMaster.vfxAtlas.addRegion("MariTheFlyingCar", busImg, 0,0,300,145);
        */

        Texture heli1 = ImageMaster.loadImage("mari_mod/images/effects/mariHelicopter1.png");
        Texture heli2 = ImageMaster.loadImage("mari_mod/images/effects/mariHelicopter2.png");
        Texture heli3 = ImageMaster.loadImage("mari_mod/images/effects/mariHelicopter3.png");
        Texture heli4 = ImageMaster.loadImage("mari_mod/images/effects/mariHelicopter4.png");
        Texture heli5 = ImageMaster.loadImage("mari_mod/images/effects/mariHelicopter5.png");

        imgHeli.add(ImageMaster.vfxAtlas.addRegion("heli1", heli1, 0,0,350,172));
        imgHeli.add(ImageMaster.vfxAtlas.addRegion("heli2", heli2, 0,0,350,172));
        imgHeli.add(ImageMaster.vfxAtlas.addRegion("heli3", heli3, 0,0,350,172));
        imgHeli.add(ImageMaster.vfxAtlas.addRegion("heli4", heli4, 0,0,350,172));
        imgHeli.add(ImageMaster.vfxAtlas.addRegion("heli5", heli5, 0,0,350,172));


        this.duration = MAX_DURATION;
        this.startingDuration = MAX_DURATION;
        this.sX = (float) Settings.WIDTH/3F;
        this.sY = (float) Settings.HEIGHT*1.5F;
        this.cX = sX;
        this.cY = sY;
        this.dX1 = x;
        this.dY1 = y;
        this.dX2 = (float) Settings.WIDTH*1.2F;
        this.dY2 = (float) Settings.HEIGHT*1.5F;
        this.speed = MathUtils.random(20.0F * Settings.scale, 40.0F * Settings.scale);
        this.speedVector = new Vector2(dX1-sX, dY1-sY);
        this.speedVector.nor();
        this.speedVector.angle();
        this.rotation = 0.0F;//this.speedVector.angle();
        this.beamLengthScale = 1.0F;
        this.beamScale = 0.0F;
        Vector2 var10000 = this.speedVector;
        var10000.x *= this.speed;
        var10000 = this.speedVector;
        var10000.y *= this.speed;
        this.scale = Settings.scale * 4.0F;
        this.inStall = false;
        this.manuallyEnded = false;
        this.randomDuration = MathUtils.random(MathUtils.PI2);

        this.playedSound = false;

    }

    public void update() {

        if(!this.playedSound){
            //CardCrawlGame.screenShake.rumble(2.0F);
            CardCrawlGame.sound.playV("MariMod:MariHeliIn", 10.0F);
            this.playedSound = true;
            delayTillNextSound = 1.98F;
        }
        if(this.playedSound && delayTillNextSound <= 0){

            if(this.duration <= UP_START) {
                CardCrawlGame.sound.playV("MariMod:MariHeliOut", 10.0F);
                delayTillNextSound = 999.0F;
            }else{
                CardCrawlGame.sound.playV("MariMod:MariHeliMid", 10.0F);
                delayTillNextSound = 1.0F;
            }
        }

        if(DOWN_START >= this.duration && this.duration >= STALL_START) {

            this.cX = Interpolation.pow2In.apply(this.dX1, this.sX, (this.duration-STALL_START) / DOWN_ANIMATION_DURATION);
            this.cY = Interpolation.Pow.pow4In.apply(this.dY1, this.sY, (this.duration-STALL_START) / DOWN_ANIMATION_DURATION);
            this.rotation = Interpolation.Pow.pow2In.apply(0, -20.0F, (this.duration-STALL_START) / DOWN_ANIMATION_DURATION);

        }else if(STALL_START >= this.duration && this.duration >= UP_START) {

            this.cX = this.dX1;
            this.cY = this.dY1;
            if(this.manuallyEnded) {
                this.duration = UP_START;
            }

        }else if(UP_START >= this.duration && this.duration >= 0.0F) {

            this.cX = Interpolation.pow2Out.apply(this.dX2, this.dX1, (this.duration-0.0F) / UP_ANIMATION_DURATION);
            this.cY = Interpolation.Pow.pow4Out.apply(this.dY2, this.dY1, (this.duration-0.0F) / UP_ANIMATION_DURATION);
            this.rotation = Interpolation.Pow.pow2Out.apply(30.0F, 0, (this.duration-0.0F) / UP_ANIMATION_DURATION);

        }

        float randomYDrift = (MathUtils.sin(this.randomDuration * MathUtils.PI * 0.15F) * Settings.scale * 14.0F);
        randomYDrift *= randomYDrift;
        float randomXDrift = 0;
        randomXDrift += MathUtils.sin(this.randomDuration * MathUtils.PI * 0.15F) * Settings.scale * 75.0F;

        this.cY += randomYDrift;
        this.cX += randomXDrift;

        //this.beamLengthScale = this.duration / MAX_DURATION * 1.2F + 0.6F;
        //this.beamHeightScale = 1.0F - this.duration / MAX_DURATION * 0.5F;
        /*
        Vector2 var10000 = this.speedVector;
        var10000.x *= this.speed * Gdx.graphics.getDeltaTime() * 60.0F;
        var10000 = this.speedVector;
        var10000.y *= this.speed * Gdx.graphics.getDeltaTime() * 60.0F;
        */
        this.delayTillNextSound -= Gdx.graphics.getDeltaTime();
        this.randomDuration  -= Gdx.graphics.getDeltaTime();
        super.update();
        this.color = new Color(1.0F,1.0F,1.0F,0.75F);
    }

    public void endAnimation(){
        if(!this.manuallyEnded){
            this.manuallyEnded = true;
        }
    }

    public void render(SpriteBatch sb) {
        if (!this.isDone) {
            //sb.setColor(this.color);
            sb.setColor(this.color);
            AtlasRegion toDraw = imgHeli.get(MathUtils.random(4));
            sb.draw(toDraw, this.cX-(toDraw.packedWidth*this.scale/4), this.cY-(toDraw.packedHeight*this.scale/4), (float)toDraw.packedWidth * 0.5F, (float)toDraw.packedHeight * 0.5F, (float)toDraw.packedWidth, (float)toDraw.packedHeight, this.scale, this.scale, this.rotation);

            //sb.setColor(this.color);
            //sb.draw(this.imgBeam, (float)(this.dX - Math.cos(360.0F-this.rotation)*this.scaledOriginX - Math.sin(360.0F-this.rotation)*this.scaledOriginY*2)-this.scaledOriginX/2.0F, (float)(this.dY + Math.sin(360.0F - this.rotation)*this.scaledOriginX - Math.cos(360.0F - this.rotation)*this.scaledOriginY), this.scaledOriginX , this.scaledOriginY, (float)this.imgBeam.packedWidth, (float)this.imgBeam.packedHeight, this.beamLengthScale, this.beamHeightScale, this.rotation);
            //logger.info("destination: " + this.dX + "/// first section: " + (Math.cos(360.0F-this.rotation)*this.scaledOriginX) + "/// second section: " + (Math.sin(360.0F-this.rotation)*this.scaledOriginY));

        }

    }

    public void dispose() {
    }
}
