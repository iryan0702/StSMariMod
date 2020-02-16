//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.effects;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.PetalEffect;
import com.megacrit.cardcrawl.vfx.SpotlightEffect;

public class PerfectPerformanceEffect extends AbstractGameEffect {
    private float timer = 0.1F;
    private final float START_TIME = 7.0F;
    private final float FEATHER_END = 3.0F;
    public float tempVolume;

    public PerfectPerformanceEffect() {
        this.duration = START_TIME;
        this.tempVolume = 0;
    }

    public void update() {
        if (this.duration == START_TIME) {
            tempVolume = Settings.MUSIC_VOLUME;
            CardCrawlGame.sound.playV("MariMod:MariPerfectPerformance", 2.8f);
            AbstractDungeon.effectsQueue.add(new SpotlightEffect());
        }

        System.out.println("volume is: " + Settings.MUSIC_VOLUME);
        if(Settings.MUSIC_VOLUME > 0){
            Settings.MUSIC_VOLUME = Settings.MUSIC_VOLUME/1.08f - 0.001f;
            if(Settings.MUSIC_VOLUME < 0f){
                Settings.MUSIC_VOLUME = 0f;
            }
            CardCrawlGame.music.updateVolume();
            Settings.soundPref.putFloat("Music Volume", Settings.MUSIC_VOLUME);
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        this.timer -= Gdx.graphics.getDeltaTime();
        if (this.timer < 0.0F && this.duration > FEATHER_END) {
            this.timer += 0.1F;
            AbstractDungeon.effectsQueue.add(new ColouredPetalEffect(new Color(0.7F, MathUtils.random(0.6F, 0.8F), MathUtils.random(0.9F, 1.0F), 1.0F)));
            AbstractDungeon.effectsQueue.add(new ColouredPetalEffect(new Color(0.6F, MathUtils.random(0.5F, 0.7F), MathUtils.random(0.8F, 1.0F), 1.0F)));
        }

        if (this.duration < 0.0F) {
            Settings.MUSIC_VOLUME = tempVolume;
            CardCrawlGame.music.updateVolume();
            Settings.soundPref.putFloat("Music Volume", Settings.MUSIC_VOLUME);
            System.out.println("volume is: " + Settings.MUSIC_VOLUME);
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
