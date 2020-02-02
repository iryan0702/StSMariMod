package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import mari_mod.actions.MariOverexertionDrawFollowUpAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Overexertion extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Overexertion.class.getName());
    public static final String ID = "MariMod:Mari_Overexertion";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int DRAW = 3;
    private static final int UPGRADE_DRAW = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Mari_Overexertion(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseMagicNumber = DRAW;
        this.magicNumber =  this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        /*
        AbstractDungeon.actionManager.addToBottom(new MariSetCostReductionOnDrawAction(1,1));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p,this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new MariSetCostReductionOnDrawAction(0,1));
        */
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.magicNumber, new MariOverexertionDrawFollowUpAction()));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new VulnerablePower(p,1,false),1));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new FrailPower(p,1,false),1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Overexertion();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_DRAW);
        }
    }
}