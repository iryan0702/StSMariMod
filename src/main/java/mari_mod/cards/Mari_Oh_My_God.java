package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.MariMod;
import mari_mod.actions.MariDefianceAction;
import mari_mod.actions.MariOhMyGodAction;
import mari_mod.powers.Oh_My_God_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Oh_My_God extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Oh_My_God.class.getName());
    public static final String ID = "MariMod:Mari_Oh_My_God";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 2;
    private static final int PLAY_CARDS = 1;
    private static final int UPGRADE_COST = 2;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Mari_Oh_My_God(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseMagicNumber = PLAY_CARDS;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(MariCustomTags.QUOTATIONS);
        this.exhaust = true;
    }

    @Override
    public void atTurnStart() {
        MariMod.timesOMGUsedThisTurn = 0;
        super.atTurnStart();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new MariOhMyGodAction(this.magicNumber, m, this.upgraded, EXTENDED_DESCRIPTION[0]));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeBaseCost(UPGRADE_COST);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
            upgradeName();
        }
    }
}