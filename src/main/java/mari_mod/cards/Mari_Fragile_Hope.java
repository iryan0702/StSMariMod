package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import mari_mod.actions.MariFragileHopeAction;
import mari_mod.actions.MariHugAction;
import mari_mod.actions.MariRecallAction;
import mari_mod.patches.EphemeralCardPatch;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Fragile_Hope extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Fragile_Hope.class.getName());
    public static final String ID = "MariMod:Mari_Fragile_Hope";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 0;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Mari_Fragile_Hope(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        EphemeralCardPatch.EphemeralField.ephemeral.set(this, true);
        this.recallPreview = true;
        this.recallType = MariRecallAction.RecallType.RADIANCE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FrailPower(p, 1, false), 1));
        addToBot(new MariRecallAction(MariRecallAction.RecallType.RADIANCE));
        //AbstractDungeon.actionManager.addToBottom(new MariFragileHopeAction());
        //if(this.upgraded) {
        //    AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
        //}
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Fragile_Hope();
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