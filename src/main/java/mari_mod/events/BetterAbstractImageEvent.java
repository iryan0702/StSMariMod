package mari_mod.events;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BetterAbstractImageEvent extends AbstractImageEvent {
    public static final Logger logger = LogManager.getLogger(BetterAbstractImageEvent.class.getName());

    private static EventStrings eventStrings;
    public static String name;
    public static String[] descriptions;
    public static String[] options;

    public BetterAbstractImageEvent(String eventID, String imgPath) {
        super("1","1","1");
        this.imageEventText.clear();
        this.roomEventText.clear();

        initializeStrings(eventID);

        this.title = name;
        this.body = searchDescriptions("intro");
        this.imageEventText.loadImage(imgPath);
        type = EventType.IMAGE;
        this.noCardsInRewards = false;
    }

    @Override
    protected void buttonEffect(int i) {
        logger.info("w-wha...what is happening? WHERE AM I!? THIS ISN'T SUPPOSED TO HAPPEN?");
    }

    public String searchDescriptions(String stringID){
        for(String s: descriptions){
            if(s.startsWith(stringID + ":")){
                return s.substring(stringID.length() + 1);
            }
        }
        return "error: no descriptions found";
    }

    public String searchOptions(String stringID){
        for(String s: options){
            if(s.startsWith(stringID + ":")){
                return s.substring(stringID.length() + 1);
            }
        }
        return "error: no options found";
    }

    public String colorText(String text, String colorKey){
        text = colorKey + text;
        text.replace(" "," " + colorKey);
        return text;
    }

    public void initializeStrings(String eventID){
        eventStrings = CardCrawlGame.languagePack.getEventString(eventID);
        name = eventStrings.NAME;
        descriptions = eventStrings.DESCRIPTIONS;
        options = eventStrings.OPTIONS;
    }
}
