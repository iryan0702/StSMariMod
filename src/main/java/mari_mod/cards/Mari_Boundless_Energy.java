package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Mari_Boundless_Energy extends AbstractMariCard {
    public static final String ID = "MariMod:Mari_Boundless_Energy";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 3;
    private static final int INITIAL_ENERGY_AND_DRAW = 2;
    private static final int BASE_GOLD_COST = 10;
    private static final int GOLD_COST_INCREASE = 5;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Mari_Boundless_Energy() {
        this(0);
    }

    public Mari_Boundless_Energy(int upgrades) {
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.timesUpgraded = upgrades;
        this.baseMagicNumber = INITIAL_ENERGY_AND_DRAW;
        this.magicNumber = this.baseMagicNumber;
        this.baseGoldCost = BASE_GOLD_COST;
        this.goldCost = this.baseGoldCost;
        this.exhaust = true;
        this.tags.add(MariCustomTags.SPEND);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
//        AbstractDungeon.actionManager.addToBottom(new MariInvestGoldAction(this));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.magicNumber));

    }

    public AbstractCard makeCopy() {
        return new Mari_Boundless_Energy(this.timesUpgraded);
    }

    public void upgrade() {
        this.upgradeMagicNumber(1);
        if(this.goldCost > 0) {
            this.upgradeGoldCost(GOLD_COST_INCREASE);
        }
        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = NAME + "+" + this.timesUpgraded;
        this.initializeTitle();
    }

    public boolean canUpgrade() {
        return true;
    }

}
