package mari_mod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.patches.EphemeralCardPatch;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Debut extends AbstractMariCard{
    public static final Logger logger = LogManager.getLogger(Mari_Debut.class.getName());
    public static final String ID = "MariMod:Mari_Debut";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 0;
    private static final int BASE_RADIANCE = 2;
    private static final int BASE_DAMAGE = 1;
    private static final int RADIANCE_UPGRADE = 1;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public Mari_Debut(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(MariCustomTags.RADIANCE);
        this.baseRadiance = BASE_RADIANCE;
        this.radiance = this.baseRadiance;
        this.damage = this.baseDamage = BASE_DAMAGE;
        EphemeralCardPatch.EphemeralField.ephemeral.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
        addToBot(new ApplyPowerAction(p, p, new Radiance_Power(p, this.radiance), this.radiance));
        addToBot(new ApplyPowerAction(p, p, new Radiance_Power(p, this.radiance), this.radiance));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Debut();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeRadiance(RADIANCE_UPGRADE);
            this.initializeDescription();
            upgradeName();
        }
    }
}