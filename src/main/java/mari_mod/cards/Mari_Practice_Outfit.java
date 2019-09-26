package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.powers.Practice_Outfit_Buff_Power;
import mari_mod.powers.Practice_Outfit_Debuff_Power;
import mari_mod.powers.Supervision_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Practice_Outfit extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Practice_Outfit.class.getName());
    public static final String ID = "MariMod:Mari_Practice_Outfit";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 3;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Mari_Practice_Outfit(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.exhaust = true;
        this.isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(!p.hasPower(Practice_Outfit_Debuff_Power.POWER_ID)){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Practice_Outfit_Debuff_Power(p, 2), 2));
        }else{
            int currentAmount = p.getPower(Practice_Outfit_Debuff_Power.POWER_ID).amount;
            if(currentAmount < 2) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Practice_Outfit_Debuff_Power(p, 2-currentAmount), 2-currentAmount));
            }
        }

        if(!p.hasPower(Practice_Outfit_Buff_Power.POWER_ID)){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Practice_Outfit_Buff_Power(p, 2), 2));
        }else{
            int currentAmount = p.getPower(Practice_Outfit_Buff_Power.POWER_ID).amount;
            if(currentAmount < 2) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Practice_Outfit_Buff_Power(p, 2-currentAmount), 2-currentAmount));
            }
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.isEthereal = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}