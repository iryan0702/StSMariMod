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
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import mari_mod.MariMod;

public class MariRaindropEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private float scale;
    //private float slightYScale;
    private float sy;
    private float dy;
    private float rotation = 0;
    private float duration;
    private float updateFrame;
    private final float MAX_DURATION = 1f;
    private final float FRAME_UPDATE_CAP = 1f/30f;
    private Sound sound;
    private boolean updated;


    public MariRaindropEffect(Sound endSound) {
        sound = endSound;
        duration = MAX_DURATION;
        rotation = 270 + MathUtils.random(89);
        x = (MathUtils.random(0.7f)+0.15f) * Settings.WIDTH;
        scale = MathUtils.random(0.1f,MathUtils.random(0.1f,0.8f));
        //slightYScale = scale * (1.0f + MathUtils.random(0.1f));
        dy = Settings.HEIGHT * -0.4f;
        sy = Settings.HEIGHT * 1.2f + (Settings.HEIGHT * scale * 6f);
        updated = false;
        updateFrame = 0f;
    }

    public void update() {
        this.duration -= Gdx.graphics.getRawDeltaTime();
        this.updateFrame += Gdx.graphics.getRawDeltaTime();

        if(this.duration <= 0){
            if(sound == Sound.FLOOR) {
                switch (MathUtils.random(4)) {
                    case 0:
                        CardCrawlGame.sound.playV("MariMod:MariRaindrop1", 5.0f);
                        break;
                    case 1:
                        CardCrawlGame.sound.playV("MariMod:MariRaindrop2", 5.0f);
                        break;
                    case 2:
                        CardCrawlGame.sound.playV("MariMod:MariRaindrop3", 5.0f);
                        break;
                    case 3:
                        CardCrawlGame.sound.playV("MariMod:MariRaindrop4", 5.0f);
                        break;
                    case 4:
                        CardCrawlGame.sound.playV("MariMod:MariRaindrop5", 5.0f);
                        break;
                }
            }
            if(sound == Sound.PLATES) {
                switch (MathUtils.random(4)) {
                    case 0:
                        CardCrawlGame.sound.playV("MariMod:MariRaindropP1", 5.0f);
                        break;
                    case 1:
                        CardCrawlGame.sound.playV("MariMod:MariRaindropP2", 5.0f);
                        break;
                    case 2:
                        CardCrawlGame.sound.playV("MariMod:MariRaindropP3", 5.0f);
                        break;
                    case 3:
                        CardCrawlGame.sound.playV("MariMod:MariRaindropP4", 5.0f);
                        break;
                    case 4:
                        CardCrawlGame.sound.playV("MariMod:MariRaindropP5", 5.0f);
                        break;
                }
            }

            this.duration = 0;
            this.isDone = true;
        }

        if(updateFrame >= FRAME_UPDATE_CAP) {
            updated = true;
            this.y = Interpolation.sineOut.apply(this.dy, this.sy, this.duration / MAX_DURATION);
            updateFrame -= FRAME_UPDATE_CAP;
            if(updateFrame >= FRAME_UPDATE_CAP){
                updateFrame = 0;
            }
        }
    }

    public void render(SpriteBatch sb) {
        if(updated) {
            sb.setColor(Color.WHITE);
            sb.draw(MariMod.rainVfx, this.x, this.y, 100f, 100f, 200.0F, 200.0F, this.scale, this.scale, this.rotation, 0, 0, 200, 200, false, false);
        }
    }

    public void dispose() {
    }

    public static enum Sound{
        FLOOR,
        PLATES,
        NONE;
        private Sound(){}
    }
}
