package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.patches.EphemeralCardPatch;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Golden_Glint extends AbstractMariCard implements OnRecallCard{
    public static final Logger logger = LogManager.getLogger(Mari_Golden_Glint.class.getName());
    public static final String ID = "MariMod:Mari_Golden_Glint";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;
    private static final int BASE_GOLD_COST = 5;
    private static final int BASE_RADIANCE_GAIN = 3;
    private static final int UPGRADE_RADIANCE_GAIN = 1;
    private static final int BASE_BLOCK = 4;
    private static final int UPGRADE_BLOCK_GAIN = 2;
    private static final int BASE_DRAW = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Mari_Golden_Glint(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        EphemeralCardPatch.EphemeralField.ephemeral.set(this, true);
        this.tags.add(MariCustomTags.SPEND);
        this.tags.add(MariCustomTags.RADIANCE);
        this.baseRadiance = BASE_RADIANCE_GAIN;
        this.radiance = this.baseRadiance;
        this.baseGoldCost = BASE_GOLD_COST;
        this.goldCost = this.baseGoldCost;
        this.baseBlock = BASE_BLOCK;
        this.block = this.baseBlock;
        this.baseMagicNumber = BASE_DRAW;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
//        AbstractDungeon.actionManager.addToBottom(new MariInvestGoldAction(this));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Radiance_Power(p, this.radiance), this.radiance));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p,p,this.block, true));
    }

    @Override
    public void onRecall() {
        addToBot(new DrawCardAction(this.magicNumber, true));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeRadiance(UPGRADE_RADIANCE_GAIN);
            upgradeBlock(UPGRADE_BLOCK_GAIN);
        }
    }
}