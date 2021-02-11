package mari_mod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mari_mod.powers.Practice_Outfit_Power;


public class MariDecrementBlockPowerHookPatch {

    @SpirePatch(clz = AbstractCreature.class, method = "decrementBlock", paramtypez = {DamageInfo.class, int.class})
    public static class TriggerPracticeOutfit {

        @SpirePrefixPatch
        public static void Prefix(AbstractCreature creature, DamageInfo info, int damage) {
            if(creature.currentBlock > 0 && info.type == DamageInfo.DamageType.NORMAL && damage > 0){
                AbstractPower p = AbstractDungeon.player.getPower(Practice_Outfit_Power.POWER_ID);
                if(p != null){
                    ((Practice_Outfit_Power)p).characterDamageBlocked(creature);
                }
            }
        }
    }
}
