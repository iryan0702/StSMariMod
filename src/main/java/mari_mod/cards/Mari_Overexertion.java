package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.actions.MariMakeTempCardInExhaustPileAction;
import mari_mod.actions.MariOverexertionDrawFollowUpAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Overexertion extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Overexertion.class.getName());
    public static final String ID = "MariMod:Mari_Overexertion";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 0;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Mari_Overexertion(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);

        this.cardsToPreview = new Mari_Repression();
        this.isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        /*
        AbstractDungeon.actionManager.addToBottom(new MariSetCostReductionOnDrawAction(1,1));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p,this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new MariSetCostReductionOnDrawAction(0,1));
        */

        returnToHand = false;
        addToBot(new MariMakeTempCardInExhaustPileAction(new Mari_Repression(), 1, false, true));

        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));

        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1, new MariOverexertionDrawFollowUpAction(this), true));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Overexertion();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.isEthereal = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}