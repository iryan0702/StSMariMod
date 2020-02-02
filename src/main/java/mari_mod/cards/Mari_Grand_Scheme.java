package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import mari_mod.actions.MariFireStrikeAction;
import mari_mod.actions.MariGainGoldAction;
import mari_mod.actions.MariGrandSchemeAction;
import mari_mod.patches.EphemeralCardPatch;
import mari_mod.powers.Gold_Spend_Start_Of_Turn_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Grand_Scheme extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Grand_Scheme.class.getName());
    public static final String ID = "MariMod:Mari_Grand_Scheme";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final UIStrings noGoldDialogue = CardCrawlGame.languagePack.getUIString("NoGoldDialogue");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int GOLD_GAIN_TURNS = 2;
    private static final int GOLD_GAIN_TURNS_UPGRADE = 1;
    private static final int GOLD_GAIN = 15;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Mari_Grand_Scheme(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(MariCustomTags.SPEND);
        this.baseMagicNumber = GOLD_GAIN_TURNS;
        this.magicNumber = this.baseMagicNumber;
        EphemeralCardPatch.EphemeralField.ephemeral.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = 0; i < this.magicNumber; i++) {
            addToBot(new MariGainGoldAction(GOLD_GAIN));
        }
        addToBot(new ApplyPowerAction(p, p, new Gold_Spend_Start_Of_Turn_Power(p, this.magicNumber, GOLD_GAIN)));
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
            this.upgradeMagicNumber(GOLD_GAIN_TURNS_UPGRADE);
            this.initializeDescription();
            upgradeName();
        }
    }
}