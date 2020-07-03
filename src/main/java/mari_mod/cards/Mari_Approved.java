package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.actions.MariApprovedAction;
import mari_mod.patches.EphemeralCardPatch;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Approved extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Approved.class.getName());
    public static final String ID = "MariMod:Mari_Approved";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 1;
    private static final int BASE_RADIANCE = 3;
    private static final int UPGRADE_BASE_RADIANCE = 1;
    private static final int RADIANCE_SCALING = 7;
    private static final int UPGRADE_RADIANCE_SCALING = -2;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private int realBaseRadiance;

    public Mari_Approved(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.realBaseRadiance = this.baseRadiance = this.radiance = BASE_RADIANCE;
        this.baseMagicNumber = this.magicNumber = RADIANCE_SCALING;
        this.tags.add(MariCustomTags.QUOTATIONS);
        this.tags.add(MariCustomTags.RADIANCE);
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
        addToBot(new ApplyPowerAction(target, p, new Radiance_Power(target, this.radiance), this.radiance));
        addToBot(new RemoveAllBlockAction(target, p));
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        this.baseRadiance = this.realBaseRadiance;

        int blockCount = 0;
        AbstractPlayer p = AbstractDungeon.player;
        if(mo == null || (p.isDraggingCard && p.hoveredCard.equals(this) && target == CardTarget.SELF)){ // mo == null applied on play for SELF target cards (not null on preview when there's only one enemy: add SELF condition when player is dragging this card)
            blockCount = AbstractDungeon.player.currentBlock;
        }else{
            blockCount = mo.currentBlock;
        }

        if(blockCount < 0) blockCount = 0;

        this.baseRadiance += blockCount/this.magicNumber;

        super.calculateCardDamage(mo);
        this.baseRadiance = this.realBaseRadiance;
        this.modifiedRadiance = this.baseRadiance != this.radiance;
    }

    @Override
    public void applyPowers() { //Defaults to player block count as SELF target cards do not apply calculateCardDamage on hover
        this.baseRadiance = this.realBaseRadiance;

        int blockCount = AbstractDungeon.player.currentBlock;

        if(blockCount < 0) blockCount = 0;

        this.baseRadiance += blockCount/this.magicNumber;

        super.applyPowers();
        this.baseRadiance = this.realBaseRadiance;
        this.modifiedRadiance = this.baseRadiance != this.radiance;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Approved();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeRadiance(UPGRADE_BASE_RADIANCE);
            upgradeMagicNumber(UPGRADE_RADIANCE_SCALING);
            this.realBaseRadiance += UPGRADE_BASE_RADIANCE;
        }
    }
}