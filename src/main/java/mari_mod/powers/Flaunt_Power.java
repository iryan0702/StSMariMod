package mari_mod.powers;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FlickCoinEffect;
import mari_mod.MariMod;
import mari_mod.actions.MariDelayedDamageRandomEnemyActionAction;
import mari_mod.actions.MariWaitAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;


public class Flaunt_Power extends TwoAmountPowerByKiooehtButIJustChangedItABitSoItShowsZeroAndIsADifferentColor
{
    public static final String POWER_ID = "MariMod:Flaunt_Power";
    private static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public Flaunt_Power(AbstractCreature owner, int perTurn)
    {
        this.name = NAME;
        this.type = POWER_TYPE;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = perTurn;
        this.amount2 = perTurn;
        this.isTurnBased = false;
        this.canGoNegative = true;
        this.updateDescription();
        MariMod.setPowerImages(this);
    }

    public void stackPower(int stackAmount)
    {
        logger.info("this stacks: " + stackAmount);
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if(amount2 < 0) amount2 = 0;
        this.amount2 += stackAmount;
    }

    @Override
    public void atStartOfTurn() {
        this.amount2 = this.amount;
        super.atStartOfTurn();
    }
    @Override
    public void onSpecificTrigger() {
        int goldSpent = MariMod.lastGoldAmountSpent;
        AbstractPlayer p = AbstractDungeon.player;
        if(this.amount2 > 0) {
            for(AbstractMonster m: AbstractDungeon.getCurrRoom().monsters.monsters){
                if(!m.halfDead && !m.isDead){
                    AbstractDungeon.effectList.add(new FlickCoinEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY));
                }
            }
            this.addToBot(new MariWaitAction(0.3F));
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(goldSpent, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
            this.flash();
        }
        amount2--;
        if(amount2 == 0) amount2 --;
    }

    public void updateDescription() {
        if(this.amount == 1){
            this.description = DESCRIPTION[0];
        }else{
            this.description = DESCRIPTION[1] + this.amount + DESCRIPTION[2];
        }
    }
}
