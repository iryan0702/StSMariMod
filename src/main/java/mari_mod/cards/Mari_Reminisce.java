package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.actions.IncreaseCostAction;
import mari_mod.actions.MariReminisceAction;
import mari_mod.screens.MariReminisceScreen;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static basemod.BaseMod.MAX_HAND_SIZE;
import static mari_mod.MariMod.mariReminisceScreen;

public class Mari_Reminisce extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Reminisce.class.getName());
    public static final String ID = "MariMod:Mari_Reminisce";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 0;
    private static final int CHOICES = 3;
    private static final int CHOICES_UPGRADE = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.NONE;
    public boolean canPullQuotations = true;
    public boolean canPullRadiance = true;
    public boolean canPullSpend = true;
    public boolean canPullExhaust = false;

    public Mari_Reminisce(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseMagicNumber = CHOICES;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new MariReminisceAction(this,canPullRadiance,canPullSpend,canPullExhaust,canPullQuotations, this.uuid));

        int optionsLeft = 0;
        if(this.upgraded && canPullExhaust){
            optionsLeft++;
        }
        if(canPullRadiance) optionsLeft++;
        if(canPullSpend) optionsLeft++;
        if(canPullQuotations) optionsLeft++;

        if(optionsLeft <= 1){
            this.exhaust = true;
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Reminisce();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.upgradeMagicNumber(CHOICES_UPGRADE);
            this.canPullExhaust = true;
        }
    }
}