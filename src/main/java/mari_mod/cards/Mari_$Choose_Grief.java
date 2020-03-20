package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_$Choose_Grief extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_$Choose_Grief.class.getName());
    public static final String ID = "MariMod:Mari_$Choose_Grief";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = -2;
    private static final CardType TYPE = CardType.STATUS;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Mari_$Choose_Grief(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, CardColor.COLORLESS, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {}

    @Override
    public void onChoseThisOption() {
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FrailPower(AbstractDungeon.player, 1, false),1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_$Choose_Grief();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
        }
    }
}