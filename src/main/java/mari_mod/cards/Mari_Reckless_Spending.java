package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.MariMod;
import mari_mod.powers.Gold_Gain_Debuff_Power;
import mari_mod.powers.Gold_Gain_Not_Damaged_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Reckless_Spending extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Reckless_Spending.class.getName());
    public static final String ID = "MariMod:Mari_Reckless_Spending";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;
    private static final int BASE_GOLD_COST = 10;
    private static final int GOLD_COST_UPGRADE = -5;
    private static final int BASE_GOLD_GAIN = 15;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Mari_Reckless_Spending(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(MariCustomTags.SPEND);

        this.baseMagicNumber = BASE_GOLD_GAIN;
        this.magicNumber = this.baseMagicNumber;
        this.baseGoldCost = BASE_GOLD_COST;
        this.goldCost = this.baseGoldCost;
        this.isEthereal = true;
        this.exhaust = true;

        this.tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Gold_Gain_Not_Damaged_Power(p, this.magicNumber), this.magicNumber));
        MariMod.spendGold(this.goldCost);
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeGoldCost(GOLD_COST_UPGRADE);
        }
    }
}