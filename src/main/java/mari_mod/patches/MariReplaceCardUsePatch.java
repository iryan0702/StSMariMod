package mari_mod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import mari_mod.MariMod;
import mari_mod.actions.MariReducePowerIfHavePowerAction;
import mari_mod.powers.Exchange_Power;


public class MariReplaceCardUsePatch
{
    @SpirePatch(clz= AbstractPlayer.class, method="useCard")
    public static class ReplaceUseCard {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(AbstractCard.class.getName()) && m.getMethodName().equals("use")) {
                        m.replace(
                        "{" +
                            "if("+MariReplaceCardUsePatch.class.getName()+".shouldNotReplace(c) ) {" +
                                "$proceed($$);" +
                            "}else{" +
                                "" + MariReplaceCardUsePatch.class.getName()+ ".useInstead(c);" +
                            "}" +
                        "}");
                    }
                }
            };
        }
    }

    public static boolean shouldNotReplace(AbstractCard c){
        boolean replaceConditionMet = AbstractDungeon.player.hasPower(Exchange_Power.POWER_ID);
        return !replaceConditionMet || c.ignoreEnergyOnUse || c.dontTriggerOnUseCard;
    }

    public static void useInstead(AbstractCard card){
        int cost = card.costForTurn;
        if(card.cost == -1) cost = card.energyOnUse;
        if(card.freeToPlayOnce) cost = 0;

        AbstractPlayer p = AbstractDungeon.player;


        boolean shouldSpendAndGainEnergy = MariMod.calculateEffectiveCardCost(card) <= EnergyPanel.getCurrentEnergy();

        if(shouldSpendAndGainEnergy) {
            if (card.cost == -1) {
                AbstractDungeon.player.energy.use(card.energyOnUse);
            }
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(cost));
        }
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
        AbstractDungeon.actionManager.addToBottom(new MariReducePowerIfHavePowerAction(p, p, Exchange_Power.POWER_ID, 1));
    }
}