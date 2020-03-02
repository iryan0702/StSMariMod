package mari_mod.helpers;

import basemod.ReflectionHacks;
import com.badlogic.gdx.audio.Music;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.audio.TempMusic;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class MusicHelper {
    private static ArrayList<MainMusic> silencedMainTracks = new ArrayList<>();
    private static ArrayList<TempMusic> silencedTempTracks = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public static void silenceAllMusic(float silenceSpeed){
        silencedMainTracks.clear();
        silencedTempTracks.clear();
        ArrayList<MainMusic> mainTrack = (ArrayList<MainMusic>)ReflectionHacks.getPrivate(CardCrawlGame.music, MusicMaster.class, "mainTrack");
        ArrayList<TempMusic> tempTrack = (ArrayList<TempMusic>)ReflectionHacks.getPrivate(CardCrawlGame.music, MusicMaster.class, "tempTrack");
        for(MainMusic m: mainTrack){
            if(!(boolean) ReflectionHacks.getPrivate(m, MainMusic.class, "isSilenced")){
                silencedMainTracks.add(m);
            }
            Music music = (Music)ReflectionHacks.getPrivate(m, MainMusic.class, "music");
            ReflectionHacks.setPrivate(m, MainMusic.class, "isSilenced", true);
            ReflectionHacks.setPrivate(m, MainMusic.class, "silenceTimer", silenceSpeed);
            ReflectionHacks.setPrivate(m, MainMusic.class, "silenceTime", silenceSpeed);
            ReflectionHacks.setPrivate(m, MainMusic.class, "silenceStartVolume", music.getVolume());
        }
        for(TempMusic m: tempTrack){
            if(!(boolean) ReflectionHacks.getPrivate(m, TempMusic.class, "isSilenced")){
                silencedTempTracks.add(m);
            }
            Music music = (Music)ReflectionHacks.getPrivate(m, TempMusic.class, "music");
            ReflectionHacks.setPrivate(m, TempMusic.class, "isSilenced", true);
            ReflectionHacks.setPrivate(m, TempMusic.class, "silenceTimer", silenceSpeed);
            ReflectionHacks.setPrivate(m, TempMusic.class, "silenceTime", silenceSpeed);
            ReflectionHacks.setPrivate(m, TempMusic.class, "silenceStartVolume", music.getVolume());
        }
    }

    @SuppressWarnings("unchecked")
    public static void unsilenceSilencedMusic(){
        ArrayList<MainMusic> mainTrack = (ArrayList<MainMusic>)ReflectionHacks.getPrivate(CardCrawlGame.music, MusicMaster.class, "mainTrack");
        ArrayList<TempMusic> tempTrack = (ArrayList<TempMusic>)ReflectionHacks.getPrivate(CardCrawlGame.music, MusicMaster.class, "tempTrack");
        for(MainMusic m: mainTrack){
            if(silencedMainTracks.contains(m)) {
                m.unsilence();
            }
        }
        for(TempMusic m: tempTrack){
            if(silencedTempTracks.contains(m)) {
                ReflectionHacks.setPrivate(m, TempMusic.class, "isSilenced", false);
                ReflectionHacks.setPrivate(m, TempMusic.class, "fadeTimer", 4.0f);
                ReflectionHacks.setPrivate(m, TempMusic.class, "fadeTime", 4.0f);
            }
        }
    }
}
