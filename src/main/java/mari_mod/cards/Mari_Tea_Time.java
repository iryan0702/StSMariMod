package mari_mod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.actions.MariPurgeCardsFromExhaustAction;
import mari_mod.actions.MariRecallAction;
import mari_mod.actions.MariSpendGoldAction;
import mari_mod.actions.MariSuccessfulKindleAction;
import mari_mod.patches.EphemeralCardPatch;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Tea_Time extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Tea_Time.class.getName());
    public static final String ID = "MariMod:Mari_Tea_Time";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int BASE_GOLD_COST = 10;
    private static final int UPGRADE_COST = 0;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Mari_Tea_Time(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        EphemeralCardPatch.EphemeralField.ephemeral.set(this, true);
        this.tags.add(MariCustomTags.SPEND);
        this.baseGoldCost = BASE_GOLD_COST;
        this.goldCost = this.baseGoldCost;
        this.isAnyTarget = true;
        this.tags.add(MariCustomTags.KINDLE);
        this.recallPreview = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCreature target;
        if(m != null) {
            target = m;
        }else{
            target = p;
        }
        AbstractDungeon.actionManager.addToBottom(new MariSpendGoldAction(this.goldCost));
        AbstractDungeon.actionManager.addToBottom(new MariPurgeCardsFromExhaustAction(true));


        if(target.hasPower(Radiance_Power.POWER_ID) && target.getPower(Radiance_Power.POWER_ID).amount >= 1){
            this.successfulKindle(target);
        }
        AbstractDungeon.actionManager.addToBottom(new MariSuccessfulKindleAction(target, new MariRecallAction(this)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Tea_Time();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeBaseCost(UPGRADE_COST);
            upgradeName();
        }
    }
}