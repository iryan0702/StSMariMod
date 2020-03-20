package mari_mod.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import mari_mod.MariMod;
import mari_mod.MariSavables;
import mari_mod.relics.MariTheSpark;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class MariSnackShackEvent extends AbstractImageEvent {
    public static final Logger logger = LogManager.getLogger(MariSnackShackEvent.class.getName());
    public static final String ID = "MariMod:SnackShack";
    private static final EventStrings eventStrings;

    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;

    private static final String SS_SELL_RELIC_OPTION;
    private static final String SS_SELL_CARD_OPTION;
    private static final String SS_SELL_BLOOD_OPTION;
    private static final String SS_GOLD_DETAIL_1;
    private static final String SS_GOLD_DETAIL_2;
    private static final String SS_LEAVE_OPTION;
    private static final String SS_LOCKED_RELIC_OPTION;
    private static final String SS_LOCKED_CARD_OPTION;
    private static final String SS_CARD_LEAVE_WITH_THANKS_OPTION;
    private static final String SS_CARD_LEAVE_OPTION;
    private static final String SS_TAKE_GOLD_AND_LEAVE_OPTION_1;
    private static final String SS_TAKE_GOLD_AND_LEAVE_OPTION_2;
    private static final String SS_SIGN_CARD_OPTION;
    private static final String SS_SIGN_CARD_AND_GAIN_GOLD_OPTION_1;
    private static final String SS_SIGN_CARD_AND_GAIN_GOLD_OPTION_2;
    private static final String SS_DONT_SIGN_CARD_OPTION;
    private static final String SS_REGULAR_LEAVE_OPTION;

    private static final String INTRODUCTION;
    private static final String SELL_RELIC;
    private static final String SELL_RELIC_WITH_THANKS;
    private static final String SELL_RELIC_WITH_THANKS_RESULT;
    private static final String SELL_CARD;
    private static final String SELL_CARD_BARGAIN_1;
    private static final String SELL_CARD_BARGAIN_2;
    private static final String SELL_CARD_SIGNED_WITH_GOLD_RESULT;
    private static final String SELL_CARD_SIGNED_RESULT;
    private static final String SELL_CARD_UNSIGNED_RESULT;
    private static final String SELL_BLOOD_RESULT;
    private static final String SELL_NOTHING_RESULT;

    private static final int RELIC_GOLD_GAIN = 110;
    private static final int CARD_RARE_GOLD_GAIN = 90;
    private static final int CARD_UNCOMMON_GOLD_GAIN = 75;
    private static final int CARD_EXTRA_GOLD_GAIN = 10;
    private static final int BLOOD_GOLD_GAIN = 75;

    private MariSnackShackEvent.CurScreen screen;
    private MariSavables save;
    private AbstractCard cardToSell;
    private ArrayList<AbstractCard> eligibleCards;

    private boolean hasSpark;
    private boolean hasGoodCard;
    private int cardGoldReward;

    public MariSnackShackEvent() {
        super(NAME, INTRODUCTION, "mari_mod/images/events/MariSnackShack.png");

        MariMod.saveableKeeper.snackShackVisited = true;

        this.screen = MariSnackShackEvent.CurScreen.SNACK_SHACK_INTRO;
        eligibleCards = new ArrayList<>();

        for(AbstractCard rareCardCheck: AbstractDungeon.player.masterDeck.group){
            if(rareCardCheck.rarity.equals(AbstractCard.CardRarity.RARE)) eligibleCards.add(rareCardCheck);
        }

        if(eligibleCards.size() > 0){
            cardToSell = eligibleCards.get(AbstractDungeon.miscRng.random(0,eligibleCards.size()-1));
        }else{
            for(AbstractCard rareCardCheck: AbstractDungeon.player.masterDeck.group){
                if(rareCardCheck.rarity.equals(AbstractCard.CardRarity.UNCOMMON)) eligibleCards.add(rareCardCheck);
            }

            if(eligibleCards.size() > 0){
                cardToSell = eligibleCards.get(AbstractDungeon.miscRng.random(0,eligibleCards.size()-1));
            }
        }

        hasGoodCard =cardToSell != null;
        hasSpark = AbstractDungeon.player.hasRelic(MariTheSpark.ID);


        if(hasSpark) {
            this.imageEventText.setDialogOption(SS_SELL_RELIC_OPTION + SS_GOLD_DETAIL_1 + RELIC_GOLD_GAIN + SS_GOLD_DETAIL_2);
        }else{
            this.imageEventText.setDialogOption(SS_LOCKED_RELIC_OPTION, true);
        }


        if(hasGoodCard && cardToSell.rarity.equals(AbstractCard.CardRarity.RARE)) {
            cardGoldReward = CARD_RARE_GOLD_GAIN;
            this.imageEventText.setDialogOption(SS_SELL_CARD_OPTION + cardToSell.name + SS_GOLD_DETAIL_1 + CARD_RARE_GOLD_GAIN + SS_GOLD_DETAIL_2, cardToSell.makeStatEquivalentCopy());
        }else if(hasGoodCard && cardToSell.rarity.equals(AbstractCard.CardRarity.UNCOMMON)) {
            cardGoldReward = CARD_UNCOMMON_GOLD_GAIN;
            this.imageEventText.setDialogOption(SS_SELL_CARD_OPTION + cardToSell.name + SS_GOLD_DETAIL_1 + CARD_UNCOMMON_GOLD_GAIN + SS_GOLD_DETAIL_2, cardToSell.makeStatEquivalentCopy());
        }else{
            this.imageEventText.setDialogOption(SS_LOCKED_CARD_OPTION, true);
        }


        this.imageEventText.setDialogOption(SS_SELL_BLOOD_OPTION + SS_GOLD_DETAIL_1 + BLOOD_GOLD_GAIN + SS_GOLD_DETAIL_2);


        this.imageEventText.setDialogOption(SS_LEAVE_OPTION);
        this.save = MariMod.saveableKeeper;
    }

    protected void buttonEffect(int buttonPressed) {
        switch(this.screen) {

            case SNACK_SHACK_INTRO:

                this.imageEventText.clearAllDialogs();

                switch(buttonPressed) {
                    case 0:
                        this.imageEventText.updateBodyText(SELL_RELIC);
                        AbstractDungeon.player.loseRelic(MariTheSpark.ID);
                        this.screen = MariSnackShackEvent.CurScreen.SNACK_SHACK_SELL_RELIC;
                        this.imageEventText.setDialogOption(SS_CARD_LEAVE_WITH_THANKS_OPTION);
                        this.imageEventText.setDialogOption(SS_CARD_LEAVE_OPTION);
                        break;
                    case 1:
                        this.imageEventText.updateBodyText(SELL_CARD);
                        this.screen = CurScreen.SNACK_SHACK_SELL_CARD;

                        AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(this.cardToSell, (float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2)));
                        AbstractDungeon.player.masterDeck.removeCard(this.cardToSell);
                        AbstractDungeon.effectList.add(new RainingGoldEffect(this.cardGoldReward));
                        AbstractDungeon.player.gainGold(this.cardGoldReward);

                        this.imageEventText.setDialogOption(SS_SIGN_CARD_OPTION);
                        this.imageEventText.setDialogOption(SS_DONT_SIGN_CARD_OPTION);
                        break;
                    case 2:
                        this.imageEventText.updateBodyText(SELL_BLOOD_RESULT);
                        AbstractDungeon.effectList.add(new RainingGoldEffect(BLOOD_GOLD_GAIN));
                        AbstractDungeon.player.gainGold(BLOOD_GOLD_GAIN);
                        AbstractDungeon.player.damage(new DamageInfo((AbstractCreature)null, 8));
                        this.screen = CurScreen.SNACK_SHACK_SELL_BLOOD_RESULT;
                        this.imageEventText.setDialogOption(SS_REGULAR_LEAVE_OPTION);
                        break;
                    default:
                        this.imageEventText.updateBodyText(SELL_NOTHING_RESULT);
                        this.screen = CurScreen.SNACK_SHACK_SELL_NOTHING_RESULT;
                        this.imageEventText.setDialogOption(SS_REGULAR_LEAVE_OPTION);
                        break;
                }
            break;

                /////

            case SNACK_SHACK_SELL_RELIC:
                switch(buttonPressed) {
                    case 0:
                        this.imageEventText.updateBodyText(SELL_RELIC_WITH_THANKS);
                        this.screen = CurScreen.SNACK_SHACK_SELL_RELIC_WITH_THANKS;
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(SS_TAKE_GOLD_AND_LEAVE_OPTION_1 + RELIC_GOLD_GAIN + SS_TAKE_GOLD_AND_LEAVE_OPTION_2);
                        break;
                    default:
                        this.openMap();
                        break;
                }
            break;

                /////

            case SNACK_SHACK_SELL_RELIC_WITH_THANKS:
                this.imageEventText.updateBodyText(SELL_RELIC_WITH_THANKS_RESULT);
                AbstractDungeon.effectList.add(new RainingGoldEffect(RELIC_GOLD_GAIN));
                AbstractDungeon.player.gainGold(RELIC_GOLD_GAIN);
                this.screen = CurScreen.SNACK_SHACK_SELL_RELIC_WITH_THANKS_RESULT;
                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(SS_REGULAR_LEAVE_OPTION);
            break;

                //////

            case SNACK_SHACK_SELL_CARD:
                this.imageEventText.clearAllDialogs();
                switch(buttonPressed){
                    case 0:
                        this.imageEventText.updateBodyText(SELL_CARD_SIGNED_RESULT);
                        this.screen = CurScreen.SNACK_SHACK_SELL_CARD_SIGNED_RESULT;
                        this.imageEventText.setDialogOption(SS_REGULAR_LEAVE_OPTION);
                        break;
                    default:
                        this.imageEventText.updateBodyText(SELL_CARD_BARGAIN_1);
                        this.screen = CurScreen.SNACK_SHACK_SELL_CARD_BARGAIN_1;
                        this.imageEventText.setDialogOption(SS_SIGN_CARD_OPTION);
                        this.imageEventText.setDialogOption(SS_DONT_SIGN_CARD_OPTION);
                }
            break;

                //////

            case SNACK_SHACK_SELL_CARD_BARGAIN_1:
                this.imageEventText.clearAllDialogs();
                switch(buttonPressed){
                    case 0:
                        this.imageEventText.updateBodyText(SELL_CARD_SIGNED_RESULT);
                        this.screen = CurScreen.SNACK_SHACK_SELL_CARD_SIGNED_RESULT;
                        this.imageEventText.setDialogOption(SS_REGULAR_LEAVE_OPTION);
                        break;
                    default:
                        this.imageEventText.updateBodyText(SELL_CARD_BARGAIN_2);
                        this.screen = CurScreen.SNACK_SHACK_SELL_CARD_BARGAIN_2;
                        this.imageEventText.setDialogOption(SS_SIGN_CARD_AND_GAIN_GOLD_OPTION_1 + CARD_EXTRA_GOLD_GAIN + SS_SIGN_CARD_AND_GAIN_GOLD_OPTION_2);
                        this.imageEventText.setDialogOption(SS_DONT_SIGN_CARD_OPTION);
                }
            break;

                /////

            case SNACK_SHACK_SELL_CARD_BARGAIN_2:
                this.imageEventText.clearAllDialogs();
                switch(buttonPressed){
                    case 0:
                        AbstractDungeon.effectList.add(new RainingGoldEffect(CARD_EXTRA_GOLD_GAIN));
                        AbstractDungeon.player.gainGold(CARD_EXTRA_GOLD_GAIN);
                        this.imageEventText.updateBodyText(SELL_CARD_SIGNED_WITH_GOLD_RESULT);
                        this.screen = CurScreen.SNACK_SHACK_SELL_CARD_SIGNED_RESULT;
                        this.imageEventText.setDialogOption(SS_REGULAR_LEAVE_OPTION);
                        break;
                    default:
                        this.imageEventText.updateBodyText(SELL_CARD_UNSIGNED_RESULT);
                        this.screen = CurScreen.SNACK_SHACK_SELL_CARD_UNSIGNED_RESULT;
                        this.imageEventText.setDialogOption(SS_REGULAR_LEAVE_OPTION);
                }
            break;

                /////

            default:
                this.openMap();
            }

    }

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("MariMod:SnackShack");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;

        SS_SELL_RELIC_OPTION = OPTIONS[0];
        SS_SELL_CARD_OPTION = OPTIONS[1];
        SS_SELL_BLOOD_OPTION = OPTIONS[2];
        SS_GOLD_DETAIL_1 = OPTIONS[3];
        SS_GOLD_DETAIL_2 = OPTIONS[4];
        SS_LEAVE_OPTION = OPTIONS[5];
        SS_LOCKED_RELIC_OPTION = OPTIONS[6];
        SS_LOCKED_CARD_OPTION = OPTIONS[7];
        SS_CARD_LEAVE_WITH_THANKS_OPTION = OPTIONS[8];
        SS_CARD_LEAVE_OPTION = OPTIONS[9];
        SS_TAKE_GOLD_AND_LEAVE_OPTION_1 = OPTIONS[10];
        SS_TAKE_GOLD_AND_LEAVE_OPTION_2 = OPTIONS[11];
        SS_SIGN_CARD_OPTION = OPTIONS[12];
        SS_SIGN_CARD_AND_GAIN_GOLD_OPTION_1 = OPTIONS[13];
        SS_SIGN_CARD_AND_GAIN_GOLD_OPTION_2 = OPTIONS[14];
        SS_DONT_SIGN_CARD_OPTION = OPTIONS[15];
        SS_REGULAR_LEAVE_OPTION = OPTIONS[16];

        INTRODUCTION = DESCRIPTIONS[0];
        SELL_RELIC = DESCRIPTIONS[1];
        SELL_RELIC_WITH_THANKS = DESCRIPTIONS[2];
        SELL_RELIC_WITH_THANKS_RESULT = DESCRIPTIONS[3];
        SELL_CARD = DESCRIPTIONS[4];
        SELL_CARD_BARGAIN_1 = DESCRIPTIONS[5];
        SELL_CARD_BARGAIN_2 = DESCRIPTIONS[6];
        SELL_CARD_SIGNED_RESULT = DESCRIPTIONS[7];
        SELL_CARD_SIGNED_WITH_GOLD_RESULT = DESCRIPTIONS[8];
        SELL_CARD_UNSIGNED_RESULT = DESCRIPTIONS[9];
        SELL_BLOOD_RESULT = DESCRIPTIONS[10];
        SELL_NOTHING_RESULT = DESCRIPTIONS[11];

    }

    private static enum CurScreen {
        SNACK_SHACK_INTRO,
        SNACK_SHACK_SELL_RELIC,
        SNACK_SHACK_SELL_RELIC_WITH_THANKS,
        SNACK_SHACK_SELL_RELIC_WITH_THANKS_RESULT,
        SNACK_SHACK_SELL_CARD,
        SNACK_SHACK_SELL_CARD_BARGAIN_1,
        SNACK_SHACK_SELL_CARD_BARGAIN_2,
        SNACK_SHACK_SELL_CARD_SIGNED_RESULT,
        SNACK_SHACK_SELL_CARD_UNSIGNED_RESULT,
        SNACK_SHACK_SELL_BLOOD_RESULT,
        SNACK_SHACK_SELL_NOTHING_RESULT;

        private CurScreen() {
        }
    }

}
