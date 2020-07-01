package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.patches.EphemeralCardPatch;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Kyu extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Strike.class.getName());
    public static final String ID = "MariMod:Mari_Kyu";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 9;
    private static final int RADIANCE_COUNT = 9;
    private static final int LESS_COST = 2;
    private static final int LESS_COST_UPGRADE = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public Mari_Kyu(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);

        this.radiance = this.baseRadiance = RADIANCE_COUNT;
        this.baseMagicNumber = this.magicNumber = LESS_COST;

        this.tags.add(MariCustomTags.QUOTATIONS);
        this.tags.add(MariCustomTags.RADIANCE);

        EphemeralCardPatch.EphemeralField.ephemeral.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new Radiance_Power(m, this.radiance), this.radiance));
    }

    public void triggerOnExhaust() {
        for(int i = 0; i < this.magicNumber; i++) {
            this.addToTop(new ReduceCostAction(this.uuid, 1));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Kyu();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.upgradeMagicNumber(LESS_COST_UPGRADE);
        }
    }
}