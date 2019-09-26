package mari_mod.cards;

import com.evacipated.cardcrawl.mod.stslib.patches.bothInterfaces.OnReceivePowerPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.ReApplyPowersAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import mari_mod.actions.MariApplyPowersAction;
import mari_mod.actions.MariDefianceAction;
import mari_mod.actions.MariDelayedActionAction;
import mari_mod.powers.Delicacy_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Defiance extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Defiance.class.getName());
    public static final String ID = "MariMod:Mari_Defiance";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 1;
    private static final int BLOCK = 8;
    private static final int UPGRADE_BLOCK = 2;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Mari_Defiance(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseBlock = BLOCK;
        this.block = this.baseBlock;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //AbstractDungeon.actionManager.addToBottom(new MariDefianceAction(this.block));

        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p,p,this.block));
        if(p.hasPower(FrailPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p,p,p.getPower(FrailPower.POWER_ID),1));
            AbstractDungeon.actionManager.addToBottom(new MariApplyPowersAction(this));
            AbstractDungeon.actionManager.addToBottom(new MariDefianceAction(this));
        }
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
        }
    }
}