package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.actions.MariRecallAction;
import mari_mod.patches.EphemeralCardPatch;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Glimmer extends AbstractMariCard{
    public static final Logger logger = LogManager.getLogger(Mari_Glimmer.class.getName());
    public static final String ID = "MariMod:Mari_Glimmer";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 2;
    private static final int RADIANCE = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL;

    public Mari_Glimmer(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(MariCustomTags.RADIANCE);
        this.baseRadiance = RADIANCE;
        this.radiance = this.baseRadiance;

        EphemeralCardPatch.EphemeralField.ephemeral.set(this, true);
        this.recallPreview = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //addToBot(new MariRecallAction(this, new MariGlimmerFollowUpAction(this.radiance)));
        addToBot(new MariRecallAction(this));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Radiance_Power(p, this.radiance), this.radiance));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Radiance_Power(p, this.radiance), this.radiance));
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new Radiance_Power(mo, this.radiance), this.radiance));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new Radiance_Power(mo, this.radiance), this.radiance));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Glimmer();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.selfRetain = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
            upgradeName();
        }
    }
}