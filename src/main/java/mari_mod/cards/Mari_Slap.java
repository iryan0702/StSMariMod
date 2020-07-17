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
import com.megacrit.cardcrawl.vfx.combat.VerticalImpactEffect;
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
    private static final int UPGRADE_DAMAGE = 0;
    private static final int SCALING = 1;
    private static final int UPGRADE_SCALING = 1;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private int veryBaseDamage;

    public Mari_Slap(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseDamage = DAMAGE;
        veryBaseDamage = this.damage = this.baseDamage;

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
        this.baseDamage = veryBaseDamage;
    }

    public void boost(){
        this.baseDamage += this.magicNumber;
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_SCALING);
            upgradeDamage(UPGRADE_DAMAGE);
            veryBaseDamage += UPGRADE_DAMAGE;
            this.initializeDescription();
        }
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