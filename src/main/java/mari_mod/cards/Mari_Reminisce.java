package mari_mod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.actions.MariInvestGoldAction;
import mari_mod.actions.MariRecallAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Reminisce extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Reminisce.class.getName());
    public static final String ID = "MariMod:Mari_Reminisce";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;
    private static final int BASE_GOLD_COST = 5;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.NONE;
    public boolean canPullQuotations = true;
    public boolean canPullRadiance = true;
    public boolean canPullSpend = true;
    public boolean canPullExhaust = false;

    public Mari_Reminisce(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseGoldCost = BASE_GOLD_COST;
        this.goldCost = this.baseGoldCost;

        this.selfRetain = true;
        this.recallPreview = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new MariInvestGoldAction(this));
        addToBot(new MariRecallAction());

    }

    @Override
    public void applyPowers() {
        super.applyPowers();
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Reminisce();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            this.initializeDescription();
        }
    }
}