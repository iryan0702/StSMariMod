package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.MariMod;
import mari_mod.patches.EphemeralCardPatch;
import mari_mod.powers.Gold_Gain_No_Block_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Unveiled_Secret extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Unveiled_Secret.class.getName());
    public static final String ID = "MariMod:Mari_Unveiled_Secret";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;
    private static final int BASE_GOLD_COST = 10;
    private static final int GOLD_GAIN_UPGRADE = 5;
    private static final int BASE_GOLD_GAIN = 20;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Mari_Unveiled_Secret(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(MariCustomTags.SPEND);

        this.baseMagicNumber = BASE_GOLD_GAIN;
        this.magicNumber = this.baseMagicNumber;
        this.baseGoldCost = BASE_GOLD_COST;
        this.goldCost = this.baseGoldCost;
        EphemeralCardPatch.EphemeralField.ephemeral.set(this, true);

        this.tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Gold_Gain_No_Block_Power(p, this.magicNumber), this.magicNumber));
        MariMod.spendGold(this.goldCost);
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(GOLD_GAIN_UPGRADE);
        }
    }
}