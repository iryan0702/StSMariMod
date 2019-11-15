package mari_mod.cards;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMiscAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.MariMod;
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
    private static final int UPGRADE_BLOCK_INCREASE = 8;
    private static final int BLOCK_DECREASE = -2;
    private static final int BASE_BLOCK_AMT = 20;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Mari_Closure(){
        this(BASE_BLOCK_AMT);
    }

    public Mari_Closure(int misc){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.misc = misc;
        this.baseBlock = this.misc;
        this.block = this.baseBlock;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        if(this.misc < 100) AbstractDungeon.actionManager.addToBottom(new MariClosureAction(this.uuid, this.misc, BLOCK_DECREASE));
    }

    public void applyPowers() {
        this.baseBlock = this.misc;
        super.applyPowers();
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Closure();
    }

    @Override
    public void upgrade() {
        this.misc+=UPGRADE_BLOCK_INCREASE;
        this.upgradeBlock(UPGRADE_BLOCK_INCREASE);
        ++this.timesUpgraded;
        this.upgraded = true;
        if(this.misc < 100) {
            this.name = NAME + "+" + this.timesUpgraded;
            this.initializeTitle();
        }else{
            this.name = EXTENDED_DESCRIPTION[0];
            this.initializeTitle();
        }
        this.updateDescription();
    }

    public void updateDescription(){
        if(this.misc < 100) {
            this.rawDescription = DESCRIPTION;
            this.initializeDescription();
        }else{
            this.rawDescription = EXTENDED_DESCRIPTION[1];
            this.initializeDescription();
        }
    }

    public boolean canUpgrade() {
        return true;
    }
}