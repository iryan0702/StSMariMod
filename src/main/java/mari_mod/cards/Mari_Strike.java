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
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
    STRIKE
    Starter card for Mari
    Mari's Strike is different from the starter strike for other classes
    to allow for more interesting decision making at the start of the game.
    A low base damage + applying Radiance makes Radiance on both self and
    enemies amplify overall damage in slightly different ways.
 */

public class Mari_Strike extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Strike.class.getName());
    public static final String ID = "MariMod:Mari_Strike";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK_DMG = 3;
    private static final int ATTACK_UPGRADE = 4;
    private static final int RADIANCE_AMOUNT = 1;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public Mari_Strike(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseRadiance = RADIANCE_AMOUNT;
        this.radiance = this.baseRadiance;

        this.baseDamage = ATTACK_DMG;
        this.damage = this.baseDamage;

        this.tags.add(CardTags.STRIKE);
        this.tags.add(MariCustomTags.RADIANCE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new Radiance_Power(m, this.radiance), this.radiance));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Strike();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeDamage(ATTACK_UPGRADE);
            upgradeName();
        }
    }
}