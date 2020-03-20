package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.powers.Research_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Research extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Research.class.getName());
    public static final String ID = "MariMod:Mari_Research";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int REDUCTION = 2;
    private static final int UPGRADE_REDUCTION = 1;
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Mari_Research(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseMagicNumber = REDUCTION;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Research_Power(p, this.magicNumber), this.magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_REDUCTION);
        }
    }
}