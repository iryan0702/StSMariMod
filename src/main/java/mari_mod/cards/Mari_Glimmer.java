package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.actions.MariRecallAction;
import mari_mod.actions.MariReducePowerIfHavePowerAction;
import mari_mod.patches.EphemeralCardPatch;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Glimmer extends AbstractMariCard implements OnRecallCard{
    public static final Logger logger = LogManager.getLogger(Mari_Glimmer.class.getName());
    public static final String ID = "MariMod:Mari_Glimmer";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 2;
    private static final int RADIANCE = 1;
    private static final int AMOUNT = 2;
    private static final int UPGRADE_AMOUNT = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Mari_Glimmer(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(MariCustomTags.RADIANCE);
        this.baseRadiance = RADIANCE;
        this.radiance = this.baseRadiance;
        this.selfRetain = true;
        this.baseMagicNumber = AMOUNT;
        this.magicNumber = this.baseMagicNumber;

        EphemeralCardPatch.EphemeralField.ephemeral.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MariRecallAction(MariRecallAction.RecallType.RADIANCE));
    }

    @Override
    public void onRecall() {
        AbstractPlayer p = AbstractDungeon.player;
        for(int i = 0; i < this.magicNumber; i++){
            for(AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) addToBot(new ApplyPowerAction(mo, p, new Radiance_Power(mo, this.radiance), this.radiance));
            addToBot(new ApplyPowerAction(p, p, new Radiance_Power(p, this.radiance), this.radiance));
        }
        //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Radiance_Power(p, this.radiance), this.radiance));
        if(this.upgraded) this.costForTurn = 1;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Glimmer();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
            upgradeName();
        }
    }
}