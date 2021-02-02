package mari_mod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import mari_mod.actions.MariApplyPowersAction;
import mari_mod.actions.MariDefianceAction;
import mari_mod.actions.MariReducePowerIfHavePowerAction;
import mari_mod.actions.MariSuccessfulKindleAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

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
    private static final CardTarget TARGET = CardTarget.SELF;

    public Mari_Defiance(){
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
        //AbstractDungeon.actionManager.addToBottom(new MariDefianceAction(this.block));

        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p,p,this.block));

        ArrayList<AbstractGameAction> success = new ArrayList<>();
        success.add(new MariDefianceAction(this));
        success.add(new MariApplyPowersAction(this));
        success.add(new MariReducePowerIfHavePowerAction(p,p,FrailPower.POWER_ID,1));
        AbstractDungeon.actionManager.addToBottom(new MariSuccessfulKindleAction(target, success, this));
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
        }
    }
}