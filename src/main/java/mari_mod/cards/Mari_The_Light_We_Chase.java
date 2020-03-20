package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.powers.The_Light_We_Chase_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_The_Light_We_Chase extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_The_Light_We_Chase.class.getName());
    public static final String ID = "MariMod:Mari_The_Light_We_Chase";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final int RADIANCE_GAIN = 1;
    public static final int AMOUNT = 2;
    private static final int COST = 1;
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Mari_The_Light_We_Chase(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(MariCustomTags.RADIANCE);

        //this.baseRadiance = RADIANCE_GAIN;
        //this.radiance = this.baseRadiance;

        this.baseMagicNumber = AMOUNT;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.upgraded) {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new The_Light_We_Chase_Power(p, this.magicNumber), this.magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public float getTitleFontSize()
    {
        return 20;
    }
}