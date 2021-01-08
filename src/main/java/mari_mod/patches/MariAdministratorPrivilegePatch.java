package mari_mod.patches;

public class MariAdministratorPrivilegePatch
{
//    @SpirePatch(clz= AbstractPlayer.class, method="useCard")
//    public static class ReplaceUseCard {
//        public static ExprEditor Instrument() {
//            return new ExprEditor() {
//                @Override
//                public void edit(MethodCall m) throws CannotCompileException {
//                    if (m.getClassName().equals(AbstractCard.class.getName()) && m.getMethodName().equals("use")) {
//                        m.replace(
//                        "{" +
//                            "if("+ MariAdministratorPrivilegePatch.class.getName()+".shouldNotReplace(c) ) {" +
//                                "$proceed($$);" +
//                            "}else{" +
//                                "" + MariAdministratorPrivilegePatch.class.getName()+ ".useInstead(c);" +
//                            "}" +
//                        "}");
//                    }
//                }
//            };
//        }
//    }
//
//    public static boolean shouldNotReplace(AbstractCard c){
//        if(MariMod.calculateEffectiveCardCost(c) <= EnergyPanel.getCurrentEnergy()) return true;
//        AbstractPower power = AbstractDungeon.player.getPower(Administrator_Privilege_Power.POWER_ID);
//        return power == null || power.amount <= 0;
//    }
//
//    public static void useInstead(AbstractCard card){
//        int cost = MariMod.calculateEffectiveCardCost(card);
//
//        AbstractPlayer p = AbstractDungeon.player;
//        for(AbstractMonster m: AbstractDungeon.getCurrRoom().monsters.monsters) {
//            if(!(m.isDead || m.halfDead)){
//                AbstractDungeon.actionManager.addToBottom(new LoseHPAction(m, p, cost * 2, AbstractGameAction.AttackEffect.FIRE));
//                AbstractDungeon.actionManager.addToBottom(new MariReducePowerIfHavePowerAction(p, p, Administrator_Privilege_Power.POWER_ID, 1));
//            }
//        }
//    }
//
//
//    @SpirePatch(clz= AbstractPlayer.class, method="useCard")
//    public static class StopEnergySpend {
//        public static ExprEditor Instrument() {
//            return new ExprEditor() {
//                @Override
//                public void edit(MethodCall m) throws CannotCompileException {
//                    if (m.getClassName().equals(EnergyManager.class.getName()) && m.getMethodName().equals("use")) {
//                        m.replace(
//                                "{" +
//                                        "if("+ MariAdministratorPrivilegePatch.class.getName()+".shouldSpend(c) ) {" +
//                                        "$proceed($$);" +
//                                        "}" +
//                                        "}");
//                    }
//                }
//            };
//        }
//    }
//
//    public static boolean shouldSpend(AbstractCard c){
//        if(MariMod.calculateEffectiveCardCost(c) <= EnergyPanel.getCurrentEnergy()) return true;
//        AbstractPower power = AbstractDungeon.player.getPower(Administrator_Privilege_Power.POWER_ID);
//        return power == null || power.amount <= 0;
//    }
//
//    @SpirePatch(
//            clz = AbstractCard.class,
//            method = "hasEnoughEnergy"
//    )
//    public static class MariCardEnergyRestrictionPatch {
//
//        @SpireInsertPatch(
//                locator = Locator.class
//        )
//        public static SpireReturn<Boolean> Insert(AbstractCard __instance) {
//            AbstractPower power = AbstractDungeon.player.getPower(Administrator_Privilege_Power.POWER_ID);
//            if (power != null && power.amount > 0) {
//                return SpireReturn.Return(true);
//            }
//            return SpireReturn.Continue();
//        }
//
//        public static class Locator extends SpireInsertLocator {
//            public Locator() {
//            }
//            @Override
//            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
//                Matcher finalMatcher = new Matcher.FieldAccessMatcher(EnergyPanel.class, "totalCount");
//                int[] found = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
//                return new int[]{found[0]};
//            }
//        }
//
//    }
}