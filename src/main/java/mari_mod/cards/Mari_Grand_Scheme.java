package mari_mod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import mari_mod.actions.MariFireStrikeAction;
import mari_mod.actions.MariGrandSchemeAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Grand_Scheme extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Grand_Scheme.class.getName());
    public static final String ID = "MariMod:Mari_Grand_Scheme";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = -1;
    private static final int GOLD_COST = 5;
    private static final int GOLD_RETURN = 15;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Mari_Grand_Scheme(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(MariCustomTags.SPEND);
        this.baseGoldCost = GOLD_COST;
        this.goldCost = this.baseGoldCost;
        this.baseMagicNumber = GOLD_RETURN;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.energyOnUse < EnergyPanel.totalCount) {
            this.energyOnUse = EnergyPanel.totalCount;
        }
        AbstractDungeon.actionManager.addToBottom(new MariGrandSchemeAction(p, this.goldCost, this.magicNumber, this.energyOnUse, this.upgraded, this.freeToPlayOnce));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if(!canUse){
            return false;
        }else {
            if (AbstractDungeon.player.gold < this.goldCost * EnergyPanel.getCurrentEnergy()) {
                canUse = false;
                this.cantUseMessage = "I don't have enough gold!";
            }
            return canUse;
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Grand_Scheme();
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