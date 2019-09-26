package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_$My_Mai_Tonight extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_$My_Mai_Tonight.class.getName());
    public static final String ID = "MariMod:Mari_$My_Mai_Tonight";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Mari_$My_Mai_Tonight(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p,1));
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(2));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_$My_Mai_Tonight();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
        }
    }
}