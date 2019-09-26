package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import mari_mod.cards.Mari_All_Or_Nothing;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariDowngradeCardAction extends AbstractGameAction { //ONLY EQUIPPED FOR ALL OR NOTHING
    private DamageInfo info;
    public static final Logger logger = LogManager.getLogger(MariDowngradeCardAction.class.getName());
    public Mari_All_Or_Nothing card;
    public String newName;
    public String newDesc;
    public boolean exhaust;

    public MariDowngradeCardAction(Mari_All_Or_Nothing card, String newName, String newDesc, boolean exhaust) {
        this.actionType = ActionType.SPECIAL;
        this.card = card;
        this.newName = newName;
        this.newDesc = newDesc;
        this.exhaust = exhaust;
    }

    public void update() {
        card.exhaust = true;

        --card.timesUpgraded;
        card.upgraded = false;
        card.name = newName;
        card.publicInitializeTitle();

        card.rawDescription = newDesc;
        card.initializeDescription();
        this.isDone = true;
    }
}
