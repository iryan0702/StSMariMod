//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Injury;
import com.megacrit.cardcrawl.cards.curses.Writhe;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.GoldenIdol;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import mari_mod.relics.MariShiningIdol;

public class MariGoldenIdolEvent extends AbstractImageEvent {
    public static final String ID = "MariMod:Golden Idol";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final String DIALOG_START;
    private static final String DIALOG_BOULDER;
    private static final String DIALOG_CHOSE_RUN;
    private static final String DIALOG_CHOSE_FIGHT;
    private static final String DIALOG_CHOSE_FLAT;
    private static final String DIALOG_IGNORE;

    private static final EventStrings additionalEventStrings;
    public static final String[] ADDITIONAL_DESCRIPTIONS;
    public static final String[] ADDITIONAL_OPTIONS;
    private int screenNum = 0;
    private static final float HP_LOSS_PERCENT = 0.25F;
    private static final float MAX_HP_LOSS_PERCENT = 0.08F;
    private static final float A_2_HP_LOSS_PERCENT = 0.35F;
    private static final float A_2_MAX_HP_LOSS_PERCENT = 0.1F;

    private static final float WORSE_HP_LOSS_PERCENT = 0.50F;
    private static final float WORSE_MAX_HP_LOSS_PERCENT = 0.12F;
    private static final float WORSE_A_2_HP_LOSS_PERCENT = 0.60F;
    private static final float WORSE_A_2_MAX_HP_LOSS_PERCENT = 0.15F;
    private int damage;
    private int maxHpLoss;
    private int worseDamage;
    private int worseMaxHpLoss;
    private AbstractRelic relicMetric = null;

    public MariGoldenIdolEvent() {
        super(NAME, DIALOG_START, "images/events/goldenIdol.jpg");
        this.imageEventText.setDialogOption(OPTIONS[0], new GoldenIdol());
        this.imageEventText.setDialogOption(OPTIONS[1]);
        if (AbstractDungeon.ascensionLevel >= 15) {
            this.damage = (int)((float)AbstractDungeon.player.maxHealth * A_2_HP_LOSS_PERCENT);
            this.maxHpLoss = (int)((float)AbstractDungeon.player.maxHealth * A_2_MAX_HP_LOSS_PERCENT);
        } else {
            this.damage = (int)((float)AbstractDungeon.player.maxHealth * HP_LOSS_PERCENT);
            this.maxHpLoss = (int)((float)AbstractDungeon.player.maxHealth * MAX_HP_LOSS_PERCENT);
        }


        if (AbstractDungeon.ascensionLevel >= 15) {
            this.worseDamage = (int)((float)AbstractDungeon.player.maxHealth * WORSE_A_2_HP_LOSS_PERCENT);
            this.worseMaxHpLoss = (int)((float)AbstractDungeon.player.maxHealth * WORSE_A_2_MAX_HP_LOSS_PERCENT);
        } else {
            this.worseDamage = (int)((float)AbstractDungeon.player.maxHealth * WORSE_HP_LOSS_PERCENT);
            this.worseMaxHpLoss = (int)((float)AbstractDungeon.player.maxHealth * WORSE_MAX_HP_LOSS_PERCENT);
        }

        if (this.maxHpLoss < 1) {
            this.maxHpLoss = 1;
        }

        if (this.worseMaxHpLoss < 2) {
            this.worseMaxHpLoss = 2;
        }
    }

    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON) {
            CardCrawlGame.sound.play("EVENT_GOLDEN");
        }

    }

    protected void buttonEffect(int buttonPressed) {
        switch(this.screenNum) {
        case 0:
            switch(buttonPressed) {
            case 0:
                this.imageEventText.updateBodyText(ADDITIONAL_DESCRIPTIONS[0]);
                if (AbstractDungeon.player.hasRelic("Golden Idol")) {
                    this.relicMetric = RelicLibrary.getRelic("Circlet").makeCopy();
                } else {
                    this.relicMetric = RelicLibrary.getRelic("Golden Idol").makeCopy();
                }

                CardCrawlGame.screenShake.mildRumble(5.0F);
                CardCrawlGame.sound.play("BLUNT_HEAVY");
                this.screenNum = 1;
                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(ADDITIONAL_OPTIONS[0], new MariShiningIdol());
                this.imageEventText.setDialogOption(OPTIONS[2], CardLibrary.getCopy("Injury"));
                this.imageEventText.setDialogOption(OPTIONS[3] + this.damage + OPTIONS[4]);
                this.imageEventText.setDialogOption(OPTIONS[5] + this.maxHpLoss + OPTIONS[6]);
                return;
            default:
                this.imageEventText.updateBodyText(DIALOG_IGNORE);
                this.screenNum = 2;
                this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                this.imageEventText.clearRemainingOptions();
                AbstractEvent.logMetricIgnored("Golden Idol");
                return;
            }
        case 1:
            switch(buttonPressed) {
            case 0:
                CardCrawlGame.screenShake.rumble(5.0F);
                this.imageEventText.updateBodyText(ADDITIONAL_DESCRIPTIONS[1]);
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), new MariShiningIdol());
                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(ADDITIONAL_OPTIONS[1], CardLibrary.getCopy(Writhe.ID));
                this.imageEventText.setDialogOption(OPTIONS[3] + this.worseDamage + OPTIONS[4]);
                this.imageEventText.setDialogOption(OPTIONS[5] + this.worseMaxHpLoss + OPTIONS[6]);
                this.screenNum = 2;
                return;
            case 1:
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), this.relicMetric);
                CardCrawlGame.screenShake.shake(ShakeIntensity.MED, ShakeDur.MED, false);
                this.imageEventText.updateBodyText(DIALOG_CHOSE_RUN);
                AbstractCard curse = new Injury();
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(curse, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                this.screenNum = 3;
                this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                this.imageEventText.clearRemainingOptions();
                return;
            case 2:
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), this.relicMetric);
                this.imageEventText.updateBodyText(DIALOG_CHOSE_FIGHT);
                CardCrawlGame.screenShake.shake(ShakeIntensity.MED, ShakeDur.MED, false);
                CardCrawlGame.sound.play("BLUNT_FAST");
                AbstractDungeon.player.damage(new DamageInfo((AbstractCreature)null, this.damage));
                this.screenNum = 3;
                this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                this.imageEventText.clearRemainingOptions();
                return;
            case 3:
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), this.relicMetric);
                this.imageEventText.updateBodyText(DIALOG_CHOSE_FLAT);
                AbstractDungeon.player.decreaseMaxHealth(this.maxHpLoss);
                CardCrawlGame.screenShake.shake(ShakeIntensity.MED, ShakeDur.MED, false);
                CardCrawlGame.sound.play("BLUNT_FAST");
                this.screenNum = 3;
                this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                this.imageEventText.clearRemainingOptions();
                return;
            default:
                this.openMap();
                return;
            }
        case 2:
            switch(buttonPressed) {
            case 0:
                CardCrawlGame.screenShake.shake(ShakeIntensity.HIGH, ShakeDur.MED, false);
                this.imageEventText.updateBodyText(DIALOG_CHOSE_RUN);
                AbstractCard curse = new Writhe();
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(curse, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                this.screenNum = 3;
                this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                this.imageEventText.clearRemainingOptions();
                return;
            case 1:
                this.imageEventText.updateBodyText(DIALOG_CHOSE_FIGHT);
                CardCrawlGame.screenShake.shake(ShakeIntensity.HIGH, ShakeDur.MED, false);
                CardCrawlGame.sound.play("BLUNT_FAST");
                AbstractDungeon.player.damage(new DamageInfo((AbstractCreature)null, this.worseDamage));
                this.screenNum = 3;
                this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                this.imageEventText.clearRemainingOptions();
                return;
            case 2:
                this.imageEventText.updateBodyText(DIALOG_CHOSE_FLAT);
                AbstractDungeon.player.decreaseMaxHealth(this.worseMaxHpLoss);
                CardCrawlGame.screenShake.shake(ShakeIntensity.HIGH, ShakeDur.MED, false);
                CardCrawlGame.sound.play("BLUNT_FAST");
                this.screenNum = 3;
                this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                this.imageEventText.clearRemainingOptions();
                return;
            default:
                this.openMap();
                return;
            }
        case 3:
            this.openMap();
            break;
        default:
            this.openMap();
        }

    }

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Golden Idol");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_START = DESCRIPTIONS[0];
        DIALOG_BOULDER = DESCRIPTIONS[1];
        DIALOG_CHOSE_RUN = DESCRIPTIONS[2];
        DIALOG_CHOSE_FIGHT = DESCRIPTIONS[3];
        DIALOG_CHOSE_FLAT = DESCRIPTIONS[4];
        DIALOG_IGNORE = DESCRIPTIONS[5];

        additionalEventStrings = CardCrawlGame.languagePack.getEventString("MariMod:Golden Idol");
        ADDITIONAL_DESCRIPTIONS = additionalEventStrings.DESCRIPTIONS;
        ADDITIONAL_OPTIONS = additionalEventStrings.OPTIONS;
    }
}
