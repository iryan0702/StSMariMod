package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import mari_mod.cards.AbstractMariCard;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariFadeCardAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariFadeCardAction.class.getName());
    private AbstractMariCard fadeCard;

    public MariFadeCardAction(AbstractMariCard cardToFade) {
        this.duration = 0.0F;
        this.fadeCard = cardToFade;
    }

    public void update() {
        fadeCard.faded = true;
        fadeCard.setFadedStats();
        this.isDone = true;
    }
}
