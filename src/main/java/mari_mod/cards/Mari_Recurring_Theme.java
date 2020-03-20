package mari_mod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.actions.MariRecallAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Recurring_Theme extends AbstractMariCard{
    public static final Logger logger = LogManager.getLogger(Mari_Recurring_Theme.class.getName());
    public static final String ID = "MariMod:Mari_Recurring_Theme";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Mari_Recurring_Theme(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.recallPreview = true;
        this.recallType = MariRecallAction.RecallType.GLARING;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new MariRecallAction(MariRecallAction.RecallType.GLARING));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Recurring_Theme();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            this.selfRetain = true;
        }
    }
}