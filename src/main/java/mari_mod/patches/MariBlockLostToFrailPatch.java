package mari_mod.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.FrailPower;
import mari_mod.MariMod;


public class MariBlockLostToFrailPatch {

    public static int blockWithFrail = 0;
    public static int blockWithoutFrail = 0;
    public static boolean noFrailMode = true;


    @SpirePatch(clz = FrailPower.class, method = "modifyBlock",
            paramtypez = {
                    float.class})
    public static class MariFrailModifierPatch {
        @SpirePrefixPatch
        public static SpireReturn<Float> Prefix(FrailPower power, float block) {
            if (power.owner.isPlayer) {
                if (noFrailMode) {
                    return SpireReturn.Return(block);
                }
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "applyPowersToBlock")
    public static class MariPrePowersToBlock {
        @SpirePrefixPatch
        public static void Prefix(AbstractCard instance) {
        }

        @SpirePostfixPatch
        public static void Postfix(AbstractCard instance) {
            if (noFrailMode) {
                noFrailMode = false;
                blockWithoutFrail = instance.block;
                ReflectionHacks.RMethod m = ReflectionHacks.privateMethod(AbstractCard.class, "applyPowersToBlock");
                m.invoke(instance);
            }else{
                noFrailMode = true;
                blockWithFrail = instance.block;
                int difference = Math.max(0, blockWithoutFrail - blockWithFrail);
//                System.out.println("card " + instance.name + " block lost: " + difference);
                BlockLostToFrailField.blockLostToFrail.set(instance, difference);
            }
        }
    }

    /*@SpirePatch(clz= AbstractCard.class, method="applyPowersToBlock")
    public static class StoreFrailBlockValues {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall a) throws CannotCompileException {
                    if (a.getClassName().equals(AbstractPower.class.getName()) && a.getMethodName().equals("modifyBlock")) {
                        a.replace(
                        "{" +
                            "if($0.ID.equals(" + FrailPower.class.getName() + ".POWER_ID)){"+
                                MariBlockLostToFrailPatch.class.getName()+".setBefore($1);" +
                                "$_ = $proceed($$);" +
                                MariBlockLostToFrailPatch.class.getName()+".setAfter($_, this);" +
                            "}else{"+
                                "$_ = $proceed($$);" +
                            "}"+
                        "}");
                    }
                }
            };
        }
    }

    public static void setBefore(float f){
        blockBeforeFrail = f;
        System.out.println("BEFORE POWER: " + f);
    }

    public static void setAfter(float f, AbstractCard c){
        blockAfterFrail = f;
        System.out.println("AFTER POWER: " + f);
        float difference = blockBeforeFrail - blockAfterFrail;

        System.out.println("BLOCK LOST: " + difference);
        BlockLostToFrailField.blockLostToFrail.set(c, difference);
    }*/


    @SpirePatch(clz = GainBlockAction.class, method = "update")
    public static class UpdateBlockLostToFrailPatch {

        @SpirePostfixPatch
        public static void Postfix(GainBlockAction instance) {

            if(instance.isDone){
                AbstractCard card = AbstractDungeon.player.cardInUse;
                System.out.println(card);
                if(card != null && instance.amount == card.block){
                    MariMod.blockLostToFrailThisCombat += MathUtils.ceil(BlockLostToFrailField.blockLostToFrail.get(card));
//                    System.out.println("total block lost: " + MariMod.blockLostToFrailThisCombat);
                }
            }
        }
    }


    @SpirePatch(
            clz = AbstractCard.class,
            method = SpirePatch.CLASS
    )
    public static class BlockLostToFrailField {
        public static SpireField<Integer> blockLostToFrail = new SpireField<>(() -> 0);
    }
}