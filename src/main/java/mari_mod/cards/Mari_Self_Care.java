package mari_mod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.actions.MariInvestGoldAction;
import mari_mod.actions.MariPurgeNextRecallAction;
import mari_mod.actions.MariRecallAction;
import mari_mod.actions.MariSuccessfulKindleAction;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class Mari_Self_Care extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Self_Care.class.getName());
    public static final String ID = "MariMod:Mari_Self_Care";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int BASE_GOLD_COST = 5;
    private static final int BLOCK_AMT = 5;
    private static final int UPGRADE_BLOCK_AMT = 3;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Mari_Self_Care(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(MariCustomTags.SPEND);
        this.baseBlock = BLOCK_AMT;
        this.block = this.baseBlock;
        this.baseGoldCost = BASE_GOLD_COST;
        this.goldCost = this.baseGoldCost;
        this.tags.add(MariCustomTags.KINDLE);
        this.isAnyTarget = true;
        this.recallPreview = true;
        this.recallIthCard = 2;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCreature target;
        if(m != null) {
            target = m;
        }else{
            target = p;
        }
        addToBot(new MariInvestGoldAction(this));
        addToBot(new MariPurgeNextRecallAction());
        addToBot(new MariRecallAction());

        if(target.hasPower(Radiance_Power.POWER_ID) && target.getPower(Radiance_Power.POWER_ID).amount >= 1){
            this.successfulKindle(target);
        }

        ArrayList<AbstractGameAction> kindleActions = new ArrayList<>();
        kindleActions.add(new GainBlockAction(p, this.block));
        addToBot(new MariSuccessfulKindleAction(target, kindleActions));

    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Self_Care();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK_AMT);
        }
    }
}