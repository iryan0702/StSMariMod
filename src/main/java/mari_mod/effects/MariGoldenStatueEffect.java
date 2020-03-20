//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.effects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariGoldenStatueEffect extends AbstractGameEffect {
    public static final Logger logger = LogManager.getLogger(MariGoldenStatueEffect.class.getName());
    private static final float EFFECT_DUR = 0.5F;
    private float cX;
    private float cY;
    private float sX;
    private float sY;
    private float dX;
    private float dY;
    private float pivot_x = 0;
    private float pivot_y = 0;
    private static final float INITIAL_ROTATION = 25F;
    private static final float SLIDE_IN_ROTATION = 0;
    private static final float WOBBLE_FORWARD_ROTATION = -5F;
    private static final float WOBBLE_BACKWARD_ROTATION = 6F;
    public static final float FALL_ROTATION = -80F;
    public static final float SLIDE_IN_DURATION = 0.5F;
    public static final float WOBBLE_FORWARD_DURATION = 0.3F;
    public static final float WOBBLE_BACKWARD_DURATION = 1.0F;
    public static final float FALL_DURATION = 1.0F;
    public static final float FADE_DURATION = 2.0F;
    public static final float FADE_END = 0.0f;
    public static final float FALL_END = FADE_END + FADE_DURATION;
    public static final float WOBBLE_BACKWARD_END = FALL_END + FALL_DURATION;
    public static final float WOBBLE_FORWARD_END = WOBBLE_BACKWARD_END + WOBBLE_BACKWARD_DURATION;
    public static final float SLIDE_IN_END = WOBBLE_FORWARD_END + WOBBLE_FORWARD_DURATION;
    public static final float ANIMATION_START = SLIDE_IN_END + SLIDE_IN_DURATION;

    private AtlasRegion imgStatue;


    private boolean playedSound;
    public MariGoldenStatueEffect(float x) {

        this.scale = Settings.scale * 1.5f;

        Texture statue = ImageMaster.loadImage("mari_mod/images/effects/mariStatue.png");

        imgStatue = ImageMaster.vfxAtlas.addRegion("statue", statue, 0,0,200,457);
        this.pivot_y = imgStatue.originalHeight * 0.105f * (this.scale); //0.105

        this.duration = ANIMATION_START;
        this.startingDuration = ANIMATION_START;
        this.sX = (float) Settings.WIDTH * -0.5f;
        this.sY = AbstractDungeon.floorY - Settings.scale * 75.0f;
        this.cX = sX;
        this.cY = sY;
        this.dX = x - Settings.scale * 550.0f;
        this.dY = sY;
        this.rotation = INITIAL_ROTATION;
        this.color = new Color(1.0F,1.0F,1.0F,1.0F);



        this.playedSound = false;
        System.out.println(Settings.scale);
    }

    public void update() {

        if(duration <= ANIMATION_START && duration >= SLIDE_IN_END){
            this.cX = Interpolation.pow2In.apply(dX, sX,(duration - SLIDE_IN_END)/SLIDE_IN_DURATION);
            this.rotation = Interpolation.pow2.apply(SLIDE_IN_ROTATION, INITIAL_ROTATION,(duration - SLIDE_IN_END)/SLIDE_IN_DURATION);
        }else if(duration <= SLIDE_IN_END && duration >= WOBBLE_FORWARD_END){
            this.rotation = Interpolation.pow3In.apply(WOBBLE_FORWARD_ROTATION, SLIDE_IN_ROTATION,(duration - WOBBLE_FORWARD_END)/WOBBLE_FORWARD_DURATION);
        }else if(duration <= WOBBLE_FORWARD_END && duration >= WOBBLE_BACKWARD_END){
            this.rotation = Interpolation.pow3.apply(WOBBLE_BACKWARD_ROTATION, WOBBLE_FORWARD_ROTATION,(duration - WOBBLE_BACKWARD_END)/WOBBLE_BACKWARD_DURATION);
        }else if(duration <= WOBBLE_BACKWARD_END && duration >= FALL_END){
            this.rotation = Interpolation.pow4Out.apply(FALL_ROTATION, WOBBLE_BACKWARD_ROTATION,(duration - FALL_END)/FALL_DURATION);
        }else if(duration <= FALL_END && duration >= FADE_END){
            this.color.a = Interpolation.linear.apply(0, 1,(duration - FADE_END)/FADE_DURATION);
        }

        if(this.rotation <= 0){
            pivot_x = imgStatue.originalWidth * 0.6f * (this.scale);
        }else{
            pivot_x = imgStatue.originalWidth * 0.28f * (this.scale);
        }
        super.update();
    }

    public void render(SpriteBatch sb) {
        if (!this.isDone) {
            sb.setColor(this.color);
            sb.draw(imgStatue, this.cX, this.cY, (float)pivot_x, (float)pivot_y, (float)imgStatue.packedWidth * this.scale, (float)imgStatue.packedHeight * this.scale, 1.0f, 1.0f, this.rotation);

        }

    }

    public void dispose() {
    }
}
