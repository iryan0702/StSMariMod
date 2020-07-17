package mari_mod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.MariMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Spontaneous_Strike extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Spontaneous_Strike.class.getName());
    public static final String ID = "MariMod:Mari_Spontaneous_Strike";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int BASE_DAMAGE = 8;
    private static final int BASE_DAMAGE_UPGRADE = 3;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public Mari_Spontaneous_Strike(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseMagicNumber = BASE_DAMAGE;
        this.magicNumber = this.baseMagicNumber;
        this.baseDamage = BASE_DAMAGE;
        this.damage = this.baseDamage;
        this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        this.baseDamage = this.magicNumber;

        this.baseDamage += MariMod.energySpentThisTurn * 2;
        super.calculateCardDamage(mo);

        this.baseDamage = this.magicNumber;
        this.isDamageModified = this.damage != this.magicNumber;
    }

    @Override
    public void applyPowers() {
        this.baseDamage = this.magicNumber;

        this.baseDamage += MariMod.energySpentThisTurn;
        super.applyPowers();

        this.baseDamage = this.magicNumber;
        this.isDamageModified = this.damage != this.magicNumber;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Spontaneous_Strike();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(BASE_DAMAGE_UPGRADE);
            upgradeDamage(BASE_DAMAGE_UPGRADE);
        }
    }
}