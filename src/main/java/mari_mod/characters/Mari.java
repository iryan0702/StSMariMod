package mari_mod.characters;

import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Alchemize;
import com.megacrit.cardcrawl.cards.green.BouncingFlask;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.events.beyond.SpireHeart;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import mari_mod.abstracts.MariOrb;
import mari_mod.relics.MariCursedDoll;
import mari_mod.screens.MariReminisceScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import mari_mod.MariMod;
import mari_mod.patches.CardColorEnum;
import mari_mod.patches.PlayerClassEnum;
import mari_mod.relics.MariTheSpark;
import mari_mod.cards.*;

import java.util.ArrayList;

public class Mari extends CustomPlayer {
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString("MariMod:Mari");

    public static final int START_HP = 72;
    public static final int CARD_DRAW = 5;
    public static final int MAX_ORBS = 0;
    public static final int ENERGY = 3;
    public static final int START_GOLD = 136;

    public static final String[] orbTextures = {
            "mari_mod/images/characters/Mari/orb/layer1.png",
            "mari_mod/images/characters/Mari/orb/layer2.png",
            "mari_mod/images/characters/Mari/orb/layer3.png",
            "mari_mod/images/characters/Mari/orb/layer4.png",
            "mari_mod/images/characters/Mari/orb/layer5.png",
            "mari_mod/images/characters/Mari/orb/layer6.png",
            "mari_mod/images/characters/Mari/orb/layer1d.png",
            "mari_mod/images/characters/Mari/orb/layer2d.png",
            "mari_mod/images/characters/Mari/orb/layer3d.png",
            "mari_mod/images/characters/Mari/orb/layer4d.png",
            "mari_mod/images/characters/Mari/orb/layer5d.png",
    };

    public Mari(String name) {
        super(name, PlayerClassEnum.MARI, new MariOrb(orbTextures, "mari_mod/images/characters/Mari/orb/vfx.png"), (String)null, null);
        this.initializeClass(
                "mari_mod/images/characters/Mari/main2.png",
                "mari_mod/images/characters/Mari/shoulderNotLit.png",
                "mari_mod/images/characters/Mari/shoulderLit.png",
                "mari_mod/images/characters/Mari/corpse.png",
                getLoadout(), 20.0f, -10.0f, 220.0f, 290.0f, new EnergyManager(ENERGY));

        if (MariMod.mariReminisceScreen == null) {
            MariMod.mariReminisceScreen = new MariReminisceScreen();
        }
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return characterStrings.NAMES[1];
    }

    @Override
    public String getLocalizedCharacterName() {
        return characterStrings.NAMES[0];
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return CardColorEnum.MARI;
    }

    @Override
    public Color getCardRenderColor() {
        return MariMod.WHITE;
    }

    @Override
    public Color getCardTrailColor() {
        return MariMod.WHITE;
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 4;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playV("MariMod:MariCharacterSelect", 1.5F);
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, true);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "MariMod:MariCharacterSelect";
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.BLUNT_HEAVY, AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.BLUNT_HEAVY};
    }

    @Override
    public String getSpireHeartText() {
        return characterStrings.TEXT[1];
    }

    @Override
    public Color getSlashAttackColor() {
        return MariMod.WHITE;
    }

    @Override
    public String getVampireText() {
        return characterStrings.TEXT[2];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new Mari(this.name);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<String>();


        retVal.add(Mari_Strike.ID);
        retVal.add(Mari_Strike.ID);
        retVal.add(Mari_Strike.ID);
        retVal.add(Mari_Strike.ID);
        retVal.add(Mari_Strike.ID);
        retVal.add(Mari_Defend.ID);
        retVal.add(Mari_Defend.ID);
        retVal.add(Mari_Defend.ID);
        retVal.add(Mari_Defend.ID);
        retVal.add(Mari_Defend.ID);
        retVal.add(Mari_Reminisce.ID);
        retVal.add(Mari_Debut.ID);



        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<String>();

        String starterRelic = getChosenRelic(MariCharacterSelectScreen.chosenRelic);

        if(starterRelic == "oh no"){
            starterRelic = Circlet.ID;
        }

        retVal.add(starterRelic);
        UnlockTracker.markRelicAsSeen(starterRelic);
        return retVal;
    }

    public static String getChosenRelic(int index){
        switch(index){
            case 0: return MariTheSpark.ID;
            case 1: return MariStageDirections.ID;
            case 2: return MariPinkHandbag.ID;
            default: return "oh no";
        }
    }

    public AbstractCard getStartCardForEvent() {
        return new Mari_Strike();
    }


    @Override
    public CharSelectInfo getLoadout() {
        CharSelectInfo a = new CharSelectInfo(
                characterStrings.NAMES[0],
                characterStrings.TEXT[0],
                START_HP,
                START_HP,
                MAX_ORBS,
                START_GOLD,
                CARD_DRAW,
                this,
                getStartingRelics(),
                getStartingDeck(),
                false);
        return a;
    }
}