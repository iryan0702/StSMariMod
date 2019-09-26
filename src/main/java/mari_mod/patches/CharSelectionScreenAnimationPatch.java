/*
    TODO: CREDIT NEW LINE PATCH CREDITED TO gygrazok's Witch Mod
 */

package mari_mod.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.ReflectionHelper;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import mari_mod.MariMod;

public class CharSelectionScreenAnimationPatch {

  @SpirePatch(clz = CharacterSelectScreen.class, method = "render", paramtypez = {SpriteBatch.class})
  public static class CharSelectRenderPatch {
    @SpireInsertPatch(locator = Locator.class)
    public static void insert(CharacterSelectScreen __instance, SpriteBatch sb) {
      MariMod.renderCharacterSelectScreenElements(sb);
    }

    private static class Locator extends SpireInsertLocator {

      public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
        Matcher finalMatcher = new Matcher.FieldAccessMatcher(CharacterSelectScreen.class, "cancelButton");
        return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[0]};
      }

    }
  }

  @SpirePatch(clz = CharacterSelectScreen.class, method = "update")
  public static class CharSelectUpdatePatch {

    @SpirePostfixPatch
    public static void Prefix(CharacterSelectScreen obj) {//, AbstractCard card

      MariMod.updateCharacterSelectScreenElements();

    }
  }
}