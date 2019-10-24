//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Doubt;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import mari_mod.relics.MariTheSpark;

public class MariSssserpent extends AbstractImageEvent {
    public static final String ID = "MariMod:Liars Game";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final String DIALOG_1;
    private static final String AGREE_DIALOG;
    private static final String DISAGREE_DIALOG;
    private static final String GOLD_RAIN_MSG;
    private MariSssserpent.CUR_SCREEN screen;

    private static final EventStrings additionalEventStrings;
    public static final String[] ADDITIONAL_DESCRIPTIONS;
    public static final String[] ADDITIONAL_OPTIONS;

    private static final int GOLD_REWARD = 175;
    private static final int A_2_GOLD_REWARD = 150;
    private int goldReward;
    private AbstractCard curse;

    private boolean hasTheSpark;
    private AbstractRelic theSpark;

    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON) {
            CardCrawlGame.sound.play("EVENT_SERPENT");
        }

    }

    public MariSssserpent() {
        super(NAME, DIALOG_1, "images/events/liarsGame.jpg");
        this.screen = MariSssserpent.CUR_SCREEN.INTRO;
        if (AbstractDungeon.ascensionLevel >= 15) {
            this.goldReward = 150;
        } else {
            this.goldReward = 175;
        }

        hasTheSpark = AbstractDungeon.player.hasRelic(MariTheSpark.ID);

        this.curse = new Doubt();
        if(hasTheSpark) {
            this.imageEventText.setDialogOption(ADDITIONAL_OPTIONS[0]);
            theSpark = AbstractDungeon.player.getRelic(MariTheSpark.ID);
        }
        this.imageEventText.setDialogOption(OPTIONS[0] + this.goldReward + OPTIONS[1], CardLibrary.getCopy(this.curse.cardID));
        this.imageEventText.setDialogOption(OPTIONS[2]);
    }

    protected void buttonEffect(int buttonPressed) {
        switch(this.screen) {
        case INTRO:
            this.imageEventText.clearAllDialogs();
            if (buttonPressed == 0 && hasTheSpark) {
                this.imageEventText.updateBodyText(ADDITIONAL_DESCRIPTIONS[0]);
                this.imageEventText.setDialogOption(ADDITIONAL_OPTIONS[1]);
                this.screen = CUR_SCREEN.CONFUSE;
            }else if ((buttonPressed == 1 && hasTheSpark) || buttonPressed == 0) {
                this.imageEventText.updateBodyText(AGREE_DIALOG);
                this.imageEventText.setDialogOption(OPTIONS[3]);
                this.screen = CUR_SCREEN.AGREE;
                AbstractEvent.logMetricGainGoldAndCard("Liars Game", "AGREE", this.curse, this.goldReward);
            } else {
                this.imageEventText.updateBodyText(DISAGREE_DIALOG);
                this.imageEventText.setDialogOption(OPTIONS[4]);
                this.screen = CUR_SCREEN.DISAGREE;
                AbstractEvent.logMetricIgnored("Liars Game");
            }
            break;
        case AGREE:
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(this.curse, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            AbstractDungeon.effectList.add(new RainingGoldEffect(this.goldReward));
            AbstractDungeon.player.gainGold(this.goldReward);
            this.imageEventText.updateBodyText(GOLD_RAIN_MSG);
            this.imageEventText.updateDialogOption(0, OPTIONS[4]);
            this.screen = MariSssserpent.CUR_SCREEN.COMPLETE;
            break;
        case CONFUSE:
            AbstractDungeon.player.loseGold(20);
            theSpark.flash();
            theSpark.counter += 80;
            this.imageEventText.updateBodyText(ADDITIONAL_DESCRIPTIONS[1]);
            this.imageEventText.updateDialogOption(0, OPTIONS[4]);
            this.screen = MariSssserpent.CUR_SCREEN.COMPLETE;
            break;
        default:
            this.openMap();
        }

    }

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Liars Game");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        AGREE_DIALOG = DESCRIPTIONS[1];
        DISAGREE_DIALOG = DESCRIPTIONS[2];
        GOLD_RAIN_MSG = DESCRIPTIONS[3];


        additionalEventStrings = CardCrawlGame.languagePack.getEventString("MariMod:Liars Game");

        ADDITIONAL_DESCRIPTIONS = additionalEventStrings.DESCRIPTIONS;
        ADDITIONAL_OPTIONS = additionalEventStrings.OPTIONS;
    }

    private static enum CUR_SCREEN {
        INTRO,
        AGREE,
        DISAGREE,
        CONFUSE,
        COMPLETE;

        private CUR_SCREEN() {
        }
    }
}
