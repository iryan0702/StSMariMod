package mari_mod.cards;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.patches.EphemeralCardPatch;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static mari_mod.MariMod.energySpentThisTurn;

public class Mari_Sparkle extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Sparkle.class.getName());
    public static final String ID = "MariMod:Mari_Sparkle";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 1;
    private static final int RADIANCE = 4;
    private static final int UPGRADE_RADIANCE = 1;
    private static final int DRAW = 1;
    private static final int UPGRADE_THRESH = -1;
    private static final int ENERGY_THRESH = 5;
    private boolean isExtra = false;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public Mari_Sparkle(boolean isExtra){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(MariCustomTags.RADIANCE);
        this.magicNumber = this.baseMagicNumber = ENERGY_THRESH;
        this.radiance = this.baseRadiance = RADIANCE;
        this.isExtra = isExtra;
        EphemeralCardPatch.EphemeralField.ephemeral.set(this, true);
    }

    public Mari_Sparkle(){
        this(false);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0] + energySpentThisTurn + EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new ApplyPowerAction(m, p, new Radiance_Power(m, this.radiance), this.radiance));
        //addToBot(new DrawCardAction(DRAW));

        if(!isExtra && energySpentThisTurn >= this.magicNumber) {
            isExtra = true;
            GameActionManager.queueExtraCard(this, m);
            GameActionManager.queueExtraCard(this, m);
        }
        isExtra = false;
    }

    public void triggerOnGlowCheck() {
        if (energySpentThisTurn >= this.magicNumber) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
            this.defaultGlowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
            this.defaultGlowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Sparkle(isExtra);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_THRESH);
            upgradeRadiance(UPGRADE_RADIANCE);
        }
    }
}