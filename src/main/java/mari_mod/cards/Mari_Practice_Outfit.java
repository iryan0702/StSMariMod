package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.powers.Practice_Outfit_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Practice_Outfit extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Practice_Outfit.class.getName());
    public static final String ID = "MariMod:Mari_Practice_Outfit";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;
    private static final int BLOCK = 20;
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL;

    public Mari_Practice_Outfit(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.exhaust = true;
        this.baseBlock = BLOCK;
        this.block = this.baseBlock;
        this.baseMagicNumber = BLOCK;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new ApplyPowerAction(p, p, new Practice_Outfit_Power(p)));
//        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block, true ));
//        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BarricadePower(p)));
//
//        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
//
//            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(mo, p, this.magicNumber, true ));
//            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new BarricadePower(mo)));
//
//        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
//        if(this.block != this.magicNumber){
//            this.rawDescription = UPGRADE_DESCRIPTION;
//            this.initializeDescription();
//        }else{
//            this.rawDescription = DESCRIPTION;
//            this.initializeDescription();
//        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeBaseCost(UPGRADE_COST);
            upgradeName();
        }
    }
}