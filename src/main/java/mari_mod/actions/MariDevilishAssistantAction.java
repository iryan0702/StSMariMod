package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.MasterRealityPower;
import mari_mod.patches.EphemeralCardPatch;
import mari_mod.powers.Radiance_Power;

import java.util.NavigableMap;
import java.util.TreeMap;

public class MariDevilishAssistantAction extends AbstractGameAction {
    public AbstractPlayer p;
    public NavigableMap<Float, String> pool;
    public float totalWeight = 0f;
    public float MASTER_REALITY_WEIGHT = 40f;
    public boolean upgraded;

    public MariDevilishAssistantAction(boolean upgraded) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.upgraded = upgraded;
        this.initializePool();
    }

    public void initializePool(){
        pool = new TreeMap<>();
        add("Sentinel", 30).
        add("Feel No Pain", 40).
        add("Pummel", 50).
        add("Body Slam", 60).
        add("Thunderclap", 70).
        add("Pommel Strike", 80).
        add("Adrenaline", 30).
        add("Footwork", 40).
        add("Escape Plan", 50).
        add("Setup", 60).
        add("Blade Dance", 70).
        add("Choke", 80).
        add("Seek", 30).
        add("Sunder", 40).
        add("Fusion", 50).
        add("Chaos", 60).
        add("Cold Snap", 70).
        add("Rebound", 80).
        add("Blasphemy", 30).
        add("MasterReality", MASTER_REALITY_WEIGHT).
        add("TalkToTheHand", 50).
        add("Swivel", 60).
        add("DeceiveReality", 70).
        add("CutThroughFate", 80).
        add("Sadistic Nature", 20).
        add("Panache", 20).
        add("Secret Technique", 20).
        add("Secret Weapon", 20).
        add("Master of Strategy", 20).
        add("Enlightenment", 20);
    }

    public void improvePool(){
        //unconditional improvements
        add("Sentinel", 120).
        add("Adrenaline", 120).
        add("Footwork", 80).
        add("Escape Plan", 80).
        add("Sadistic Nature", 80).
        add("Panache", 80).
        add("Secret Technique", 80).
        add("Secret Weapon", 80).
        add("Master of Strategy", 80).
        add("Enlightenment", 80);

        AbstractPower str = p.getPower(StrengthPower.POWER_ID);
        AbstractPower rad = p.getPower(Radiance_Power.POWER_ID);
        int strAmount = str == null ? 0 : str.amount;
        int radAmount = rad == null ? 0 : rad.amount;
        int baseDamageBoost = strAmount + radAmount;
        int masterFadingCards = 0;
        int handFadingCards = 0;
        int handExhaustCards = 0;
        int drawFadingCards = 0;
        int playerBlock = p.currentBlock;
        int highestCostInHand = 0;
        int highestCostAttackInHand = 0;
        int minEnemyHP = 999999;
        int deckSize = 0;
        float averageDeckCost = 0;

        for(AbstractCard c: AbstractDungeon.player.masterDeck.group){
            if(EphemeralCardPatch.EphemeralField.ephemeral.get(c)) masterFadingCards++;
            if(c.type == AbstractCard.CardType.ATTACK) highestCostAttackInHand = Math.max(c.costForTurn, highestCostAttackInHand);
            highestCostInHand = Math.max(c.costForTurn, highestCostInHand);
            deckSize++;
            averageDeckCost += Math.min(0, c.costForTurn);
        }
        averageDeckCost /= deckSize;

        for(AbstractCard c: AbstractDungeon.player.hand.group){
            if(EphemeralCardPatch.EphemeralField.ephemeral.get(c)) handFadingCards++;
            if(c.exhaust) handExhaustCards++;
        }

        for(AbstractCard c: AbstractDungeon.player.hand.group){
            if(EphemeralCardPatch.EphemeralField.ephemeral.get(c)) drawFadingCards++;
        }

        for(AbstractMonster m: AbstractDungeon.getCurrRoom().monsters.monsters){
            minEnemyHP = Math.min(m.currentHealth, minEnemyHP);
        }

        add("Feel No Pain", 200 * masterFadingCards + 300 * drawFadingCards + 600 * handFadingCards);

        if(baseDamageBoost > 2) add("Pummel", 300 * baseDamageBoost);
        if(baseDamageBoost > 4) add("Blade Dance", 600 + 150 * baseDamageBoost);

        add("Body Slam", 120 * playerBlock);
        if(highestCostInHand > 2) add("Setup", 200 * (highestCostInHand + 5));

        if(minEnemyHP < 24) add("Sunder", 4800);
        if(averageDeckCost >= 1.5f) add("Fusion", averageDeckCost * 800);

        if(highestCostAttackInHand > 2) add("Swivel", 300 * (highestCostInHand + 4));
        if(p.hasPower(MasterRealityPower.POWER_ID) && pool.containsKey("MasterReality")){
            pool.remove("MasterReality");
            totalWeight -= MASTER_REALITY_WEIGHT;
        }

    }

    public void update() {
        if(this.upgraded) improvePool();

        float val = AbstractDungeon.cardRandomRng.random()*totalWeight;
        String finalKey = pool.higherEntry(val).getValue();
        AbstractCard c = CardLibrary.getCard(finalKey).makeCopy();
        EphemeralCardPatch.EphemeralField.ephemeral.set(c, true);
        //using sameUUID to have ephemeral be copied
        addToTop(new MakeTempCardInHandAction(c, false, true));
        this.isDone = true;
    }

    public MariDevilishAssistantAction add(String key, float weight){
        totalWeight += weight;
        pool.put(totalWeight, key);
        return this;
    }
}
