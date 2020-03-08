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
    private static final int COST = 2;
    private static final int DAMAGE = 12;
    private static final int SCALING = 75;
    private static final int UPGRADE_SCALING = -25;
    //private static final int GOLD_PER_SCALE = 10;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public Mari_Expenses(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseDamage = DAMAGE;
        this.damage = this.baseDamage;

        this.baseMagicNumber = SCALING;
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

        this.baseDamage = DAMAGE + (MariMod.saveableKeeper.goldInvested/this.magicNumber);
        this.damage =  this.baseDamage;

        super.calculateCardDamage(mo);

    }

    @Override
    public void applyPowers() {

        this.baseDamage = DAMAGE + (MariMod.saveableKeeper.goldInvested/this.magicNumber);
        this.damage =  this.baseDamage;

        super.applyPowers();
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Expenses();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.upgradeMagicNumber(UPGRADE_SCALING);
            this.initializeDescription();
        }
    }
}