package mari_mod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import javassist.CtBehavior;
import mari_mod.MariMod;
import mari_mod.actions.MariReducePowerIfHavePowerAction;
import mari_mod.powers.Debuff_Energy_Power;



public class MariDebuffEnergyPatch {
    @SpirePatch(
            clz = AbstractCard.class,
            method = "hasEnoughEnergy"
    )
    public static class MariCardEnergyRestrictionPatch {

        @SpireInsertPatch(
                locator = Locator.class
        )
        public static SpireReturn<Boolean> Insert(AbstractCard __instance) {
            if (AbstractDungeon.player.hasPower(Debuff_Energy_Power.POWER_ID)) {
                if(((Debuff_Energy_Power)AbstractDungeon.player.getPower(Debuff_Energy_Power.POWER_ID)).frailOnly && ((EnergyPanel.totalCount + MariMod.frailAmount) >= __instance.costForTurn)){
                    return SpireReturn.Return(true);
                }
                if(!((Debuff_Energy_Power)AbstractDungeon.player.getPower(Debuff_Energy_Power.POWER_ID)).frailOnly && ((EnergyPanel.totalCount + MariMod.vulnerableAmount + MariMod.frailAmount) >= __instance.costForTurn)){
                    return SpireReturn.Return(true);
                }
            }
            return SpireReturn.Continue();
        }

        public static class Locator extends SpireInsertLocator {
            public Locator() {
            }
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(EnergyPanel.class, "totalCount");
                int[] found = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
                return new int[]{found[0]};
            }
        }

    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "useCard",
            paramtypez = {AbstractCard.class, AbstractMonster.class, int.class}
    )
    public static class MariXCostEnergySpendPatch {

        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert(AbstractPlayer __instance, AbstractCard c, AbstractMonster monster, int energyOnUse) {

            if (c.cost == -1 && !c.ignoreEnergyOnUse && AbstractDungeon.player.hasPower(Debuff_Energy_Power.POWER_ID)) {

                if(((Debuff_Energy_Power)AbstractDungeon.player.getPower(Debuff_Energy_Power.POWER_ID)).frailOnly){
                    c.energyOnUse = EnergyPanel.totalCount + MariMod.frailAmount;
                    AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player,AbstractDungeon.player, FrailPower.POWER_ID));
                }
                if(!((Debuff_Energy_Power)AbstractDungeon.player.getPower(Debuff_Energy_Power.POWER_ID)).frailOnly){
                    c.energyOnUse = EnergyPanel.totalCount + MariMod.frailAmount + MariMod.vulnerableAmount;
                    AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player,AbstractDungeon.player, VulnerablePower.POWER_ID));
                    AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player,AbstractDungeon.player, FrailPower.POWER_ID));
                }
            }
        }

        public static class Locator extends SpireInsertLocator {
            public Locator() {
            }
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "use");
                int[] found = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
                return new int[]{found[0]};
            }
        }

    }

    @SpirePatch(
            clz = EnergyPanel.class,
            method = "useEnergy",
            paramtypez = {int.class}
    )
    public static class EnergyPanelUsePatch {
        @SpirePrefixPatch
        public static SpireReturn Prefix(int use) {
            if(!AbstractDungeon.player.hasPower(Debuff_Energy_Power.POWER_ID) || EnergyPanel.totalCount >= use){
                return SpireReturn.Continue();
            }
            int amountToCompensate = use;
            amountToCompensate -= EnergyPanel.totalCount;
            EnergyPanel.totalCount = 0;

            int frailConsumption = Math.min(MariMod.frailAmount, amountToCompensate);
            amountToCompensate -= frailConsumption;
            int vulnerableConsumption = amountToCompensate;

            if(vulnerableConsumption > 0) {
                AbstractDungeon.actionManager.addToTop(new MariReducePowerIfHavePowerAction(AbstractDungeon.player,AbstractDungeon.player, VulnerablePower.POWER_ID,vulnerableConsumption));
            }
            if(frailConsumption > 0){
                AbstractDungeon.actionManager.addToTop(new MariReducePowerIfHavePowerAction(AbstractDungeon.player,AbstractDungeon.player, FrailPower.POWER_ID,frailConsumption));

            }


            return SpireReturn.Return(null);
        }
    }
}