package mari_mod.powers;


import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.SeekAction;
import com.megacrit.cardcrawl.actions.utility.ReApplyPowersAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.MariMod;
import mari_mod.actions.MariHandGlowCheckAction;
import mari_mod.actions.MariWaitAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Administrator_Privilege_Power extends AbstractPower
{
    public static final String POWER_ID = "MariMod:Administrator_Privilege_Power";
    private static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.DESCRIPTIONS;
    private static final int GOLD_COST = 20;
    private static final int ENERGY_GAIN = 3;
    public int originalDraw;
    public int yetToDraw;
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public Administrator_Privilege_Power(AbstractCreature owner)
    {
        this.name = NAME;
        this.type = POWER_TYPE;
        this.ID = POWER_ID;
        this.owner = owner;
        this.isTurnBased = false;
        this.updateDescription();
        MariMod.setPowerImages(this);
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        this.originalDraw = AbstractDungeon.player.gameHandSize;
        AbstractDungeon.player.gameHandSize = 0;
    }

    @Override
    public void atStartOfTurn() {
        int deckSize = AbstractDungeon.player.drawPile.group.size();
        AbstractDungeon.actionManager.addToTop(new MariHandGlowCheckAction());
        if (this.originalDraw > deckSize) {
            int tmp = this.originalDraw - deckSize;
            AbstractDungeon.actionManager.addToTop(new SeekAction(tmp));
            AbstractDungeon.actionManager.addToTop(new EmptyDeckShuffleAction());
            if (deckSize != 0) {
                AbstractDungeon.actionManager.addToTop(new SeekAction(deckSize));
            }
        }else{
            AbstractDungeon.actionManager.addToTop(new SeekAction(this.originalDraw));
        }
        super.atStartOfTurn();
        AbstractDungeon.actionManager.addToTop(new MariWaitAction(1.0F));
    }

    @Override
    public void onRemove() {
        AbstractDungeon.player.gameHandSize = this.originalDraw;
        super.onRemove();
    }

    @Override
    public void onVictory() {
        AbstractDungeon.player.gameHandSize = this.originalDraw;
        super.onVictory();
    }


    public void updateDescription() {
        this.description = DESCRIPTION[0];
    }
}
