package mari_mod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.MariMod;
import mari_mod.characters.Mari;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Expenses extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Expenses.class.getName());
    public static final String ID = "MariMod:Mari_Expenses";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 2;
    private static final int DAMAGE = 12;
    private static final int SCALING = 4;
    private static final int UPGRADE_SCALING = 2;
    //private static final int GOLD_PER_SCALE = 10;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public Mari_Expenses(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseBlock = SCALING;
        this.block = this.baseBlock;

        this.baseDamage = DAMAGE;
        this.damage = this.baseDamage;

        this.baseMagicNumber = DAMAGE;
        this.magicNumber = this.baseMagicNumber;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.applyPowers();
        this.calculateCardDamage(m);
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        this.applyPowers();

        this.forgetBlock();
    }

    @Override
    public void applyPowers() {

        int scaling = SCALING;
        if(upgraded) scaling += UPGRADE_SCALING;

        int dam = DAMAGE;

        this.baseDamage = dam + scaling * (MariMod.timesMariSpentGoldThisCombat);
        this.damage =  this.baseDamage;

        super.applyPowers();

        if(this.damage > 0) {
            String desc;
            if(upgraded){
                desc = UPGRADE_DESCRIPTION;
            }else{
                desc = DESCRIPTION;
            }
            this.rawDescription = desc + EXTENDED_DESCRIPTION[0];
            this.initializeDescription();
        }

        this.forgetBlock();
    }


    public void forgetBlock(){
        this.baseBlock = SCALING;
        if(this.upgraded) this.baseBlock += UPGRADE_SCALING;
        this.block = this.baseBlock;
        this.isBlockModified = false;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Expenses();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.upgradeBlock(UPGRADE_SCALING);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}