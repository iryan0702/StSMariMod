package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.actions.MariSpendGoldAction;
import mari_mod.powers.Administrator_Privilege_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Administrator_Privilege extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Administrator_Privilege.class.getName());
    public static final String ID = "MariMod:Mari_Administrator_Privilege";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int BASE_GOLD_COST = 25;
    private static final int BASE_CARD_FETCH = 3;
    private static final int UPGRADE_CARD_FETCH = 2;
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Mari_Administrator_Privilege(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(MariCustomTags.SPEND);
        this.baseGoldCost = BASE_GOLD_COST;
        this.goldCost = this.baseGoldCost;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new MariSpendGoldAction(this));
        //AbstractDungeon.actionManager.addToBottom(new ReprogramAction(p.drawPile.group.size()));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Administrator_Privilege_Power(p)));

    }


    @Override
    public AbstractCard makeCopy() {
        return new Mari_Administrator_Privilege();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.isInnate = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public float getTitleFontSize()
    {
        return 20;
    }
}