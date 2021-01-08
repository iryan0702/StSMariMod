package mari_mod.powers;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.MariMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Administrator_Privilege_Power extends AbstractPower
{
    public static final String POWER_ID = "MariMod:Administrator_Privilege_Power";
    private static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.DESCRIPTIONS;
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

    public void atEndOfTurn(boolean isPlayer) {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            int totalCost = 0;
            for(AbstractCard c: AbstractDungeon.player.hand.group){
                int cost = MariMod.calculateEffectiveCardCostNotOnPlay(c);
                if(cost > 0) totalCost += cost;
                logger.info("Card name: " + c.name + " cost: " + cost + " total cost: " + totalCost);
            }
            if(totalCost > 0) {
                int[] damages = new int[AbstractDungeon.getMonsters().monsters.size()];
                for(int i = 0; i < damages.length; ++i) {
                    damages[i] = totalCost;
                }
                this.flash();
                this.addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, damages, DamageInfo.DamageType.HP_LOSS, AbstractGameAction.AttackEffect.FIRE));
            }
        }

    }

    public void updateDescription() {
        this.description = DESCRIPTION[0];
    }
}
