package mari_mod.relics;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import mari_mod.MariMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariFestivalBadge extends AbstractMariRelic
{
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public static final String ID = "MariMod:MariFestivalBadge";
    public MariFestivalBadge()
    {
        super(ID, RelicTier.BOSS, LandingSound.FLAT);
    }


    @Override
    public void onEquip() {
        for(AbstractCard cardToCheck: AbstractDungeon.player.masterDeck.group){
            for(AbstractCard otherCardToCheck: AbstractDungeon.player.masterDeck.group){
                if(cardToCheck.cardID.equals(otherCardToCheck.cardID) && !cardToCheck.uuid.equals(otherCardToCheck.uuid) && !cardToCheck.upgraded && !otherCardToCheck.upgraded){
                    otherCardToCheck.upgrade();
                    cardToCheck.upgrade();

                    float x = MathUtils.random(0.1F, 0.9F) * (float) Settings.WIDTH;
                    float y = MathUtils.random(0.2F, 0.8F) * (float)Settings.HEIGHT;
                    AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(cardToCheck.makeStatEquivalentCopy(), x, y));
                    AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(x, y));


                    x = MathUtils.random(0.1F, 0.9F) * (float) Settings.WIDTH;
                    y = MathUtils.random(0.2F, 0.8F) * (float)Settings.HEIGHT;
                    AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(otherCardToCheck.makeStatEquivalentCopy(), x, y));
                    AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(x, y));
                }
            }
        }
    }

    @Override
    public void onObtainCard(AbstractCard c) {
        AbstractCard matchingCard = null;
        for(AbstractCard card: AbstractDungeon.player.masterDeck.group){
            if(card.cardID.equals(c.cardID) && !card.uuid.equals(c.uuid) && !c.upgraded && !card.upgraded){
                matchingCard = card;
                break;
            }
        }

        if(matchingCard != null){
            c.upgrade();
            matchingCard.upgrade();
            this.flash();
        }
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy()
    {
        return new MariFestivalBadge();
    }
}
