package mari_mod.events;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mari_mod.effects.MariOdoreEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariTheTemple extends BetterAbstractImageEvent {
    public static final Logger logger = LogManager.getLogger(MariTheTemple.class.getName());
    public static final String ID = "MariMod:MariTheTemple";
    public Screen screen;
    public MariOdoreEffect lastingEffect;

    public MariTheTemple() {
        super(ID, "mari_mod/images/events/MariTheTemple.png");
        CardCrawlGame.music.precacheTempBgm("MariMod:MariTheSoundOfRain");
        this.imageEventText.setDialogOption(searchOptions("do"));
        this.imageEventText.setDialogOption(searchOptions("do not"));
        this.screen = Screen.INTRO;
        this.lastingEffect = new MariOdoreEffect();
        AbstractDungeon.topLevelEffects.add(this.lastingEffect);
    }

    protected void buttonEffect(int buttonPressed) {
        switch(this.screen) {
            case INTRO:
                this.imageEventText.clearAllDialogs();
                switch(buttonPressed) {
                    case 0:
                        this.imageEventText.updateBodyText(searchDescriptions("do"));
                        this.imageEventText.setDialogOption(searchOptions("listen"));

                        CardCrawlGame.music.playTempBGM("MariMod:MariTheSoundOfRain");
                        this.lastingEffect.musicTimer = 0f;

                        this.screen = Screen.DO;
                        break;
                    case 1:
                        this.imageEventText.updateBodyText(searchDescriptions("do not"));
                        this.imageEventText.setDialogOption(searchOptions("leave"));
                        this.screen = Screen.DO_NOT;
                        break;
                    default:
                        break;
                }
                break;
            case DO:
                this.imageEventText.clearAllDialogs();
                this.imageEventText.updateBodyText(searchDescriptions("listen"));
                this.imageEventText.setDialogOption(searchOptions("leave"));
                this.screen = Screen.DO_RESULT;

                break;
            default:
                this.lastingEffect.stopEffect();
                this.openMap();
            }
    }

    private static enum Screen {
        INTRO,
        DO,
        DO_RESULT,
        DO_NOT;
        private Screen() {
        }
    }
}
