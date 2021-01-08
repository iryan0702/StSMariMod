package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.actions.MariClosureAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Closure extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Closure.class.getName());
    public static final String ID = "MariMod:Mari_Closure";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 2;
    private static final int BLOCK_DECREASE = 6;
    private static final int UPGRADE_BLOCK_DECREASE = -3;
    private static final int BASE_BLOCK_AMT = 20;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Mari_Closure(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseBlock = BASE_BLOCK_AMT;
        this.block = this.baseBlock;

        this.baseMagicNumber = BLOCK_DECREASE;
        this.magicNumber = this.baseMagicNumber;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new MariClosureAction(this.uuid, p.currentBlock, -this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Closure();
    }

    @Override
    public void upgrade() {
        this.upgradeMagicNumber(UPGRADE_BLOCK_DECREASE);
        upgradeName();
        this.upgraded = true;
        this.updateDescription();
    }

    public void updateDescription(){
        this.rawDescription = DESCRIPTION;
        this.initializeDescription();
    }
}