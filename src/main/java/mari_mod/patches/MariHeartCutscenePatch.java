package mari_mod.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.cutscenes.Cutscene;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.helpers.EventHelper;
import mari_mod.MariMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

@SpirePatch(clz= Cutscene.class,method = SpirePatch.CONSTRUCTOR,
        paramtypez = {
                AbstractPlayer.PlayerClass.class})
public class MariHeartCutscenePatch {

    @SpirePostfixPatch
    public static void Postfix(Cutscene obj, AbstractPlayer.PlayerClass chosenClass) {

            if(chosenClass.equals(PlayerClassEnum.MARI)){
                ReflectionHacks.setPrivate(obj, Cutscene.class, "isDone", true);
                ReflectionHacks.setPrivate(obj, Cutscene.class, "panels", new ArrayList());
            }

    }
}
