package mari_mod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.actions.MariAweAction;
import mari_mod.actions.MariSuccessfulKindleAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Awe extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Awe.class.getName());
    public static final String ID = "MariMod:Mari_Awe";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int BASE_CONVERT = 2;
    private static final int UPGRADE_CONVERT = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Mari_Awe(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseMagicNumber = BASE_CONVERT;
        this.magicNumber = this.baseMagicNumber;
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
        AbstractDungeon.actionManager.addToBottom(new MariSuccessfulKindleAction(target, new MariAweAction()));
        AbstractDungeon.actionManager.addToBottom(new MariSuccessfulKindleAction(target, new MariAweAction()));
        if(this.upgraded) AbstractDungeon.actionManager.addToBottom(new MariSuccessfulKindleAction(target, new MariAweAction()));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Awe();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.upgradeMagicNumber(UPGRADE_CONVERT);
        }
    }
}