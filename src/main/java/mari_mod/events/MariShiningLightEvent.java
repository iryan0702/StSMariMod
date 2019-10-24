//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.events;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Doubt;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import mari_mod.relics.MariJarOfLight;

import java.util.*;

public class MariShiningLightEvent extends AbstractImageEvent {
    public static final String ID = "MariMod:Shining Light";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final String INTRO;
    private static final String AGREE_DIALOG;
    private static final String DISAGREE_DIALOG;

    private static final EventStrings additionalEventStrings;
    public static final String[] ADDITIONAL_DESCRIPTIONS;
    public static final String[] ADDITIONAL_OPTIONS;

    private int damage = 0;
    private static final float HP_LOSS_PERCENT = 0.2F;
    private static final float A_2_HP_LOSS_PERCENT = 0.3F;
    private MariShiningLightEvent.CUR_SCREEN screen;

    public MariShiningLightEvent() {
        super(NAME, INTRO, "images/events/shiningLight.jpg");
        this.screen = MariShiningLightEvent.CUR_SCREEN.INTRO;
        if (AbstractDungeon.ascensionLevel >= 15) {
            this.damage = MathUtils.round((float)AbstractDungeon.player.maxHealth * 0.3F);
        } else {
            this.damage = MathUtils.round((float)AbstractDungeon.player.maxHealth * 0.2F);
        }


        this.imageEventText.setDialogOption(ADDITIONAL_OPTIONS[0], CardLibrary.getCard(Doubt.ID));

        if (AbstractDungeon.player.masterDeck.hasUpgradableCards()) {
            this.imageEventText.setDialogOption(OPTIONS[0] + this.damage + OPTIONS[1]);
        } else {
            this.imageEventText.setDialogOption(OPTIONS[3], true);
        }

        this.imageEventText.setDialogOption(OPTIONS[2]);
    }

    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON) {
            CardCrawlGame.sound.play("EVENT_SHINING");
        }
    }

    protected void buttonEffect(int buttonPressed) {
        switch(this.screen) {
        case INTRO:
            this.imageEventText.clearAllDialogs();
            if (buttonPressed == 0) {
                this.imageEventText.updateBodyText(ADDITIONAL_DESCRIPTIONS[0]);
                this.imageEventText.setDialogOption( OPTIONS[2]);
                this.screen = MariShiningLightEvent.CUR_SCREEN.COMPLETE;
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), new MariJarOfLight());
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Doubt(), (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));

            }else if (buttonPressed == 1) {
                this.imageEventText.updateBodyText(AGREE_DIALOG);
                this.imageEventText.setDialogOption(OPTIONS[2]);
                this.screen = MariShiningLightEvent.CUR_SCREEN.COMPLETE;
                AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player, this.damage));
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, AttackEffect.FIRE));
                this.upgradeCards();
            } else {
                this.imageEventText.updateBodyText(DISAGREE_DIALOG);
                this.imageEventText.setDialogOption(OPTIONS[2]);
                this.screen = MariShiningLightEvent.CUR_SCREEN.COMPLETE;
                AbstractEvent.logMetricIgnored("Shining Light");
            }
            break;
        default:
            this.openMap();
        }

    }

    private void upgradeCards() {
        AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        ArrayList<AbstractCard> upgradableCards = new ArrayList();
        Iterator var2 = AbstractDungeon.player.masterDeck.group.iterator();

        while(var2.hasNext()) {
            AbstractCard c = (AbstractCard)var2.next();
            if (c.canUpgrade()) {
                upgradableCards.add(c);
            }
        }

        List<String> cardMetrics = new ArrayList();
        Collections.shuffle(upgradableCards, new Random(AbstractDungeon.miscRng.randomLong()));
        if (!upgradableCards.isEmpty()) {
            if (upgradableCards.size() == 1) {
                ((AbstractCard)upgradableCards.get(0)).upgrade();
                cardMetrics.add(((AbstractCard)upgradableCards.get(0)).cardID);
                AbstractDungeon.player.bottledCardUpgradeCheck((AbstractCard)upgradableCards.get(0));
                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(((AbstractCard)upgradableCards.get(0)).makeStatEquivalentCopy()));
            } else {
                ((AbstractCard)upgradableCards.get(0)).upgrade();
                ((AbstractCard)upgradableCards.get(1)).upgrade();
                cardMetrics.add(((AbstractCard)upgradableCards.get(0)).cardID);
                cardMetrics.add(((AbstractCard)upgradableCards.get(1)).cardID);
                AbstractDungeon.player.bottledCardUpgradeCheck((AbstractCard)upgradableCards.get(0));
                AbstractDungeon.player.bottledCardUpgradeCheck((AbstractCard)upgradableCards.get(1));
                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(((AbstractCard)upgradableCards.get(0)).makeStatEquivalentCopy(), (float)Settings.WIDTH / 2.0F - 190.0F * Settings.scale, (float)Settings.HEIGHT / 2.0F));
                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(((AbstractCard)upgradableCards.get(1)).makeStatEquivalentCopy(), (float)Settings.WIDTH / 2.0F + 190.0F * Settings.scale, (float)Settings.HEIGHT / 2.0F));
            }
        }

        AbstractEvent.logMetric("Shining Light", "Entered Light", (List)null, (List)null, (List)null, cardMetrics, (List)null, (List)null, (List)null, this.damage, 0, 0, 0, 0, 0);
    }

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Shining Light");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        INTRO = DESCRIPTIONS[0];
        AGREE_DIALOG = DESCRIPTIONS[1];
        DISAGREE_DIALOG = DESCRIPTIONS[2];

        additionalEventStrings = CardCrawlGame.languagePack.getEventString("MariMod:Shining Light");
        ADDITIONAL_DESCRIPTIONS = additionalEventStrings.DESCRIPTIONS;
        ADDITIONAL_OPTIONS = additionalEventStrings.OPTIONS;
    }

    private static enum CUR_SCREEN {
        INTRO,
        COMPLETE;

        private CUR_SCREEN() {
        }
    }
}
