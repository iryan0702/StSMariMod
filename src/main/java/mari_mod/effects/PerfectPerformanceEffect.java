//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.effects;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.PetalEffect;
import com.megacrit.cardcrawl.vfx.SpotlightEffect;
import mari_mod.MariMod;
import mari_mod.helpers.MusicHelper;

public class PerfectPerformanceEffect extends AbstractGameEffect {
    private float timer = 0.1F;
    private final float START_TIME = 7.0F;
    private final float FEATHER_SPAWN_END = 3.0F;
    private final float SMOKE_SPAWN_END = 6.0F;
    private final float FEATHER_FADE = 1.0F;
    private boolean endFeathers;
    private boolean beginMusicFade;

    private final float BG_END_FADE_IN = 6.0F;
    private final float BG_START_FADE_OUT = 2.0F;

    private final float MUSIC_END_FADE_OUT = 6.5F;
    private final float MUSIC_START_FADE_IN = 2.0F;
    public float tempVolume;

    public PerfectPerformanceEffect() {
        this.duration = START_TIME;
        this.tempVolume = 0;
        this.color = new Color(1f,1f,1f,0);
        this.renderBehind = true;
        this.endFeathers = false;
        this.beginMusicFade = false;
    }

    public void update() {
        if (this.duration == START_TIME) {
            //tempVolume = Settings.MUSIC_VOLUME;
            //System.out.println("first put: temp volume is: " + tempVolume);
            CardCrawlGame.sound.playV("MariMod:MariPerfectPerformance", 2.8f);
            MusicHelper.silenceAllMusic(1.0f);
            AbstractDungeon.effectsQueue.add(new MariSpotlightEffect(this.duration + 4.0f,Settings.WIDTH/2f, Settings.WIDTH, true, 1.5f, new Color(1.0f, 0.8f, 0.9f, 0.5f)));
        }

        if(this.duration > MUSIC_END_FADE_OUT){
            //Settings.MUSIC_VOLUME = (this.duration - MUSIC_END_FADE_OUT)/(START_TIME - MUSIC_END_FADE_OUT) * tempVolume;
            //CardCrawlGame.music.updateVolume();
            //Settings.soundPref.putFloat("Music Volume", Settings.MUSIC_VOLUME);
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        this.timer -= Gdx.graphics.getDeltaTime();
        if (this.timer < 0.0F && this.duration > FEATHER_SPAWN_END) {
            this.timer += 0.1F;
            AbstractDungeon.effectsQueue.add(new ColouredPetalEffect(new Color(0.6F, MathUtils.random(0.5F, 0.7F), MathUtils.random(0.9F, 1.0F), MathUtils.random(1.0F, 0.8F))));
            AbstractDungeon.effectsQueue.add(new ColouredPetalEffect(new Color(0.5F, MathUtils.random(0.4F, 0.6F), MathUtils.random(0.8F, 1.0F), MathUtils.random(0.9F, 0.7F))));
            AbstractDungeon.effectsQueue.add(new ColouredPetalEffect(new Color(0.4F, MathUtils.random(0.3F, 0.5F), MathUtils.random(0.9F, 1.0F), MathUtils.random(0.9F, 0.7F))));
            AbstractDungeon.effectsQueue.add(new ColouredPetalEffect(new Color(0.5F, MathUtils.random(0.4F, 0.6F), MathUtils.random(0.8F, 1.0F), MathUtils.random(0.8F, 0.6F))));
            AbstractDungeon.effectsQueue.add(new ColouredPetalEffect(new Color(0.4F, MathUtils.random(0.3F, 0.5F), MathUtils.random(0.9F, 1.0F), MathUtils.random(0.8F, 0.6F))));

            if(this.duration > SMOKE_SPAWN_END) {
                for(int i = 0; i < 25; i++) {
                    AbstractDungeon.effectsQueue.add(new MariSmokeEffect());
                }
            }
        }else if(!endFeathers && this.duration < FEATHER_FADE){
            endFeathers = true;
            for(AbstractGameEffect e: AbstractDungeon.effectList){
                if(e instanceof ColouredPetalEffect){
                    ((ColouredPetalEffect) e).earlyFade();
                }
            }
        }

        if (this.duration > BG_END_FADE_IN) {
            this.color.a = (START_TIME - this.duration) / (START_TIME - BG_END_FADE_IN) * 0.5f;
        }else if(this.duration < BG_START_FADE_OUT){
            this.color.a = this.duration / this.BG_START_FADE_OUT * 0.5f;
        }else{
            this.color.a = 0.5f;
        }

        if(this.duration < MUSIC_START_FADE_IN && !beginMusicFade){
            MusicHelper.unsilenceSilencedMusic();
            this.beginMusicFade = true;
            //Settings.MUSIC_VOLUME = Math.max(Settings.MUSIC_VOLUME,((MUSIC_START_FADE_IN - this.duration)/MUSIC_START_FADE_IN) * tempVolume);
            //CardCrawlGame.music.updateVolume();
            //Settings.soundPref.putFloat("Music Volume", Settings.MUSIC_VOLUME);
            //System.out.println("volume is: " + Settings.MUSIC_VOLUME);
        }

        if (this.duration < 0.0F) {
            //Settings.MUSIC_VOLUME = Math.max(Settings.MUSIC_VOLUME,tempVolume);
            //CardCrawlGame.music.updateVolume();
            //Settings.soundPref.putFloat("Music Volume", Settings.MUSIC_VOLUME);
            //System.out.println("final put: temp volume is: " + Settings.MUSIC_VOLUME);
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        Texture img = MariMod.starFieldVfx;
        sb.draw(img, 0, 0, img.getWidth() * Settings.scale, img.getHeight() * Settings.scale);
    }

    public void dispose() {
    }
}
