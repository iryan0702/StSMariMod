package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.actions.MariPurgeSpecificCardAction;
import mari_mod.actions.MariRecallAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class Mari_Repression extends AbstractMariCard implements OnRecallCard{
    public static final Logger logger = LogManager.getLogger(Mari_Repression.class.getName());
    public static final String ID = "MariMod:Mari_Repression";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 2;
    private static final int ENERGY_GAIN = 1;
    private static final CardType TYPE = CardType.STATUS;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Mari_Repression(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, CardColor.COLORLESS, RARITY, TARGET);
        tags.add(MariCustomTags.GLARING);
        this.recallPreview = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MariRecallAction());
        addToBot(new GainEnergyAction(ENERGY_GAIN));
    }

    @Override
    public void onMoveToDiscard() {
        addToTop(new MariPurgeSpecificCardAction(this,AbstractDungeon.player.discardPile,true));
    }

    @Override
    public void onRecall() {
        ArrayList<AbstractCard> choices = new ArrayList<>();
        choices.add(new Mari_$Choose_Grief());
        choices.add(new Mari_$Choose_Pain());
        this.addToBot(new ChooseOneAction(choices));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Repression();
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upgrade() {
    }
}