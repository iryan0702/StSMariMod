package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.EndTurnAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.unique.RetainCardsAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import mari_mod.MariMod;
import mari_mod.actions.MariEmotionalEpisodeAction;
import mari_mod.actions.MariGainGoldPerCardInHandAction;
import mari_mod.patches.EphemeralCardPatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Filler_Episode extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Filler_Episode.class.getName());
    public static final String ID = "MariMod:Mari_Filler_Episode";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;
    private static final int RETAIN = 2;
    private static final int RETAIN_UPGRADE = 1;
    private static final int BASE_GOLD_GAIN = 5;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Mari_Filler_Episode(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseMagicNumber = RETAIN;
        this.magicNumber = this.baseMagicNumber;

        EphemeralCardPatch.EphemeralField.ephemeral.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MariGainGoldPerCardInHandAction(BASE_GOLD_GAIN));
        addToBot(new RetainCardsAction(p, this.magicNumber));
        addToBot(new PressEndTurnButtonAction());
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(RETAIN_UPGRADE);
        }
    }
}