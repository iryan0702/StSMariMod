package mari_mod.relics;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import mari_mod.MariMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class MariDolphinTrinket extends AbstractMariRelic
{
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public static final String ID = "MariMod:MariDolphinTrinket";
    public ArrayList<AbstractCard> currentDeck = new ArrayList<AbstractCard>();
    public MariDolphinTrinket()
    {
        super(ID, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public void onObtainCard(AbstractCard c) {
        if(c.rarity.equals(AbstractCard.CardRarity.UNCOMMON)){
            this.flash();
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, 8));
            } else {
                AbstractDungeon.player.heal(8);
            }
        }
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy()
    {
        return new MariDolphinTrinket();
    }
}
