package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.powers.Higher_Ups_Power;
import mari_mod.powers.Higher_Ups_Turn_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Higher_Ups extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Higher_Ups.class.getName());
    public static final String ID = "MariMod:Mari_Higher_Ups";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Mari_Higher_Ups(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.upgraded){
            addToBot(new ApplyPowerAction(p,p,new Higher_Ups_Power(p,1),1));
        }else{
            addToBot(new ApplyPowerAction(p,p,new Higher_Ups_Turn_Power(p,1),1));
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new Mari_Higher_Ups();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {

            this.rawDescription = UPGRADE_DESCRIPTION;
            upgradeName();
            this.initializeDescription();
        }
    }
}