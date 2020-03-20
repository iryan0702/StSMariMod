package mari_mod.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Regret;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import mari_mod.MariMod;
import mari_mod.MariSavables;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.currMapNode;

public class AllMariModEvents extends AbstractImageEvent {
    public static final Logger logger = LogManager.getLogger(AllMariModEvents.class.getName());
    public static final String ID = "Big Fish";
    private static final EventStrings eventStrings;
    private static final EventStrings tunnelOfStarsStrings;

    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final String DIALOG_1;
    private static final String BANANA_RESULT;
    private static final String DONUT_RESULT;
    private static final String BOX_RESULT;
    private static final String BOX_BAD;

    public static final String TUNNEL_OF_STARS_NAME;
    public static final String[] TUNNEL_OF_STARS_DESCRIPTIONS;
    private int healAmt = 0;
    private static final int MAX_HP_AMT = 5;
    private AllMariModEvents.CurScreen screen;
    private MariSavables save;

    public AllMariModEvents() {
        super(NAME, DIALOG_1, "mari_mod/images/events/MariTunnelOfStars.png");
        this.screen = AllMariModEvents.CurScreen.TUNNEL_OF_STARS_INTRO;
        this.healAmt = AbstractDungeon.player.maxHealth / 3;
        this.imageEventText.setDialogOption(OPTIONS[0]);
        this.imageEventText.setDialogOption(OPTIONS[2] + 5 + OPTIONS[3]);
        this.imageEventText.setDialogOption(OPTIONS[4], CardLibrary.getCopy("Regret"));
        this.save = MariMod.saveableKeeper;
    }

    public void eventSetup(String title, String body, String imgUrl){

        this.imageEventText.clear();
        this.roomEventText.clear();
        this.title = title;
        this.body = body;
        this.imageEventText.loadImage(imgUrl);
        type = EventType.IMAGE;
        this.noCardsInRewards = false;


        if(this.screen == CurScreen.TUNNEL_OF_STARS_INTRO){
            this.imageEventText.setDialogOption(TUNNEL_OF_STARS_DESCRIPTIONS[0]);
        }
    }

    protected void buttonEffect(int buttonPressed) {
        switch(this.screen) {
        case MYSTERIOUS_VOICE_INTRO:
            switch(buttonPressed) {
            case 0:
                for(MapEdge edge : currMapNode.getEdges()){
                    if(edge.srcX == currMapNode.x && edge.srcY == currMapNode.y && CardCrawlGame.dungeon.getMap().get(edge.dstY).get(edge.dstX).room.getMapSymbol().equals("M")){
                        save.hiredGunVisited = true;
                        save.hiredGunHired = true;
                        save.hiredGunX = edge.dstX;
                        save.hiredGunY = edge.dstY;
                        logger.info("found monster after node!");
                    }
                }
                this.imageEventText.updateBodyText(BANANA_RESULT);
                break;
            case 1:
                AbstractDungeon.player.increaseMaxHp(5, true);
                this.imageEventText.updateBodyText(DONUT_RESULT);
                break;
            default:
                this.imageEventText.updateBodyText(BOX_RESULT + BOX_BAD);
                AbstractCard c = new Regret();
                AbstractRelic r = AbstractDungeon.returnRandomScreenlessRelic(AbstractDungeon.returnRandomRelicTier());
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(CardLibrary.getCopy(c.cardID), (float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2)));
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), r);
            }

            this.imageEventText.clearAllDialogs();
            this.imageEventText.setDialogOption(OPTIONS[5]);
            this.screen = AllMariModEvents.CurScreen.MYSTERIOUS_VOICE_RESULT;
            break;

        case MYSTERIOUS_VOICE_RESULT:
            this.openMap();
            break;
        case TUNNEL_OF_STARS_INTRO:

        default:
            this.openMap();
        }

    }

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("MariMod:TunnelOfStars");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        BANANA_RESULT = DESCRIPTIONS[1];
        DONUT_RESULT = DESCRIPTIONS[2];
        BOX_RESULT = DESCRIPTIONS[4];
        BOX_BAD = DESCRIPTIONS[5];

        tunnelOfStarsStrings = CardCrawlGame.languagePack.getEventString("MariMod:TunnelOfStars");
        TUNNEL_OF_STARS_NAME = tunnelOfStarsStrings.NAME;
        TUNNEL_OF_STARS_DESCRIPTIONS = tunnelOfStarsStrings.DESCRIPTIONS;
    }

    private static enum CurScreen {
        MYSTERIOUS_VOICE_INTRO,
        MYSTERIOUS_VOICE_RESULT,
        TUNNEL_OF_STARS_INTRO,
        TUNNEL_OF_STARS_RESULT;

        private CurScreen() {
        }
    }

    private static enum CurEvent {
        FISH,
        RESULT;

        private CurEvent() {
        }
    }
}
