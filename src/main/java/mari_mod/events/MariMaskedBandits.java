//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.events;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.relics.RedMask;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;

public class MariMaskedBandits extends AbstractEvent {
    public static final String ID = "MariMod:Masked Bandits";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final String PAID_MSG_1;
    private static final String PAID_MSG_2;
    private static final String PAID_MSG_3;
    private static final String PAID_MSG_4;

    private static final EventStrings additionalEventStrings;
    public static final String[] ADDITIONAL_DESCRIPTIONS;
    public static final String[] ADDITIONAL_OPTIONS;
    private MariMaskedBandits.CurScreen screen;

    public static final int JOIN_GOLD_COST = 40;
    int halfGold = 0;
    AbstractRelic rewardRelic;
    AbstractMonster whereDidBearGo;

    public MariMaskedBandits() {
        this.screen = MariMaskedBandits.CurScreen.INTRO;
        this.body = DESCRIPTIONS[4];
        this.roomEventText.clear();
        if(AbstractDungeon.player.gold >= JOIN_GOLD_COST) {
            this.roomEventText.addDialogOption(ADDITIONAL_OPTIONS[0].replace("[JOIN]", "" + JOIN_GOLD_COST));
        }else{
            this.roomEventText.addDialogOption(ADDITIONAL_OPTIONS[1].replace("[JOIN]", "" + JOIN_GOLD_COST));
        }
        this.roomEventText.addDialogOption(OPTIONS[0]);
        this.roomEventText.addDialogOption(OPTIONS[1]);
        this.hasDialog = true;
        this.hasFocus = true;
        rewardRelic = new RedMask();
        AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("Masked Bandits");
    }

    public void update() {
        super.update();
        if (!RoomEventDialog.waitForInput) {
            this.buttonEffect(this.roomEventText.getSelectedOption());
        }

    }

    protected void buttonEffect(int buttonPressed) {
        switch(this.screen) {
        case INTRO:
            switch(buttonPressed) {
            case 0:
                this.stealGold(JOIN_GOLD_COST);
                AbstractDungeon.player.loseGold(JOIN_GOLD_COST);
                this.roomEventText.updateBodyText(ADDITIONAL_DESCRIPTIONS[0]);
                this.roomEventText.clearRemainingOptions();
                this.roomEventText.updateDialogOption(0,OPTIONS[2]);
                this.screen = CurScreen.JOIN_1;
                return;
            case 1:
                this.stealGold(AbstractDungeon.player.gold);
                AbstractDungeon.player.loseGold(AbstractDungeon.player.gold);
                this.roomEventText.updateBodyText(PAID_MSG_1);
                this.roomEventText.updateDialogOption(0, OPTIONS[2]);
                this.roomEventText.clearRemainingOptions();
                this.screen = MariMaskedBandits.CurScreen.PAID_1;
                return;
            case 2:
                logMetric("Masked Bandits", "Fought Bandits");
                if (Settings.isDailyRun) {
                    AbstractDungeon.getCurrRoom().addGoldToRewards(AbstractDungeon.miscRng.random(30));
                } else {
                    AbstractDungeon.getCurrRoom().addGoldToRewards(AbstractDungeon.miscRng.random(25, 35));
                }

                if (AbstractDungeon.player.hasRelic("Red Mask")) {
                    AbstractDungeon.getCurrRoom().addRelicToRewards(new Circlet());
                } else {
                    AbstractDungeon.getCurrRoom().addRelicToRewards(new RedMask());
                }

                this.enterCombat();
                AbstractDungeon.lastCombatMetricKey = "Masked Bandits";
                return;
            default:
                return;
            }
        case JOIN_1:
                halfGold = AbstractDungeon.player.gold/2;
                this.roomEventText.updateBodyText(ADDITIONAL_DESCRIPTIONS[1]);
                this.roomEventText.removeDialogOption(0);
                this.roomEventText.addDialogOption(ADDITIONAL_OPTIONS[2].replace("[BUY]", "" + halfGold), rewardRelic);
                this.roomEventText.addDialogOption(ADDITIONAL_OPTIONS[3]);
                whereDidBearGo = AbstractDungeon.getCurrRoom().monsters.monsters.remove(2);
                this.screen = CurScreen.JOIN_2;
            break;
        case JOIN_2:
            switch(buttonPressed) {
                case 0:
                    this.stealGold(halfGold);
                    AbstractDungeon.player.loseGold(halfGold);
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), rewardRelic);
                    this.roomEventText.updateBodyText(ADDITIONAL_DESCRIPTIONS[2]);
                    this.roomEventText.clearRemainingOptions();
                    this.roomEventText.updateDialogOption(0, OPTIONS[3]);
                    AbstractDungeon.getCurrRoom().monsters.monsters.add(whereDidBearGo);
                    this.screen = CurScreen.BUY;
                    return;
                case 1:
                    if (Settings.isDailyRun) {
                        AbstractDungeon.getCurrRoom().addGoldToRewards(AbstractDungeon.miscRng.random(30));
                    } else {
                        AbstractDungeon.getCurrRoom().addGoldToRewards(AbstractDungeon.miscRng.random(25, 35));
                    }

                    if (AbstractDungeon.player.hasRelic("Red Mask")) {
                        AbstractDungeon.getCurrRoom().addRelicToRewards(new Circlet());
                    } else {
                        AbstractDungeon.getCurrRoom().addRelicToRewards(new RedMask());
                    }
                    this.enterCombat();
            }
            break;
        case PAID_1:
            this.roomEventText.updateBodyText(PAID_MSG_2);
            this.screen = MariMaskedBandits.CurScreen.PAID_2;
            this.roomEventText.updateDialogOption(0, OPTIONS[2]);
            break;
        case PAID_2:
            this.roomEventText.updateBodyText(PAID_MSG_3);
            this.screen = MariMaskedBandits.CurScreen.PAID_3;
            this.roomEventText.updateDialogOption(0, OPTIONS[3]);
            break;
        case PAID_3:
            this.roomEventText.updateBodyText(PAID_MSG_4);
            this.roomEventText.updateDialogOption(0, OPTIONS[3]);
            this.screen = MariMaskedBandits.CurScreen.END;
            this.openMap();
            break;
        case BUY:
            this.roomEventText.updateBodyText(ADDITIONAL_DESCRIPTIONS[3]);
            this.roomEventText.updateDialogOption(0, OPTIONS[3]);
        case END:
            this.openMap();
        }

    }

    private void stealGold(int stealAmount) {
        AbstractCreature target = AbstractDungeon.player;
        if (target.gold != 0) {
            CardCrawlGame.sound.play("GOLD_JINGLE");

            for(int i = 0; i < stealAmount; ++i) {
                AbstractCreature source = AbstractDungeon.getCurrRoom().monsters.getRandomMonster();
                AbstractDungeon.effectList.add(new GainPennyEffect(source, target.hb.cX, target.hb.cY, source.hb.cX, source.hb.cY, false));
            }

        }
    }

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Masked Bandits");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        PAID_MSG_1 = DESCRIPTIONS[0];
        PAID_MSG_2 = DESCRIPTIONS[1];
        PAID_MSG_3 = DESCRIPTIONS[2];
        PAID_MSG_4 = DESCRIPTIONS[3];

        additionalEventStrings = CardCrawlGame.languagePack.getEventString("MariMod:Masked Bandits");
        ADDITIONAL_DESCRIPTIONS = additionalEventStrings.DESCRIPTIONS;
        ADDITIONAL_OPTIONS = additionalEventStrings.OPTIONS;
    }

    private static enum CurScreen {
        INTRO,
        PAID_1,
        PAID_2,
        PAID_3,
        JOIN_1,
        JOIN_2,
        BUY,
        END;

        private CurScreen() {
        }
    }
}
