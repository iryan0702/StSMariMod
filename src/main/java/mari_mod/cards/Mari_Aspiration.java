package mari_mod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Aspiration extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Aspiration.class.getName());
    public static final String ID = "MariMod:Mari_Aspiration";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 3;
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Mari_Aspiration(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {}

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Aspiration();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
        }
    }
}