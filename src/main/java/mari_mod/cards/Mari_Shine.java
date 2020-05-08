package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.patches.EphemeralCardPatch;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Shine extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Shine.class.getName());
    public static final String ID = "MariMod:Mari_Shine";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;
    private static final int BASE_RADIANCE = 2;
    private static final int UPGRADE_RADIANCE = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Mari_Shine(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(MariCustomTags.RADIANCE);
        this.baseRadiance = BASE_RADIANCE;
        this.radiance = this.baseRadiance;
        this.baseMagicNumber = BASE_RADIANCE;
        this.magicNumber = baseMagicNumber;
        EphemeralCardPatch.EphemeralField.ephemeral.set(this, true);
        this.isAnyTarget = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCreature target;
        if(m != null) {
            target = m;
        }else{
            target = p;
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, p, new Radiance_Power(target, this.radiance), this.radiance));
    }

    @Override
    public void applyPowers() {

        this.baseRadiance = this.magicNumber;
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p.type == AbstractPower.PowerType.DEBUFF) {
                this.baseRadiance += 2;
            }
        }

        super.applyPowers();
        this.baseRadiance = this.magicNumber;
        this.modifiedRadiance = this.baseRadiance != this.radiance;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Shine();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_RADIANCE);
            upgradeRadiance(UPGRADE_RADIANCE);
        }
    }
}