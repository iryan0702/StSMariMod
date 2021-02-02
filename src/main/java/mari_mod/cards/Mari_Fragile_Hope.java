package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import mari_mod.actions.MariFragileHopeAction;
import mari_mod.actions.MariRecallAction;
import mari_mod.actions.MariSuccessfulKindleAction;
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

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FrailPower(p, 1, false), 1));
        //AbstractDungeon.actionManager.addToBottom(new MariFragileHopeAction());
        //if(this.upgraded) {
        //    AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
        //}

        if(target.hasPower(Radiance_Power.POWER_ID) && target.getPower(Radiance_Power.POWER_ID).amount >= 1){
            this.successfulKindle(target);
        }
        addToBot(new MariSuccessfulKindleAction(target, new MariRecallAction(new MariFragileHopeAction()), this));

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