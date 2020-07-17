package mari_mod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.actions.MariMakeTempCardInExhaustPileAction;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Reckless_Driving extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Strike.class.getName());
    public static final String ID = "MariMod:Mari_Reckless_Driving";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK_DMG = 10;
    private static final int UPGRADE_ATTACK_DMG = 4;
    private static final int RADIANCE = 1;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public Mari_Reckless_Driving(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseDamage = ATTACK_DMG;
        this.damage = this.baseDamage;
        this.baseRadiance = this.radiance = RADIANCE;
        this.cardsToPreview = new Mari_Repression();
        this.tags.add(MariCustomTags.RADIANCE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new MariMakeTempCardInExhaustPileAction(new Mari_Repression(), 1, false, true));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new Radiance_Power(m, this.radiance), this.radiance));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new VulnerablePower(p,1,false),1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Reckless_Driving();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_ATTACK_DMG);
        }
    }
}