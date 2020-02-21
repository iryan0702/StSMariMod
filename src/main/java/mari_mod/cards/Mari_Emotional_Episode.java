package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import mari_mod.MariMod;
import mari_mod.actions.MariEmotionalEpisodeAction;
import mari_mod.patches.EphemeralCardPatch;
import mari_mod.powers.Gold_Gain_Debuff_Power;
import mari_mod.powers.Gold_Gain_Not_Damaged_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Emotional_Episode extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Emotional_Episode.class.getName());
    public static final String ID = "MariMod:Mari_Emotional_Episode";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;
    private static final int BLOCK = 6;
    private static final int BLOCK_UPGRADE = 2;
    private static final int BASE_GOLD_GAIN = 10;
    private static final int GOLD_GAIN_UPGRADE = 2;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Mari_Emotional_Episode(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(MariCustomTags.SPEND);

        this.baseMagicNumber = BASE_GOLD_GAIN;
        this.magicNumber = this.baseMagicNumber;

        this.baseBlock = BLOCK;
        this.block = this.baseBlock;

        EphemeralCardPatch.EphemeralField.ephemeral.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new ApplyPowerAction(p, p, new FrailPower(p, 1, false), 1));
        addToBot(new MariEmotionalEpisodeAction(this.magicNumber));

        MariMod.spendGold(this.goldCost);
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(GOLD_GAIN_UPGRADE);
            upgradeBlock(BLOCK_UPGRADE);
        }
    }
}