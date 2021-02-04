package mari_mod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.VerticalImpactEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Slap extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Slap.class.getName());
    public static final String ID = "MariMod:Mari_Slap";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 1;
    private static final int DAMAGE = 8;
    private static final int UPGRADE_DAMAGE = 2;
    private static final int SCALING = 2;
    private static final int UPGRADE_SCALING = 1;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public Mari_Slap(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);

        this.damage = this.baseDamage = DAMAGE;

        this.baseMagicNumber = SCALING;
        this.magicNumber = this.baseMagicNumber;

//        slapMod = new SlapMod();
//        CardModifierManager.addModifier(this, slapMod);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractGameAction.AttackEffect effect = AbstractGameAction.AttackEffect.BLUNT_LIGHT;
        if(this.damage > this.baseDamage * 2) effect = AbstractGameAction.AttackEffect.BLUNT_HEAVY;
        if(this.damage > this.baseDamage * 5) AbstractDungeon.actionManager.addToBottom(new VFXAction(new VerticalImpactEffect(m.hb.cX + m.hb.width / 4.0F, m.hb.cY - m.hb.height / 4.0F)));;
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), effect));
        this.misc = 0;
    }

    @Override
    public void applyPowers() {
        int originalBase = this.baseDamage;
        this.baseDamage += misc;

        super.applyPowers();

        this.isDamageModified = this.damage != originalBase;
        this.baseDamage = originalBase;

        initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int originalBase = this.baseDamage;
        this.baseDamage += misc;

        super.calculateCardDamage(mo);

        this.isDamageModified = this.damage != originalBase;
        this.baseDamage = originalBase;

        initializeDescription();
    }

    @Override
    public void initializeDescription() {
        if(!CardCrawlGame.isInARun() || AbstractDungeon.player == null || AbstractDungeon.player.hand.contains(this)){
            this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];
        }else if(this.misc > 0){
            this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[1] + (this.misc) + EXTENDED_DESCRIPTION[2] + EXTENDED_DESCRIPTION[0];
        }else{
            this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];
        }
        super.initializeDescription();
    }

    public void boost(){
        this.misc += this.magicNumber;
        if(AbstractDungeon.player.hand.contains(this)){
            applyPowers();
        }
        initializeDescription();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_SCALING);
            upgradeDamage(UPGRADE_DAMAGE);
            this.initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        Mari_Slap returnCopy = new Mari_Slap();
        returnCopy.misc = this.misc;
        return returnCopy;
    }

    //    public class SlapMod extends AbstractCardModifier{
//
//        public int boost;
//
//        @Override
//        public boolean isInherent(AbstractCard card) {
//            return true;
//        }
//
//        @Override
//        public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
//            super.onUse(card, target, action);
//            this.boost = 0;
//        }
//
//        @Override
//        public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
//            return boost + super.modifyDamage(damage, type, card, target);
//        }
//
//        @Override
//        public AbstractCardModifier makeCopy() {
//            return new SlapMod();
//        }
//    }
}