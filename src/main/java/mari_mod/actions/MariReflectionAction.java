package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariReflectionAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariReflectionAction.class.getName());
    private static final float DURATION = 0.01F;
    public boolean upgraded;
    public boolean faded;

    public MariReflectionAction(AbstractCreature target, boolean upgraded, int fadedRadiance, boolean faded) {
        this.upgraded = upgraded;
        this.target = target;
        this.faded = faded;
        this.amount = fadedRadiance;
    }

    public void update() {

        AbstractPlayer p = AbstractDungeon.player;

        if(this.upgraded) {
            AbstractPower mp = this.target.getPower(Radiance_Power.POWER_ID);
            if (mp != null) {
                if(mp.amount >= 1) addToTop(new ApplyPowerAction(p, p, new Radiance_Power(p, this.faded ? this.amount : mp.amount), this.faded ? this.amount : mp.amount));
            }
        }

        AbstractPower pp = p.getPower(Radiance_Power.POWER_ID);
        if(pp != null){
            if(pp.amount >= 1) addToTop(new ApplyPowerAction(this.target, p, new Radiance_Power(this.target, this.faded ? this.amount : pp.amount), this.faded ? this.amount : pp.amount));
        }

//        if(this.target.hasPower(Radiance_Power.POWER_ID)) {
//
//            ArrayList<AbstractCreature> allOthers = new ArrayList<>();
//
//            if(!this.target.equals(p)){
//                allOthers.add(p);
//            }
//
//            for(AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
//                if(!monster.isDead && !monster.halfDead && !this.target.equals(monster)) {
//                    allOthers.add(monster);
//                }
//            }
//
//            int currRadiance = this.target.getPower(Radiance_Power.POWER_ID).amount;
//
//            if(currRadiance > 0 && allOthers.size() > 0) {
//                if (this.upgraded) {
//                    for(AbstractCreature other: allOthers){
//                        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(other, p, new Radiance_Power(other, currRadiance), currRadiance));
//                    }
//                } else {
//                    AbstractCreature luckySoul = allOthers.get(AbstractDungeon.cardRandomRng.random(0,allOthers.size()-1));
//                    AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(luckySoul, p, new Radiance_Power(luckySoul, currRadiance), currRadiance));
//                }
//
//            }
//        }
        this.isDone = true;
    }
}
