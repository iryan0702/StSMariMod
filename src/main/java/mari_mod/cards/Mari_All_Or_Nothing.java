package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.actions.MariAllOrNothingAction;
import mari_mod.actions.MariSuccessfulKindleAction;
import mari_mod.actions.MariUnsuccessfulKindleAction;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_All_Or_Nothing extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_All_Or_Nothing.class.getName());
    public static final String ID = "MariMod:Mari_All_Or_Nothing";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 0;
    private static final int DRAW = 4;
    private static final int DRAW_UPGRADE = 6;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Mari_All_Or_Nothing(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);

        this.tags.add(MariCustomTags.QUOTATIONS);

        this.magicNumber = this.baseMagicNumber = DRAW;

        this.isEthereal = true;
        this.exhaust = true;
        this.isInnate = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(this.magicNumber));
        addToBot(new MariAllOrNothingAction());
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_All_Or_Nothing();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(DRAW_UPGRADE);
        }
    }

    public void publicInitializeTitle() {
        this.initializeTitle();
    }
}