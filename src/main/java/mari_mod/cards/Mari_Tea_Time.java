package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.MariMod;
import mari_mod.actions.MariSpendGoldAction;
import mari_mod.actions.MariTeaTimeAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Tea_Time extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Tea_Time.class.getName());
    public static final String ID = "MariMod:Mari_Tea_Time";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int BASE_GOLD_COST = 5;
    private static final int STACK_REDUCTION = 2;
    private static final int STACK_REDUCTION_UPGRADE = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Mari_Tea_Time(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(MariCustomTags.SPEND);
        this.baseGoldCost = BASE_GOLD_COST;
        this.goldCost = this.baseGoldCost;
        this.baseMagicNumber = STACK_REDUCTION;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new MariSpendGoldAction(this.goldCost));
        AbstractDungeon.actionManager.addToBottom(new MariTeaTimeAction(this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Tea_Time();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(STACK_REDUCTION_UPGRADE);
        }
    }
}