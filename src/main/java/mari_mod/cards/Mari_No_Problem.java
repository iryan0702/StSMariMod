package mari_mod.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import mari_mod.actions.MariSuccessfulKindleAction;
import mari_mod.actions.MariUnsuccessfulKindleAction;
import mari_mod.powers.No_Problem_Power;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_No_Problem extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_No_Problem.class.getName());
    public static final String ID = "MariMod:Mari_No_Problem";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;
    private static final int BASE_TURN = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Mari_No_Problem(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseMagicNumber = BASE_TURN;
        this.magicNumber =  this.baseMagicNumber;
        this.tags.add(MariCustomTags.QUOTATIONS);
        this.tags.add(MariCustomTags.KINDLE);
        this.isAnyTarget = true;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {


        AbstractCreature target;
        if(m != null) {
            target = m;
        }else{
            target = p;
        }

        if(target.hasPower(Radiance_Power.POWER_ID) && target.getPower(Radiance_Power.POWER_ID).amount >= 1){
            this.successfulKindle();
        }
        AbstractDungeon.actionManager.addToBottom(new MariUnsuccessfulKindleAction(target, new ApplyPowerAction(p,p,new No_Problem_Power(p, 1), 1)));
        AbstractDungeon.actionManager.addToBottom(new MariSuccessfulKindleAction(target, new ApplyPowerAction(p,p,new No_Problem_Power(p, 2), 2)));

    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_No_Problem();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
        }
    }
}