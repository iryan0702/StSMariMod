//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import mari_mod.helpers.MusicHelper;

public class MariOdoreEffect extends AbstractGameEffect {

    public float raindropTimer;
    public float musicTimer = -999f;
    public final float MUSIC_END = 74f;
    public boolean finishedMusic = false;
//    public MariTheTemple event;
    public MariOdoreEffect() {
//        event = sourceEvent;
        raindropTimer = 1.5f;
    }

    //moved ALL notes after 43f back by 0.1f
    public float[] dropTiming = {3.57f, 3.97f, 6.17f, 9.13f, 10.17f, 12.23f, 15.23f, 16.6f, 17.97f, 19.27f, 19.90f, 20.60f, 21.90f, 22.57f, 23.27f, 23.90f, 24.53f, 25.23f, 25.93f, 27.26f, 27.93f, 28.67f, 29.97f, 30.63f, 31.3f, 31.93f, 32.63f, 33.6f, 33.9f, 37.97f, 38.7f, 39.43f, 40.7f, 41.4f, 42f, 43.1f, 43.83f, 44.57f, 45.07f, 45.83f, 46.8f, 47.13f, 48.5f, 49.2f, 49.6f, 51.0f, 51.5f, 52.1f, 52.8f, 53.5f, 54.5f, 54.93f, 55.33f, 56.23f, 56.83f, 57.43f, 60.83f, 60.93f, 61.03f};
    public float offsetTiming = 1f;
    public int currentDrop = 0;

    public void stopEffect(){
        if(musicTimer >= 0) {
            currentDrop = dropTiming.length;
            musicTimer = MUSIC_END;
            CardCrawlGame.music.justFadeOutTempBGM();
            MusicHelper.fadeInAllMainMusic();
        }
        this.isDone = true;
    }

    @Override
    public void update() {
        if(musicTimer > -1f && !finishedMusic){
            musicTimer += Gdx.graphics.getRawDeltaTime();
            if(musicTimer >= MUSIC_END){
                CardCrawlGame.music.justFadeOutTempBGM();
                finishedMusic = true;
            }
        }

        raindropTimer -= Gdx.graphics.getRawDeltaTime();
        if(raindropTimer <= 0){
            if(musicTimer > 0){
                AbstractDungeon.effectsQueue.add(new MariRaindropEffect(MariRaindropEffect.Sound.PLATES));
            }else{
                AbstractDungeon.effectsQueue.add(new MariRaindropEffect(MariRaindropEffect.Sound.FLOOR));
            }

            if(musicTimer > 70f){
                raindropTimer = MathUtils.random(2f);
            }else if(musicTimer > 65f){
                raindropTimer = MathUtils.random(3f);
            }else if(musicTimer > 28f){
                raindropTimer = MathUtils.random(8f, 20f);
            }else if(musicTimer > 16f){
                raindropTimer = MathUtils.random(4f, 8f);
            }else if(musicTimer > 7f){
                raindropTimer = MathUtils.random(2f, 4f);
            }else if(musicTimer > 2f){
                raindropTimer = MathUtils.random(5f, 6f);
            }else{
                raindropTimer = MathUtils.random(1.33f);
            }

        }

        if(musicTimer > 65f && raindropTimer > 3f){
            raindropTimer = 3f;
        }

        if(currentDrop < dropTiming.length && musicTimer >= dropTiming[currentDrop] - offsetTiming){
            AbstractDungeon.effectsQueue.add(new MariRaindropEffect(MariRaindropEffect.Sound.NONE));
            currentDrop++;
        }
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
