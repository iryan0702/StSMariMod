package mari_mod.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardSave;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import mari_mod.MariMod;
import mari_mod.MariSavables;
import mari_mod.cards.AbstractMariCard;
import mari_mod.cards.Mari_Stewshine;
import mari_mod.cards.Mari_Undying_Spark;
import mari_mod.patches.CardColorEnum;
import mari_mod.patches.PlayerClassEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;

public class MariStewshine extends AbstractMariRelic
{
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public static final String ID = "MariMod:MariStewshine";
    private boolean cardSelected = true;
    public MariStewshine()
    {
        super(ID, RelicTier.BOSS, LandingSound.MAGICAL);
    }
    private int cardSelectSize;
    private int selectNeeded = 999;

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    public void onEquip() {
        if(AbstractDungeon.player.chosenClass != PlayerClassEnum.MARI) return;

        this.cardSelected = false;
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }

        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;



        CardGroup mariCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        for(int i = 0; i < 20; ++i) {
            AbstractCard card;
            do {
                card = AbstractDungeon.getCard(AbstractDungeon.rollRarity()).makeCopy();
            }while(card == null || card instanceof Mari_Undying_Spark);
            if (mariCards.contains(card)) {
                --i;
            } else {
                if (card.type == AbstractCard.CardType.ATTACK && AbstractDungeon.player.hasRelic("Molten Egg 2")) {
                    card.upgrade();
                } else if (card.type == AbstractCard.CardType.SKILL && AbstractDungeon.player.hasRelic("Toxic Egg 2")) {
                    card.upgrade();
                } else if (card.type == AbstractCard.CardType.POWER && AbstractDungeon.player.hasRelic("Frozen Egg 2")) {
                    card.upgrade();
                }

                mariCards.addToBottom(card);
            }
        }

        Iterator var6 = mariCards.group.iterator();

        while(var6.hasNext()) {
            AbstractCard c = (AbstractCard)var6.next();
            UnlockTracker.markCardAsSeen(c.cardID);
        }

        cardSelectSize = mariCards.size();
        selectNeeded = Math.min(cardSelectSize,3);
        MariSavables saves = MariMod.saveableKeeper;
        saves.stewshineCards = selectNeeded;

        if(selectNeeded > 0) {
            AbstractDungeon.gridSelectScreen.open(mariCards, selectNeeded, this.DESCRIPTIONS[selectNeeded], false, false, false, true);
        }
    }

    public void update() {
        super.update();
        if (!this.cardSelected && (AbstractDungeon.gridSelectScreen.selectedCards.size() == selectNeeded)) {
            this.cardSelected = true;
            MariSavables saves = MariMod.saveableKeeper;
            AbstractCard select1;
            AbstractCard select2;
            AbstractCard select3;
            saves.setStewshineCost(0);
            if(selectNeeded >= 3){
                select3 = AbstractDungeon.gridSelectScreen.selectedCards.get(2);
                saves.stewshineCardC = new CardSave(select3.cardID, select3.timesUpgraded, select3.misc);
                saves.increaseStewshineCost(select3.cost);
                AbstractDungeon.topLevelEffects.add(new PurgeCardEffect((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(2), (float)Settings.WIDTH / 2.0F + 30.0F * Settings.scale + AbstractCard.IMG_WIDTH / 2.0F * 3, (float)Settings.HEIGHT / 2.0F));
            }
            if(selectNeeded >= 2) {
                select2 = AbstractDungeon.gridSelectScreen.selectedCards.get(1);
                saves.stewshineCardB = new CardSave(select2.cardID, select2.timesUpgraded, select2.misc);
                saves.increaseStewshineCost(select2.cost);
                AbstractDungeon.topLevelEffects.add(new PurgeCardEffect((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(1), (float) Settings.WIDTH / 2.0F - 30.0F * Settings.scale, (float)Settings.HEIGHT / 2.0F));
            }
            if(selectNeeded >= 1) {
                select1 = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                saves.stewshineCardA = new CardSave(select1.cardID, select1.timesUpgraded, select1.misc);
                saves.increaseStewshineCost(select1.cost);
                AbstractDungeon.topLevelEffects.add(new PurgeCardEffect((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0), (float) Settings.WIDTH / 2.0F - 30.0F * Settings.scale - AbstractCard.IMG_WIDTH / 2.0F * 3, (float)Settings.HEIGHT / 2.0F));

                AbstractCard stewshineCard = new Mari_Stewshine();
                stewshineCard.applyPowers();
                stewshineCard.initializeDescription();

                AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(stewshineCard,(float)Settings.WIDTH / 2.0F + 30.0F * Settings.scale + AbstractCard.IMG_WIDTH / 2.0F * 5, (float)Settings.HEIGHT / 2.0F));
            }


            Iterator var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

            while(var1.hasNext()) {
                AbstractCard card = (AbstractCard)var1.next();
                AbstractDungeon.player.masterDeck.removeCard(card);
                AbstractDungeon.transformCard(card, true, AbstractDungeon.miscRng);
            }

            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }

    }
    public AbstractRelic makeCopy()
    {
        return new MariStewshine();
    }
}
