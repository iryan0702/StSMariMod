package mari_mod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.actions.MariSpendGoldAction;
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
    private static final int ATTACK_DMG = 32;
    private static final int UPGRADE_ATTACK_DMG = 10;
    private static final int RADIANCE = 5;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public Mari_The_FLYING_CAR(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(MariCustomTags.SPEND);
        this.tags.add(MariCustomTags.RADIANCE);

        this.baseDamage = ATTACK_DMG;
        this.damage = this.baseDamage;

        this.baseGoldCost = BASE_GOLD_COST;
        this.goldCost = this.baseGoldCost;

        this.baseRadiance = RADIANCE;
        this.radiance = this.baseRadiance;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new MariSpendGoldAction(this));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new MariTheFlyingCarEffect(m.hb.cX, m.hb.cY),1.5F));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new Radiance_Power(m, this.radiance), this.radiance));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_The_FLYING_CAR();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_ATTACK_DMG);
        }
    }
}