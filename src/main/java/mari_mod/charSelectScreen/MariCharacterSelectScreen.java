package mari_mod.charSelectScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class MariCharacterSelectScreen {

    public Texture backgroundTexture;
    public TextureAtlas.AtlasRegion background;
    public Texture backgroundGlowTexture;
    public TextureAtlas.AtlasRegion backgroundGlow;
    public Texture foregroundTexture;
    public TextureAtlas.AtlasRegion foreground;
    public Texture particleTexture;
    public TextureAtlas.AtlasRegion particleImg;

    public Color backgroundColor;

    public Color foregroundColor;
    public float foregroundColorTimer;

    public Color foregroundGlowColor;
    public float foregroundGlowColorTimer;

    public ArrayList<GlowyParticle> particles;
    public float particleTimer;

    public static float particleTimeDilation = 45.0f;

    public MariCharacterSelectScreen(){
        foregroundColorTimer = 0;
        foregroundGlowColorTimer = 0;
        particleTimer = 0;
        particles = new ArrayList<>();
    }

    public void update(){

        particleTimer -= Gdx.graphics.getRawDeltaTime() * particleTimeDilation;
        particleTimeDilation = MathUtils.lerp(particleTimeDilation, 1.0f, Gdx.graphics.getRawDeltaTime() * 2.0f);
        if(particleTimer <= 0){
            particles.add(new GlowyParticle(MathUtils.random(0,7)));
            particleTimer += 0.12f;
        }

        foregroundColorTimer += Gdx.graphics.getRawDeltaTime();
        float foregroundBrightness = 0.90f + MathUtils.sin(foregroundColorTimer * MathUtils.PI/4.0f) * 0.10f;
        foregroundColor = new Color(foregroundBrightness, foregroundBrightness, foregroundBrightness, 1.0f);

        foregroundGlowColorTimer += Gdx.graphics.getRawDeltaTime();
        float foregroundGlowBrightness = 0.90f + MathUtils.sin(foregroundGlowColorTimer * MathUtils.PI/2.4f) * 0.10f;
        foregroundGlowColor = new Color(foregroundGlowBrightness, foregroundGlowBrightness, foregroundGlowBrightness, 0.50f);

        Iterator itr = particles.iterator();
        while(itr.hasNext()){
            GlowyParticle p = (GlowyParticle)itr.next();
            p.update();
            if(p.toBeDeleted){
                itr.remove();
            }
        }
    }

    public void render(SpriteBatch sb){
        if(backgroundColor == null){
            backgroundColor = Color.WHITE.cpy();
        }
        if(foregroundColor == null){
            foregroundColor = Color.WHITE.cpy();
        }
        if(foregroundGlowColor == null){
            foregroundGlowColor = Color.WHITE.cpy();
        }
        if(backgroundTexture == null){
            backgroundTexture = ImageMaster.loadImage("mari_mod/images/charSelect/MariPortraitBg.png");
            background = ImageMaster.vfxAtlas.addRegion("MariSelectBg", backgroundTexture, 0, 0, 1920, 1200);
        }
        if(foregroundTexture == null){
            foregroundTexture = ImageMaster.loadImage("mari_mod/images/charSelect/MariPortraitFront.png");
            foreground = ImageMaster.vfxAtlas.addRegion("MariSelectFront", foregroundTexture, 0, 0, 1920, 1200);
        }
        if(particleTexture == null){
            particleTexture = ImageMaster.loadImage("mari_mod/images/charSelect/MariGlowyParticle.png");
            particleImg = ImageMaster.vfxAtlas.addRegion("MariSelectParticles", particleTexture, 0, 0, 256, 256);
        }
        if(backgroundGlowTexture == null){
            backgroundGlowTexture = ImageMaster.loadImage("mari_mod/images/charSelect/MariPortraitGlow.png");
            backgroundGlow = ImageMaster.vfxAtlas.addRegion("MariSelectGlow", backgroundGlowTexture, 0, 0, 1920, 1200);
        }
        Color prevColor = sb.getColor();

        sb.setColor(foregroundColor);
        sb.draw(this.background, (float) Settings.WIDTH / 2.0F - 960.0F, (float)Settings.HEIGHT / 2.0F - 600.0F, 960.0F, 600.0F, 1920.0F, 1200.0F, Settings.scale, Settings.scale, 0.0F);

        sb.setColor(foregroundGlowColor);
        sb.draw(this.backgroundGlow, (float) Settings.WIDTH / 2.0F - 960.0F, (float)Settings.HEIGHT / 2.0F - 600.0F, 960.0F, 600.0F, 1920.0F, 1200.0F, Settings.scale, Settings.scale, 0.0F);


        Iterator itr = particles.iterator();
        while(itr.hasNext()){
            ((GlowyParticle)itr.next()).render(sb, particleImg);
        }

        sb.setColor(foregroundColor);
        sb.draw(this.foreground, (float) Settings.WIDTH / 2.0F - 960.0F, (float)Settings.HEIGHT / 2.0F - 600.0F, 960.0F, 600.0F, 1920.0F, 1200.0F, Settings.scale, Settings.scale, 0.0F);

        sb.setColor(prevColor);
    }

    public class GlowyParticle{

        public static final boolean COLOR_TEST_MODE = false;

        float w16 = Settings.WIDTH/16.0f;
        float h16 = Settings.HEIGHT/16.0f;

        float w64 = Settings.WIDTH/64.0f;
        float h64 = Settings.HEIGHT/64.0f;

        float w256 = Settings.WIDTH/256f;
        float h256 = Settings.HEIGHT/256f;

        float aX = 0;
        float aY = 0;
        float vX = 0;
        float maxVX = 9999.0f;
        float maxVY = 9999.0f;
        float vY = 0;
        float cX = 0;
        float cY = 0;

        float inherentAlpha = 1f;
        float changingAlpha = 1f;
        float inherentScale = 1f;
        float changingScale = 1f;

        Color currentColor;
        float cR = 1f;
        float cG = 0.7f;
        float cB = 1f;

        boolean blinker = false;
        float blinkTimer = 0;

        boolean toBeDeleted = false;
        int pathMode = 0;

        public GlowyParticle(){
            this(0);
        }

        public GlowyParticle(int forcedMode){
            this.inherentAlpha = MathUtils.random(0.75f, 1.0f);
            if(COLOR_TEST_MODE) currentColor = Color.RED.cpy();
            currentColor = Color.WHITE.cpy();
            pathMode = forcedMode;
            if(pathMode == 0){
                this.cX = 13 * w16;
                this.cY = 0 * h16;
                this.vX = w64 * -2;
                this.aY = h256 * 2.5f;
                cR = 0.2f;
                cG = 0.0f;
                cB = 0.5f;
                this.inherentScale = MathUtils.random(0.05f, MathUtils.random(0.02f, 0.15f));
            }
            if(pathMode == 1){
                this.cX = 11 * w16;
                this.cY = 3 * h16;
                this.vX = w64 * -2.5f;
                this.vY = h64 * 2.0f;
                this.aY = h256 * -0.5f;
                cR = 0.2f;
                cG = 0.0f;
                cB = 0.5f;
                this.inherentScale = MathUtils.random(0.05f, MathUtils.random(0.02f, 0.15f));
            }
            if(pathMode == 2){
                this.cX = 10 * w16;
                this.cY = 10 * h16;
                this.vX = w64 * -2.5f;
                this.vY = h64 * -0.5f;
                this.aY = h256 * 1f;
                this.inherentScale = MathUtils.random(0.05f, MathUtils.random(0.02f, 0.15f));
            }
            if(pathMode == 3){
                this.cX = 10 * w16;
                this.cY = 10 * h16;
                this.vX = w64 * -2.5f;
                this.vY = h64 * 0.5f;
                this.aY = h256 * 0.1f;
                this.inherentScale = MathUtils.random(0.05f, MathUtils.random(0.02f, 0.15f));
            }
            if(pathMode == 4){
                this.cX = 12 * w16;
                this.cY = 10 * h16;
                this.vX = w64 * -2.4f;
                this.vY = h64 * 0.7f;
                this.aY = h256 * 2.2f;
                this.inherentScale = MathUtils.random(0.05f, MathUtils.random(0.02f, 0.15f));
            }
            if(pathMode == 5){
                this.cX = 12 * w16;
                this.cY = 10 * h16;
                this.vX = w64 * -1.5f;
                this.vY = h64 * 4.7f;
                this.aY = h256 * -2.4f;
                this.inherentScale = MathUtils.random(0.05f, MathUtils.random(0.02f, 0.15f));
            }
            if(pathMode == 6){
                this.cX = 14 * w16;
                this.cY = 0 * h16;
                this.vX = w64 * 1.8f;
                this.aX = w256 * -1.3f;
                this.vY = h64 * 2.8f;
                this.inherentScale = MathUtils.random(0.05f, MathUtils.random(0.02f, 0.15f));
            }
            if(pathMode == 7){
                this.cX = 13 * w16;
                this.cY = 9 * h16;
                this.vX = w64 * 1.8f;
                this.aX = w256 * -1.3f;
                this.vY = h64 * 2.8f;
                this.inherentScale = MathUtils.random(0.05f, MathUtils.random(0.02f, 0.15f));
            }
            if(MathUtils.randomBoolean(0.12f)){
                blinker = true;
            }

            this.cX += MathUtils.random(w16 * -0.2f, w16 * 0.2f);
            this.cY += MathUtils.random(h16 * -0.2f, h16 * 0.2f);
        }

        public float offset = 300 * Settings.scale;
        public void update(){

            float dTime = Gdx.graphics.getRawDeltaTime() * particleTimeDilation;

            switch(pathMode) {
                case 0:
                    if(this.cX > w16 * 6 && this.cX < w16 * 10){
                        if(COLOR_TEST_MODE) currentColor = Color.ORANGE.cpy();
                        this.aY = h256 * -2.5f;
                        this.aX = h256 * -0.5f;
                        if(this.vY < 0){
                            this.vY = 0;
                            this.aY = 0;
                        }
                    }else if(this.cX > w16 * 3 && this.cX <= w16 * 6){
                        if(COLOR_TEST_MODE) currentColor = Color.YELLOW.cpy();
                        this.aY = h256 * -0.25f;
                    }else if(this.cX < w16 * 3){
                        if(COLOR_TEST_MODE) currentColor = Color.GREEN.cpy();
                        this.aY = h256 * -3.0f;
                    }
                    break;
                case 1:
                    if(this.cX > w16 * 5 && this.cX < w16 * 7){
                        if(COLOR_TEST_MODE) currentColor = Color.ORANGE.cpy();
                        this.aY = h256 * 2.5f;
                        this.aX = h256 * 0.5f;
                        if(this.vX > w64 * -1.5f){
                            this.vX = -1.5f;
                        }
                    }else if(this.cX < w16 * 5){
                        if(COLOR_TEST_MODE) currentColor = Color.YELLOW.cpy();
                        this.aY = h256 * -4f;
                        this.aX = h256 * 0.3f;
                    }
                    break;
                case 2:
                    if(this.cX < w16 * 8) {
                        if(COLOR_TEST_MODE) currentColor = Color.ORANGE.cpy();
                        this.aY = h256 * 2.8f;
                        this.aX = h256 * 2f;
                        if(this.vX > w64 * -1){
                            this.vX = w64 * -1;
                        }
                        if(this.vY > w64 * 3){
                            this.vY = w64 * 3;
                        }
                    }
                    break;
                case 3:
                    if(this.cX < w16 * 6) {
                        if(COLOR_TEST_MODE) currentColor = Color.ORANGE.cpy();
                        this.aY = h256 * 1.9f;
                        this.aX = h256 * 1f;
                        if(this.vX > w64 * -1){
                            this.vX = w64 * -1;
                        }
                        if(this.vY > w64 * 3){
                            this.vX = w64 * 4;
                        }
                    }
                    break;
                case 4:
                    if(this.cX < w16 * 5) {
                        if(COLOR_TEST_MODE) currentColor = Color.YELLOW.cpy();
                        this.aY = h256 * 0.4f;
                        this.aX = h256 * -0.2f;
                    }else if(this.cX < w16 * 9) {
                        if(COLOR_TEST_MODE) currentColor = Color.ORANGE.cpy();
                        this.aY = h256 * -2.6f;
                        this.aX = h256 * -0.1f;
                    }
                    break;
                case 5:
                    if(this.cX < w16 * 6) {
                        if(COLOR_TEST_MODE) currentColor = Color.YELLOW.cpy();
                        this.aY = h256 * 0.4f;
                        this.aX = h256 * -0.3f;
                    }else if(this.cX < w16 * 9) {
                        if(COLOR_TEST_MODE) currentColor = Color.ORANGE.cpy();
                        this.aY = 0;
                        this.vY = MathUtils.lerp(this.vY, 0, dTime * 0.1f);
                        this.aX = h256 * -0.3f;
                    }
                    break;
                case 6:
                    if(this.cY > h16 * 8) {
                        if(COLOR_TEST_MODE) currentColor = Color.ORANGE.cpy();
                        this.aY = h256 * 0.2f;
                        this.aX = w256 * 1.2f;
                    }
                    break;
                case 7:
                    break;

                default:
                    break;
            }

            vX += aX * dTime;
            vY += aY * dTime;

            if(vX != 0 && Math.abs(vX) > maxVX) vX = maxVX * (vX/Math.abs(vX));
            if(vY != 0 && Math.abs(vY) > maxVY) vY = maxVY * (vY/Math.abs(vY));

            cX += vX * dTime;
            cY += vY * dTime;

            if(this.cX < -1 * offset || this.cX > Settings.WIDTH + offset || this.cY < -1 * offset || this.cY > Settings.HEIGHT + offset){
                this.toBeDeleted = true;
            }

            if(blinker){
                if(this.inherentScale > 0.1f){
                    this.inherentScale = 0.1f;
                }
                blinkTimer += dTime;
                this.changingScale = (MathUtils.sin(blinkTimer) + 1.0f)/1.5f;
                this.changingAlpha = (MathUtils.sin(blinkTimer) + 1.0f)/2.0f;
            }

            if(cX < w16 * 6 && cY < h16 * 10){
                changingAlpha = MathUtils.lerp(changingAlpha, 0.2f, dTime * 0.1f);
            }

            if(cX > w16 * 6 && cX < w16 * 10 && cY < h16 * 8){
                cR = MathUtils.lerp(cR, 0.8f, dTime * 0.15f);
                cG = MathUtils.lerp(cG, 0.0f, dTime * 0.15f);
                cB = MathUtils.lerp(cB, 1.0f, dTime * 0.15f);
            }else if(cX > w16 * 6 && cX < w16 * 12 && cY > h16 * 10){
                cG = MathUtils.lerp(cG, 0.4f, dTime * 0.1f);
            }else if(cX < w16 * 6 && cY > h16 * 10){
                cG = MathUtils.lerp(cG, 1.0f, dTime * 0.1f);
            }

            if(!COLOR_TEST_MODE){
                this.currentColor = new Color(cR, cG, cB, changingAlpha * inherentAlpha);
            }
        }

        public void render(SpriteBatch sb, TextureAtlas.AtlasRegion img){

            float masterAlpha = inherentAlpha * changingAlpha;
            float masterScale = inherentScale * changingScale;
            this.currentColor.a = masterAlpha;
            float settingScale = masterScale * Settings.scale;
            sb.setColor(this.currentColor);
            sb.draw(img, cX - (128 * settingScale), cY - (128 * settingScale), 0,0,256,256, masterScale, masterScale, 0.0f);
        }
    }
}
