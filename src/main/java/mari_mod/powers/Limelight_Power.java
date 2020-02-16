package mari_mod.powers;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.MariMod;
import mari_mod.effects.MariSpotlightEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Limelight_Power extends AbstractPower
{
    public static final String POWER_ID = "MariMod:Limelight_Power";
    private static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public Limelight_Power(AbstractCreature owner, int bufferAmt)
    {
        this.name = NAME;
        this.type = POWER_TYPE;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = bufferAmt;
        this.isTurnBased = false;
        this.updateDescription();
        MariMod.setPowerImages(this);
    }

    public void stackPower(int stackAmount)
    {
        logger.info("this stacks: " + stackAmount);
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        AbstractPlayer p = AbstractDungeon.player;
        if (isPlayer) {

            for(int i = 0; i < this.amount; i++) AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Radiance_Power(p, 1), 1));
            AbstractDungeon.effectList.add(new MariSpotlightEffect(7.0f, p.drawX + p.hb_x /*+ p.hb_w/2f*/, Settings.WIDTH / (4f) * (p.hb_w / 220f), false));

            for (AbstractCreature toApplyRadiance : AbstractDungeon.getMonsters().monsters) {
                if (!toApplyRadiance.isDead && !toApplyRadiance.halfDead) {
                    for(int i = 0; i < this.amount; i++) AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(toApplyRadiance, p, new Radiance_Power(toApplyRadiance, 1), 1));
                    AbstractDungeon.effectList.add(new MariSpotlightEffect(7.0f, toApplyRadiance.drawX + toApplyRadiance.hb_x /*+ toApplyRadiance.hb_w / 2f*/, Settings.WIDTH / (3.5f) * (toApplyRadiance.hb_w / 220f), false));
                }
            }
        }
        CardCrawlGame.sound.playV("MariMod:MariLimelight", 2.0f);
        super.atEndOfTurn(isPlayer);
    }

    @Override
    public void updateDescription() {
        if(this.amount == 1) {
            this.description = DESCRIPTION[0] + this.amount + DESCRIPTION[1];
        }else{
            this.description = DESCRIPTION[0] + this.amount + DESCRIPTION[2];
        }
    }
}
