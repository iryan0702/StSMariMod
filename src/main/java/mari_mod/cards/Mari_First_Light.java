package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.actions.MariDebutAction;
import mari_mod.patches.EphemeralCardPatch;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_First_Light extends AbstractMariCard implements OnRecallCard{
    public static final Logger logger = LogManager.getLogger(Mari_First_Light.class.getName());
    public static final String ID = "MariMod:Mari_First_Light";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int BASE_RADIANCE = 3;
    private static final int RADIANCE_UPGRADE = 1;
    private static final int ENERGY_GAIN = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ALL;

    public Mari_First_Light(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(MariCustomTags.RADIANCE);
        this.baseRadiance = BASE_RADIANCE;
        this.radiance = this.baseRadiance;
        EphemeralCardPatch.EphemeralField.ephemeral.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new Radiance_Power(p, this.radiance), this.radiance));
        addToBot(new MariDebutAction(this.radiance, false));
    }

    @Override
    public void onRecall() {
        addToBot(new GainEnergyAction(ENERGY_GAIN));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_First_Light();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeRadiance(RADIANCE_UPGRADE);
            this.initializeDescription();
            upgradeName();
        }
    }
}