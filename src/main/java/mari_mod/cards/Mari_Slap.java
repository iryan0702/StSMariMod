package mari_mod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.VerticalImpactEffect;
import mari_mod.MariStatTracker;
import mari_mod.actions.MariDefianceAction;
import mari_mod.actions.MariSlapAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Slap extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Slap.class.getName());
    public static final String ID = "MariMod:Mari_Slap";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int DAMAGE = 9;
    private static final int UPGRADE_DAMAGE = 2;
    private static final int SCALING = 3;
    private static final int UPGRADE_SCALING = 1;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public Mari_Slap(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseDamage = DAMAGE;
        this.damage = this.baseDamage;

        this.baseMagicNumber = SCALING;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractGameAction.AttackEffect effect = AbstractGameAction.AttackEffect.BLUNT_LIGHT;
        if(this.damage > this.baseDamage * 2) effect = AbstractGameAction.AttackEffect.BLUNT_HEAVY;
        if(this.damage > this.baseDamage * 5) AbstractDungeon.actionManager.addToBottom(new VFXAction(new VerticalImpactEffect(m.hb.cX + m.hb.width / 4.0F, m.hb.cY - m.hb.height / 4.0F)));;
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), effect));

    }


    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int originalBase = this.baseDamage;

        this.baseDamage = originalBase + MariStatTracker.debuffsReceivedThisAndLastEnemyTurn * this.magicNumber;
        this.damage =  this.baseDamage;

        super.calculateCardDamage(mo);

        this.baseDamage = originalBase;
        this.isDamageModified = this.baseDamage != this.damage;
    }

    @Override
    public void applyPowers() {

        int originalBase = this.baseDamage;

        this.baseDamage = originalBase + MariStatTracker.debuffsReceivedThisAndLastEnemyTurn * this.magicNumber;
        this.damage =  this.baseDamage;

        super.applyPowers();

        this.baseDamage = originalBase;
        this.isDamageModified = this.baseDamage != this.damage;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_SCALING);
            upgradeDamage(UPGRADE_DAMAGE);
            this.initializeDescription();
        }
    }
}