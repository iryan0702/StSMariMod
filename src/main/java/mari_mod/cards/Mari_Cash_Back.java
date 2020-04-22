package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
//import mari_mod.actions.MariLoseInvestedGoldAction;
import mari_mod.patches.EphemeralCardPatch;
import mari_mod.powers.Cash_Back_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Cash_Back extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Cash_Back.class.getName());
    public static final String ID = "MariMod:Mari_Cash_Back";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int CASH_BACK_AMOUNT = 15;
    private static final int CASH_BACK_AMOUNT_UPGRADE = 5;
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Mari_Cash_Back(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(MariCustomTags.SPEND);
        this.baseMagicNumber = CASH_BACK_AMOUNT;
        this.magicNumber = this.baseMagicNumber;
        EphemeralCardPatch.EphemeralField.ephemeral.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //addToBot(new MariLoseInvestedGoldAction(10));
        addToBot(new ApplyPowerAction(p, p, new Cash_Back_Power(p, this.magicNumber), this.magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeMagicNumber(CASH_BACK_AMOUNT_UPGRADE);
            upgradeName();
        }
    }
}