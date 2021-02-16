package mari_mod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.effects.MariTheFlyingCarEffect;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public class Mari_The_FLYING_CAR extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_The_FLYING_CAR.class.getName());
    public static final String ID = "MariMod:Mari_The_FLYING_CAR";
    private static final CardStrings cardStrings = languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 3;
    private static final int BASE_GOLD_COST = 15;
    private static final int ATTACK_DMG = 28;
    private static final int DAMAGE_AMP = 2;
    private static final int DAMAGE_AMP_UPGRADE = 1;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public Mari_The_FLYING_CAR(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(MariCustomTags.SPEND);

        this.baseDamage = ATTACK_DMG;
        this.damage = this.baseDamage;

        this.baseGoldCost = BASE_GOLD_COST;
        this.goldCost = this.baseGoldCost;

        this.magicNumber = this.baseMagicNumber = DAMAGE_AMP;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
//        AbstractDungeon.actionManager.addToBottom(new MariInvestGoldAction(this));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new MariTheFlyingCarEffect(m.hb.cX, m.hb.cY),1.5F));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    public void applyPowers() {
        AbstractPower radiancePower = AbstractDungeon.player.getPower(Radiance_Power.POWER_ID);
        if(radiancePower != null) ((Radiance_Power)radiancePower).exponentDamageBoost *= this.magicNumber;

        super.applyPowers();

        radiancePower = AbstractDungeon.player.getPower(Radiance_Power.POWER_ID);
        if(radiancePower != null) ((Radiance_Power)radiancePower).exponentDamageBoost /= this.magicNumber;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        AbstractPower radiancePower = AbstractDungeon.player.getPower(Radiance_Power.POWER_ID);
        if(radiancePower != null) ((Radiance_Power)radiancePower).exponentDamageBoost *= this.magicNumber;

        super.calculateCardDamage(mo);

        radiancePower = AbstractDungeon.player.getPower(Radiance_Power.POWER_ID);
        if(radiancePower != null) ((Radiance_Power)radiancePower).exponentDamageBoost /= this.magicNumber;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_The_FLYING_CAR();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(DAMAGE_AMP_UPGRADE);
        }
    }
}