//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import mari_mod.MariMod;
import mari_mod.cards.*;
import mari_mod.screens.MariReminisceScreen;

import java.util.ArrayList;
import java.util.UUID;

import static basemod.BaseMod.MAX_HAND_SIZE;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;

public class MariReminisceAction extends AbstractGameAction {
    private boolean retrieveCard = false;
    private CardType cardType = null;
    private ArrayList<AbstractCard> list = new ArrayList<AbstractCard>();
    private AbstractPlayer p = AbstractDungeon.player;
    private Mari_Reminisce sourceCard;
    private UUID uuid;

    public MariReminisceAction(Mari_Reminisce source, boolean radiance, boolean spend, boolean exhaust, boolean quotation, UUID uuid) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.sourceCard = source;
        this.uuid = uuid;
        AbstractCard c;
        if(spend) {
            c = new Mari_$Memories_Of_Luxury();
            this.list.add(c);
        }
        if(radiance) {
            c = new Mari_$Memories_Of_Uchiura();
            this.list.add(c);
        }
        if(quotation) {
            c = new Mari_$Memories_Of_Performance();
            this.list.add(c);
        }
        if(exhaust) {
            c = new Mari_$Memories_Of_Loss();
            this.list.add(c);
        }
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST && this.list.size() >= 1) {
            MariMod.mariReminisceScreen.open(this.list, null, "Hmm...");
            tickDuration();
            return;
        }


        if (MariMod.mariReminisceScreen.choice.cardID.equals("MariMod:Mari_$Memories_Of_Loss")){
            sourceCard.canPullExhaust = false;
        }else if(MariMod.mariReminisceScreen.choice.cardID.equals("MariMod:Mari_$Memories_Of_Uchiura")){
            sourceCard.canPullRadiance = false;
        }else if(MariMod.mariReminisceScreen.choice.cardID.equals("MariMod:Mari_$Memories_Of_Luxury")){
            sourceCard.canPullSpend = false;
        }else if(MariMod.mariReminisceScreen.choice.cardID.equals("MariMod:Mari_$Memories_Of_Performance")){
            sourceCard.canPullQuotations = false;
        }
        if(!p.exhaustPile.isEmpty() && !retrieveCard) {
            ArrayList<AbstractCard> cardList = new ArrayList<>();
            ArrayList<AbstractCard> exhaustList = (ArrayList<AbstractCard>) AbstractDungeon.player.exhaustPile.group.clone();
            if (MariMod.mariReminisceScreen.choice.cardID.equals("MariMod:Mari_$Memories_Of_Loss")){
                for(AbstractCard card: exhaustList){
                    if(card.exhaust) cardList.add(card);
                }
            }else if(MariMod.mariReminisceScreen.choice.cardID.equals("MariMod:Mari_$Memories_Of_Uchiura")){
                for(AbstractCard card: exhaustList){
                    if(card.hasTag(MariCustomTags.RADIANCE)) cardList.add(card);
                }
            }else if(MariMod.mariReminisceScreen.choice.cardID.equals("MariMod:Mari_$Memories_Of_Luxury")){
                for(AbstractCard card: exhaustList){
                    if(card.hasTag(MariCustomTags.SPEND)) cardList.add(card);
                }
            }else if(MariMod.mariReminisceScreen.choice.cardID.equals("MariMod:Mari_$Memories_Of_Performance")){
                for(AbstractCard card: exhaustList){
                    if(card.hasTag(MariCustomTags.QUOTATIONS)) cardList.add(card);
                }
            }

            if(cardList.size() > 0) {
                AbstractCard card = cardList.get(cardRandomRng.random(cardList.size() - 1));

                retrieveCard = true;

                card.unfadeOut();
                if (p.hand.size() == MAX_HAND_SIZE) {
                    p.drawPile.moveToDiscardPile(card);
                    p.createHandIsFullDialog();
                } else {
                    p.drawPile.removeCard(card);
                    p.hand.addToHand(card);
                }

                card.fadingOut = false;
                p.exhaustPile.removeCard(card);

                p.hand.refreshHandLayout();
                p.hand.applyPowers();
            }
        }
        tickDuration();
    }
}
