package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.powers.Choreography_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Formation_Book extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Formation_Book.class.getName());
    public static final String ID = "MariMod:Mari_Formation_Book";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Mari_Formation_Book(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Mari_$Mijuku_Dreamer(),1,true,true,false));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Mari_$Mirai_Ticket(),1,true,true,false));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Mari_$My_Mai_Tonight(),1,true,true,false));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Mari_$Miracle_Wave(),1,true,false,false));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
        }
    }
}