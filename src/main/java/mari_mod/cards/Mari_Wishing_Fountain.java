package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.powers.Exchange_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Wishing_Fountain extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Wishing_Fountain.class.getName());
    public static final String ID = "MariMod:Mari_Wishing_Fountain";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 0;
    private static final int BASE_GOLD_COST = 5;
    private static final int STACKS = 1;
    private static final int STACKS_UP = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Mari_Wishing_Fountain(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseMagicNumber = STACKS;
        this.magicNumber = this.baseMagicNumber;

        this.baseGoldCost = BASE_GOLD_COST;
        this.goldCost = this.baseGoldCost;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
//        AbstractDungeon.actionManager.addToBottom(new MariInvestGoldAction(this));
//        AbstractDungeon.actionManager.addToBottom(new MariGainGoldAction(this.magicNumber));
//        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(ENERGY_GAIN));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Exchange_Power(p, this.magicNumber), this.magicNumber));
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(STACKS_UP);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}