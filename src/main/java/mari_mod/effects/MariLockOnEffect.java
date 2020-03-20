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
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import mari_mod.helpers.CameraHelper;
import mari_mod.patches.MariDisableInputsPatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariLockOnEffect extends AbstractGameEffect {
    public static final Logger logger = LogManager.getLogger(MariLockOnEffect.class.getName());
    private static final float EFFECT_DUR = 0.5F;
    private float cX;
    private float cY;
    private float sX;
    private float sY;
    private float dX;
    private float dY;
    private float dX1;
    private float dY1;
    private float dX2;
    private float dY2;
    private float forwardDuration;
    //SS = screen scale
    private float sSS;
    private float cSS;
    private float dSS1;
    private float dSS2;

    public static final float FIRST_FADE_TIME = 1F;
    public static final float TARGET_TIME = 2.5F;
    public static final float TOTAL_TIME = FIRST_FADE_TIME + TARGET_TIME;

    private Color overlayColor;

    private AtlasRegion overlayCircle;
    private float overlayCircleRotation;
    private float overlayCircleScale;
    private AtlasRegion overlayTarget;
    private float overlayTargetRotation;
    private AtlasRegion overlayCross;
    private float overlayCrossRotation;


    private boolean playedSound;
    public MariLockOnEffect(float x, float y) {

        this.duration = TOTAL_TIME;
        this.startingDuration = TOTAL_TIME;
        this.forwardDuration = 0.0f;
        //values in proportion
        this.sX = (float) 0.5f;
        this.sY = (float) 0.5f;
        this.cX = sX;
        this.cY = sY;
        this.dX = x;
        this.dY = y;
        this.dX1 = x/Settings.WIDTH;
        this.dY1 = y/Settings.HEIGHT;
        this.dX2 = 0.5f;
        this.dY2 = 0.5f;

        Texture overlayCircleTexture = ImageMaster.loadImage("mari_mod/images/effects/mariOverlayCircle.png");
        overlayCircle = ImageMaster.vfxAtlas.addRegion("overlayCircle", overlayCircleTexture, 0,0,500,500);
        overlayCircleRotation = 0.0F;
        overlayCircleScale = 1.0F;
        Texture overlayTargetTexture = ImageMaster.loadImage("mari_mod/images/effects/mariOverlayTarget.png");
        overlayTarget = ImageMaster.vfxAtlas.addRegion("overlayTarget", overlayTargetTexture, 0,0,500,500);
        overlayTargetRotation = 0.0F;
        Texture overlayCrossTexture = ImageMaster.loadImage("mari_mod/images/effects/mariOverlayCross.png");
        overlayCross = ImageMaster.vfxAtlas.addRegion("overlayCross", overlayCrossTexture, 0,0,500,500);
        overlayCrossRotation = -30.0F;

        this.rotation = 0.0F;
        this.sSS = 1.0F;
        this.cSS = this.sSS;
        this.dSS1 = 5.0F;
        this.dSS2 = 1.0F;
        this.color = new Color(1.0f,1.0f,1.0f,1.0f);
        this.overlayColor = new Color(0.0f,0.0f,0.0f,0.0f);

    }

    public void update() {

        MariDisableInputsPatch.disableOn = true;
        //this.cX = MathUtils.lerp(sX,dX1,1 - (this.duration/this.startingDuration));
        //this.cY = MathUtils.lerp(sY,dY1,1 - (this.duration/this.startingDuration));
        //this.cSS = Interpolation.pow2In.apply(sSS,dSS1,1 - (this.duration/this.startingDuration));

        if(forwardDuration <= FIRST_FADE_TIME) {
            overlayColor.a = MathUtils.lerp(0, 1, Math.min((this.forwardDuration / FIRST_FADE_TIME), 1.0f));
        }else{
            this.cSS = Interpolation.pow3Out.apply(3.0f,4.0f,((this.forwardDuration-FIRST_FADE_TIME)/(TARGET_TIME)));
            CameraHelper.setCameraScale(cSS);
            CameraHelper.setCameraCentered(this.dX1, this.dY1);
            overlayCircleScale = Interpolation.pow3Out.apply(2.0f, 1.0f, Math.min(((this.forwardDuration-FIRST_FADE_TIME) / TARGET_TIME), 1.0f));

            overlayColor.a = 0;
        }

        overlayCircleRotation += Gdx.graphics.getDeltaTime() * 90.0f;
        overlayTargetRotation += Gdx.graphics.getDeltaTime() * -30.0f;
        overlayCrossRotation += Gdx.graphics.getDeltaTime() * 30.0f;

        super.update();
        this.forwardDuration += Gdx.graphics.getDeltaTime();
        if(this.isDone){
            MariDisableInputsPatch.disableOn = false;
            CameraHelper.resetCamera();
        }
    }

    public void render(SpriteBatch sb) {

        sb.setColor(overlayColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, (float)Settings.WIDTH, (float)Settings.HEIGHT);
        if(forwardDuration > FIRST_FADE_TIME) {
            sb.setColor(Color.WHITE);
            sb.draw(overlayCircle, dX - 250.f, dY - 250.f, 250.f, 250f, (float) overlayCircle.packedWidth, (float) overlayCircle.packedHeight, overlayCircleScale, overlayCircleScale, overlayCircleRotation);
            sb.draw(overlayTarget, dX - 250.f, dY - 250.f, 250.f, 250f, (float) overlayTarget.packedWidth, (float) overlayTarget.packedHeight, overlayCircleScale, overlayCircleScale, overlayTargetRotation);
            sb.draw(overlayCross, dX - 250.f, dY - 250.f, 250.f, 250f, (float) overlayCross.packedWidth, (float) overlayCross.packedHeight, overlayCircleScale, overlayCircleScale, overlayCrossRotation);
        }
    }

    public void dispose() {
        System.out.println("disposed lock on!");
    }
}
