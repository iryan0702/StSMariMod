package mari_mod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.actions.MariInvestGoldAction;
import mari_mod.actions.MariReflectionAction;
import mari_mod.patches.EphemeralCardPatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Reflection extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Reflection.class.getName());
    public static final String ID = "MariMod:Mari_Reflection";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 1;
    private static final int BASE_GOLD_COST = 5;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public Mari_Reflection(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(MariCustomTags.SPEND);
        this.tags.add(MariCustomTags.RADIANCE);

        this.baseGoldCost = BASE_GOLD_COST;
        this.goldCost = this.baseGoldCost;
//        this.isAnyTarget = true;
//        this.exhaust = true;
        EphemeralCardPatch.EphemeralField.ephemeral.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new MariInvestGoldAction(this));
        AbstractDungeon.actionManager.addToBottom(new MariReflectionAction(m, this.upgraded, this.radiance, this.faded));
    }

    @Override
    public void setFadedStats() {
        this.rawDescription = this.upgraded ? EXTENDED_DESCRIPTION[1] : EXTENDED_DESCRIPTION[0];
        super.setFadedStats();
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Reflection();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}