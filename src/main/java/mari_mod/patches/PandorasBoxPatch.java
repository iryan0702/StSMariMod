package mari_mod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.PandorasBox;
import mari_mod.cards.Mari_Strike;

import java.lang.reflect.Field;
import java.util.Iterator;


@SpirePatch(clz=com.megacrit.cardcrawl.relics.PandorasBox.class, method="onEquip")
public class PandorasBoxPatch {
    @SpireInsertPatch(rloc=12)
    public static void Insert(Object __obj_instance) {
        Field count;
        try {
            PandorasBox box = (PandorasBox) __obj_instance;
            count = box.getClass().getDeclaredField("count");
            count.setAccessible(true);

            for (Iterator<AbstractCard> i = AbstractDungeon.player.masterDeck.group.iterator(); i.hasNext();) {
                AbstractCard e = (AbstractCard) i.next();
                if (e instanceof Mari_Strike){
                    i.remove();
                    count.set(box, ((Integer) count.get(box)) + 1);
                }
            }
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
