package mari_mod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.BlurPower;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;


public class MariMonsterBlurPatch
{
    @SpirePatch(clz= MonsterGroup.class, method="applyPreTurnLogic")
    public static class ReplaceLoseBlock {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(AbstractMonster.class.getName()) && m.getMethodName().equals("loseBlock")) {
                        m.replace(
                        "{" +
                            "if("+ MariMonsterBlurPatch.class.getName()+".shouldNotReplace(m) ) {" +
                                "$proceed($$);" +
                            "}" +
                        "}");
                    }
                }
            };
        }
    }

    public static boolean shouldNotReplace(AbstractMonster m){
        return !m.hasPower(BlurPower.POWER_ID);
    }
}