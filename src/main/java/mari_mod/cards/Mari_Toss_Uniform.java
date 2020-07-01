package mari_mod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.actions.MariAweAction;
import mari_mod.actions.MariSuccessfulKindleAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Toss_Uniform extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Toss_Uniform.class.getName());
    public static final String ID = "MariMod:Mari_Toss_Uniform";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int DAMAGE = 10;
    private static final int DAMAGE_UPGRADE = 2;
    private static final int SELF_BLOCK = 0;
    private static final int SELF_BLOCK_UPGRADE = 2;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Mari_Toss_Uniform(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.isAnyTarget = true;
        this.damage = this.baseDamage = DAMAGE;
        this.block = this.baseBlock = SELF_BLOCK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCreature target;
        if(m != null) {
            target = m;
        }else{
            target = p;
        }

        if(this.upgraded) addToBot(new GainBlockAction(p, this.block));
        addToBot(new DamageAction(target, new DamageInfo(p, this.damage), AbstractGameAction.AttackEffect.BLUNT_LIGHT, true));
        addToBot(new GainBlockAction(target, this.damage * 2));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Toss_Uniform();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeDamage(DAMAGE_UPGRADE);
            upgradeBlock(SELF_BLOCK_UPGRADE);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
            upgradeName();
        }
    }
}