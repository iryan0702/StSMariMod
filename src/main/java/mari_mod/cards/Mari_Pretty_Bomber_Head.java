package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ConfusionPower;
import mari_mod.powers.Choreography_Power;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Pretty_Bomber_Head extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Pretty_Bomber_Head.class.getName());
    public static final String ID = "MariMod:Mari_Pretty_Bomber_Head";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int EXTRA_DRAW = 1;
    private static final int UPGRADE_COST = 0;
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Mari_Pretty_Bomber_Head(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseMagicNumber = EXTRA_DRAW;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(MariCustomTags.QUOTATIONS);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ConfusionPower(p)));
        AbstractDungeon.player.gameHandSize += this.magicNumber;
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.upgradeBaseCost(UPGRADE_COST);
            this.initializeDescription();
        }
    }

    @Override
    public float getTitleFontSize()
    {
        return 18;
    }
}