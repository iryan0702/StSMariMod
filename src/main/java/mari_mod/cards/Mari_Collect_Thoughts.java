package mari_mod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.actions.MariCollectThoughtsAction;
import mari_mod.actions.MariInvestGoldAction;
import mari_mod.actions.MariRecallAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Collect_Thoughts extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Collect_Thoughts.class.getName());
    public static final String ID = "MariMod:Mari_Collect_Thoughts";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int BASE_GOLD_COST = 10;
    private static final int RETURN_CARDS = 2;
    private static final int RETURN_UPGRADE = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Mari_Collect_Thoughts(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(MariCustomTags.SPEND);
        this.baseGoldCost = BASE_GOLD_COST;
        this.goldCost = this.baseGoldCost;
        this.magicNumber = this.baseMagicNumber = RETURN_CARDS;
//        this.isAnyTarget = true;
//        this.tags.add(MariCustomTags.KINDLE);
//        this.recallPreview = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
//        AbstractCreature target;
//        if(m != null) {
//            target = m;
//        }else{
//            target = p;
//        }
        AbstractDungeon.actionManager.addToBottom(new MariInvestGoldAction(this));
        for(int i = 0; i < this.magicNumber; i++) {
            AbstractDungeon.actionManager.addToBottom(new MariRecallAction(new MariCollectThoughtsAction()));
        }


//        if(target.hasPower(Radiance_Power.POWER_ID) && target.getPower(Radiance_Power.POWER_ID).amount >= 1){
//            this.successfulKindle(target);
//        }
//        AbstractDungeon.actionManager.addToBottom(new MariSuccessfulKindleAction(target, new MariRecallAction()));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Collect_Thoughts();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeMagicNumber(RETURN_UPGRADE);
            upgradeName();
        }
    }
}