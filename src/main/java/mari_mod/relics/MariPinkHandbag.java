package mari_mod.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import mari_mod.MariMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class MariPinkHandbag extends AbstractMariRelic
{
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public static final String ID = "MariMod:MariPinkHandbag";
    public MariPinkHandbag()
    {
        super(ID, RelicTier.STARTER, LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onPlayerEndTurn() {
        ArrayList<AbstractCard> cardList = new ArrayList<>();
        int highestCost = -2;
        for(AbstractCard c: AbstractDungeon.player.hand.group){
            int cardCost = c.cost == -1 ? EnergyPanel.totalCount : c.costForTurn;
            if(cardCost > highestCost){
                cardList.clear();
                highestCost = cardCost;
            }
            if(cardCost == highestCost && highestCost >= 0){
                cardList.add(c);
            }
        }
        if(cardList.size() > 0){
            AbstractCard c = cardList.get(AbstractDungeon.relicRng.random(0,cardList.size()-1));
            c.flash();
            if(!c.isEthereal) {
                c.retain = true;
            }
        }
    }

    public AbstractRelic makeCopy()
    {
        return new MariPinkHandbag();
    }
}
