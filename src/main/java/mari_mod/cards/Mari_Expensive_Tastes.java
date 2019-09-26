package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.actions.MariExpensiveTastesAction;
import mari_mod.actions.MariSuccessfulKindleAction;
import mari_mod.actions.MariUnsuccessfulKindleAction;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static basemod.BaseMod.MAX_HAND_SIZE;

public class Mari_Expensive_Tastes extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Expensive_Tastes.class.getName());
    public static final String ID = "MariMod:Mari_Expensive_Tastes";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Mari_Expensive_Tastes(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
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


        if(target.hasPower(Radiance_Power.POWER_ID) && target.getPower(Radiance_Power.POWER_ID).amount >= 1){
            this.successfulKindle();
        }

        AbstractDungeon.actionManager.addToBottom(new MariUnsuccessfulKindleAction(target, new MariExpensiveTastesAction(false)));
        AbstractDungeon.actionManager.addToBottom(new MariSuccessfulKindleAction(target, new MariExpensiveTastesAction(true)));

    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Expensive_Tastes();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
        }
    }
}