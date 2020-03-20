package mari_mod.cards;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.actions.MariRecallAction;
import mari_mod.actions.MariSpendGoldAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Reminisce extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Reminisce.class.getName());
    public static final String ID = "MariMod:Mari_Reminisce";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 0;
    private static final int USES = 2;
    private static final int TOTAL_USES_UPGRADE = 3;
    private static final int BASE_GOLD_COST = 10;
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

        this.recallPreview = true;
        this.recallType = MariRecallAction.RecallType.RADIANCE;

        ExhaustiveField.ExhaustiveFields.baseExhaustive.set(this, USES);
        ExhaustiveField.ExhaustiveFields.exhaustive.set(this, USES);
        this.isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new MariSpendGoldAction(this.goldCost));
        addToBot(new MariRecallAction(MariRecallAction.RecallType.RADIANCE));

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
            ExhaustiveField.ExhaustiveFields.baseExhaustive.set(this, TOTAL_USES_UPGRADE);
            ExhaustiveField.ExhaustiveFields.exhaustive.set(this, TOTAL_USES_UPGRADE);
            ExhaustiveField.ExhaustiveFields.isExhaustiveUpgraded.set(this, true);
        }
    }
}