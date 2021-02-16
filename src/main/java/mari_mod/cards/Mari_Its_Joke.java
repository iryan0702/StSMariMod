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
import mari_mod.actions.MariItsJokeAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Its_Joke extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Strike.class.getName());
    public static final String ID = "MariMod:Mari_Its_Joke";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;
    private static final int RADIANCE = 1;
    private static final int DAMAGE = 5;
    private static final int DAMAGE_UPGRADE = 3;
    private static final int HP_LOSS_THRESHOLD = 5;
    private static final int HP_LOSS_THRESHOLD_UPGRADE = 3;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public Mari_Its_Joke(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(MariCustomTags.QUOTATIONS);
        this.tags.add(MariCustomTags.RADIANCE);
        this.baseRadiance = this.radiance = RADIANCE;
        this.baseDamage = this.damage = DAMAGE;
        this.baseMagicNumber = this.magicNumber = HP_LOSS_THRESHOLD;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        AbstractDungeon.actionManager.addToBottom(new MariItsJokeAction(m, m.currentHealth, this.magicNumber, this.radiance));
    }

    @Override
    public AbstractCard makeCopy() {

        return new Mari_Its_Joke();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(DAMAGE_UPGRADE);
            upgradeMagicNumber(HP_LOSS_THRESHOLD_UPGRADE);
        }
    }
}