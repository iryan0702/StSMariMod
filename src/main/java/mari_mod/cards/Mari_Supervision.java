package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.actions.MariSetCostOfALLCardsAction;
import mari_mod.powers.Choreography_Power;
import mari_mod.powers.Supervision_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Supervision extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Supervision.class.getName());
    public static final String ID = "MariMod:Mari_Supervision";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 3;
    private static final int UPGRADE_COST = 2;
    private static final int ENERGY_GAIN = 3;
    private static final int GOLD_COST_UPGRADE = -5;
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Mari_Supervision(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseMagicNumber = ENERGY_GAIN;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Supervision_Power(p, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new MariSetCostOfALLCardsAction(2));

        /*if(!p.hasPower(Supervision_Power.POWER_ID)){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Supervision_Power(p, this.magicNumber), this.magicNumber));
        }else if(this.magicNumber < p.getPower(Supervision_Power.POWER_ID).amount){
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, p.getPower(Supervision_Power.POWER_ID), p.getPower(Supervision_Power.POWER_ID).amount - this.magicNumber));
        }*/
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
        }
    }
}