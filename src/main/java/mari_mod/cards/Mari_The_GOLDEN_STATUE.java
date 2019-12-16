package mari_mod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.MariMod;
import mari_mod.actions.MariSpendGoldAction;
import mari_mod.actions.MariTheMANSIONAction;
import mari_mod.actions.MariWaitAction;
import mari_mod.effects.MariGoldenStatueEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_The_GOLDEN_STATUE extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_The_GOLDEN_STATUE.class.getName());
    public static final String ID = "MariMod:Mari_The_GOLDEN_STATUE";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 3;
    private static final int BASE_GOLD_COST = 10;
    private static final int BLOCK_AMT = 19;
    private static final int UPGRADE_BLOCK_AMT = 5;
    private static final int DAMAGE_AMT = 19;
    private static final int UPGRADE_DAMAGE_AMT = 5;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public Mari_The_GOLDEN_STATUE(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(MariCustomTags.SPEND);
        this.baseBlock = BLOCK_AMT;
        this.block = this.baseBlock;
        this.baseGoldCost = BASE_GOLD_COST;
        this.baseDamage = DAMAGE_AMT;
        this.damage = this.baseDamage;
        this.goldCost = this.baseGoldCost;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new MariSpendGoldAction(this.goldCost));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new MariGoldenStatueEffect(m.hb.cX),0.1F));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new MariWaitAction(MariGoldenStatueEffect.ANIMATION_START-MariGoldenStatueEffect.FALL_END-0.6F));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_The_GOLDEN_STATUE();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK_AMT);
            upgradeDamage(UPGRADE_DAMAGE_AMT);
        }
    }

    @Override
    public float getTitleFontSize()
    {
        return 20;
    }
}