package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import com.megacrit.cardcrawl.powers.EquilibriumPower;
import mari_mod.actions.MariSuccessfulKindleAction;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Well_Kept_Secret extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Well_Kept_Secret.class.getName());
    public static final String ID = "MariMod:Mari_Well_Kept_Secret";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 3;
    private static final int BLOCK = 20;
    private static final int UPGRADE_BLOCK = 5;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Mari_Well_Kept_Secret(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseBlock = BLOCK;
        this.block = this.baseBlock;
        this.isAnyTarget = true;
        this.tags.add(MariCustomTags.KINDLE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractCreature target;
        if(m != null) {
            target = m;
        }else{
            target = p;
        }
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p,p,this.block, true));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new BlurPower(p,1),1));
        if(target.hasPower(Radiance_Power.POWER_ID) && target.getPower(Radiance_Power.POWER_ID).amount >= 1){
            this.successfulKindle(target);
        }
        AbstractDungeon.actionManager.addToBottom(new MariSuccessfulKindleAction(target, new ApplyPowerAction(p, p, new EquilibriumPower(p, 1), 1), this));

    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Well_Kept_Secret();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
        }
    }
}