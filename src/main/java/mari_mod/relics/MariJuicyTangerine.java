package mari_mod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import mari_mod.MariMod;
import mari_mod.patches.PlayerClassEnum;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariJuicyTangerine extends AbstractMariRelic
{
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    public static final String ID = "MariMod:MariJuicyTangerine";
    public int radianceValue;
    public String radianceString;
    public MariJuicyTangerine()
    {
        super(ID, RelicTier.SPECIAL, LandingSound.CLINK);

        radianceValue = (AbstractDungeon.player != null && AbstractDungeon.player.chosenClass.equals(PlayerClassEnum.MARI))? 1:3;
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new Radiance_Power(p, radianceValue), radianceValue));
        for(AbstractMonster m: AbstractDungeon.getCurrRoom().monsters.monsters){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,p,new Radiance_Power(m, radianceValue), radianceValue));
        }
    }

    public String getUpdatedDescription()
    {
        radianceValue = (AbstractDungeon.player != null && AbstractDungeon.player.chosenClass.equals(PlayerClassEnum.MARI))? 1:3;
        if(AbstractDungeon.player == null) {
            radianceString = DESCRIPTIONS[2];
        }else{
            radianceString = "" + radianceValue;
        }

        return this.DESCRIPTIONS[0] + radianceString + this.DESCRIPTIONS[1];
    }

    public AbstractRelic makeCopy()
    {
        return new MariJuicyTangerine();
    }
}
