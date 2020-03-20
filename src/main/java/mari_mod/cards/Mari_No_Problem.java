package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import mari_mod.actions.MariMakeTempCardInExhaustPileAction;
import mari_mod.powers.No_Problem_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_No_Problem extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_No_Problem.class.getName());
    public static final String ID = "MariMod:Mari_No_Problem";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    //public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 0;
    //private static final int UPGRADE_COST = 0;
    private static final int SHUFFLE_AMOUNT = 2;
    private static final int RECALL_AMOUT = 2;
    private static final int UPGRADE_RECALL = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Mari_No_Problem(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseMagicNumber = RECALL_AMOUT;
        this.magicNumber =  this.baseMagicNumber;
        this.tags.add(MariCustomTags.QUOTATIONS);
        this.cardsToPreview = new Mari_Repression();
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new RemoveSpecificPowerAction(p, p, VulnerablePower.POWER_ID));
        addToBot(new MariMakeTempCardInExhaustPileAction(new Mari_Repression(), SHUFFLE_AMOUNT, false, true));
        addToBot(new ApplyPowerAction(p, p, new No_Problem_Power(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_No_Problem();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeMagicNumber(UPGRADE_RECALL);
            upgradeName();
            this.retain = true;
        }
    }
}