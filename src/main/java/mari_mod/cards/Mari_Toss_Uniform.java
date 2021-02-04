package mari_mod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.MariMod;
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
    private static final int DAMAGE = 8;
    private static final int DAMAGE_UPGRADE = 2;
    private static final int SELF_BLOCK = 0;
    private static final int SELF_BLOCK_UPGRADE = 2;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Mari_Toss_Uniform(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.isAnyTarget = true;
        this.damage = this.baseDamage = DAMAGE;
        this.block = this.baseBlock = SELF_BLOCK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCreature targetedCreature;
        if(this.target == CardTarget.ENEMY) {
            targetedCreature = m;
        }else{
            targetedCreature = p;
        }

        if(upgraded){
            addToBot(new GainBlockAction(p, p, this.block));
        }

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                int originalBlock = targetedCreature.currentBlock;
                MariMod.instantDamageAction(targetedCreature, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AttackEffect.BLUNT_LIGHT);
                int blockGain = 2 * (originalBlock - targetedCreature.currentBlock);
                if(blockGain > 0) addToTop(new GainBlockAction(p, p, blockGain));
                addToTop(new WaitAction(0.1f));
                addToTop(new WaitAction(0.1f));
            }
        });
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