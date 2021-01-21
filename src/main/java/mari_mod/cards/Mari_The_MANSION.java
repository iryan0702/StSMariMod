package mari_mod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.actions.MariApplyPowersAction;
import mari_mod.actions.MariGainCardBlockAction;
import mari_mod.actions.MariInvestGoldAction;
import mari_mod.actions.MariTheMANSIONAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_The_MANSION extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_The_MANSION.class.getName());
    public static final String ID = "MariMod:Mari_The_MANSION";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 3;
    private static final int BASE_GOLD_COST = 15;
    private static final int BLOCK_AMT = 28;
    private static final int UPGRADE_BLOCK_AMT = 10;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Mari_The_MANSION(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(MariCustomTags.SPEND);
        this.block = BLOCK_AMT;
        this.baseBlock = this.block;
        this.baseGoldCost = BASE_GOLD_COST;
        this.goldCost = this.baseGoldCost;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new MariInvestGoldAction(this));
        addToBot(new MariTheMANSIONAction());
        addToBot(new MariApplyPowersAction(this));
        addToBot(new MariGainCardBlockAction(this));
        //AbstractDungeon.actionManager.addToBottom(new MariTheMANSIONAction(BLOCK_AMT, 3));



        //TODO: APPLY POWER

    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_The_MANSION();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK_AMT);
        }
    }
}