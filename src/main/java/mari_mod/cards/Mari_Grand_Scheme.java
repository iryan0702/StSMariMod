package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.powers.Grand_Scheme_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Grand_Scheme extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Grand_Scheme.class.getName());
    public static final String ID = "MariMod:Mari_Grand_Scheme";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 0;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Mari_Grand_Scheme(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.exhaust = true;
        this.cardsToPreview = new Mari_Relit();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new Grand_Scheme_Power(p)));
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            addToBot(new ApplyPowerAction(mo, p, new Grand_Scheme_Power(mo)));
        }
        this.addToBot(new MakeTempCardInHandAction(new Mari_Relit()));// 37
    }

    /*
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if(!canUse){
            return false;
        }else {
            if (AbstractDungeon.player.gold < this.goldCost * EnergyPanel.getCurrentEnergy()) {
                canUse = false;
                this.cantUseMessage = noGoldDialogue.TEXT[0];
            }
            return canUse;
        }
    }*/

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Grand_Scheme();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.retain = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
            upgradeName();
        }
    }
}