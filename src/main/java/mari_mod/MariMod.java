//TODO: CREDIT MAD SCIENTIST FR

package mari_mod;

import basemod.*;
import basemod.abstracts.CustomSavable;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.GameCursor;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.EffectHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import mari_mod.actions.MariUpdateRecentPowersAction;
import mari_mod.charSelectScreen.MariCharacterSelectScreen;
import mari_mod.events.*;
import mari_mod.powers.*;
import mari_mod.relics.*;
import mari_mod.screens.MariReminisceScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import mari_mod.characters.Mari;
import mari_mod.cards.*;
import mari_mod.patches.CardColorEnum;
import mari_mod.patches.PlayerClassEnum;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

@SpireInitializer
public class MariMod implements
        PostInitializeSubscriber,
        EditRelicsSubscriber,
        EditCharactersSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCardsSubscriber,
        OnPowersModifiedSubscriber,
        OnStartBattleSubscriber,
        AddAudioSubscriber,
        OnCardUseSubscriber,
        PreMonsterTurnSubscriber,
        PostBattleSubscriber,
        StartGameSubscriber,
        PostDungeonInitializeSubscriber,
        PostDrawSubscriber,
        PostPowerApplySubscriber,
        CustomSavable<MariSavables>
        {
    public static final String MODNAME = "Mari";
    public static final String AUTHOR = "iryan72";
    public static final String DESCRIPTION = "Adds a new character: Mari Ohara.";

    public static AbstractCard currentlyKindledCard = null;
    public static AbstractCreature currentKindleTarget = null;

    public static MariReminisceScreen mariReminisceScreen;
    // card trail effect
    public static final Color WHITE = new Color(255, 255, 255, 255);

    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());

    public static Properties disableRelicForOtherCharacters = new Properties();
    public static final String DISABLE_RELIC_SETTINGS = "Disable Mari Relics for non-Mari Characters?";
    public static boolean isRelicsForOtherCharactersDisabled = false;

    public static String characterLoadout = "DEFAULT";

    public static String removeModId(String id) {
        if (id.startsWith("MariMod:")) {
            return id.substring(id.indexOf(':') + 1);
        } else {
            logger.warn("Missing mod id on: " + id);
            return id;
        }
    }


    public static MariSavables saveableKeeper = new MariSavables();
    public static ArrayList<AbstractPower> recentPowers = new ArrayList<>();
    public static int goldSpentByMariThisCombat = 0;
    public static int timesMariSpentGoldThisCombat = 0;
    public static int timesOMGUsedThisTurn = 0;  //STAT RESET BY MARIOMG CARD
    public static boolean played0Cost = false;
    public static boolean played1Cost = false;
    public static boolean played2Cost = false;
    public static boolean played3Cost = false;
    public static boolean perfectPerformance = false;
    @Override
    public void receivePowersModified() {

        AbstractPlayer p = AbstractDungeon.player;


        AbstractDungeon.actionManager.addToTop(new MariUpdateRecentPowersAction());

        if(p.hasPower(Character_Development_Power.POWER_ID)) {
            p.getPower(Character_Development_Power.POWER_ID).onSpecificTrigger();
        }

        if(p.hasPower(Withdrawal_Power.POWER_ID)){
            AbstractPower power = p.getPower(Withdrawal_Power.POWER_ID);
            power.updateDescription();
        }

        if(p.hasPower(VulnerablePower.POWER_ID)){
            AbstractPower power = p.getPower(VulnerablePower.POWER_ID);
            vulnerableAmount = power.amount;
        }else{
            vulnerableAmount = 0;
        }

        if(p.hasPower(FrailPower.POWER_ID)){
            AbstractPower power = p.getPower(FrailPower.POWER_ID);
            frailAmount = power.amount;
        }else{
            frailAmount = 0;
        }

        p.hand.glowCheck();
    }

    @Override
    public void receivePostPowerApplySubscriber(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        if(abstractPower.ID.equals(FrailPower.POWER_ID) || abstractPower.ID.equals(VulnerablePower.POWER_ID)){
            for(AbstractCard c: AbstractDungeon.player.exhaustPile.group){
                if(c instanceof Mari_Undying_Spark){
                    ((Mari_Undying_Spark)c).returnToHand();
                }
            }
        }
    }

            @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        resetBattleStats();
        performBattleStartEvents();
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        resetBattleStats();
    }

    @Override
    public void receiveStartGame() {
        resetBattleStats();
        logger.info("START GAME RESETS");
        for(AbstractCard card: AbstractDungeon.player.masterDeck.group){
            if(card instanceof Mari_Stewshine){
                ((Mari_Stewshine)card).refreshOneTimeCardStats();
            }
        }
        if(AbstractDungeon.player.chosenClass == PlayerClassEnum.MARI){
            ReflectionHacks.setPrivate(CardCrawlGame.cursor, GameCursor.class, "img", ImageMaster.loadImage("mari_mod/images/cursor/MariCursor.png"));
        }else{
            ReflectionHacks.setPrivate(CardCrawlGame.cursor, GameCursor.class, "img", ImageMaster.loadImage("images/ui/cursors/gold2.png"));
        }
    }

    @Override
    public void receivePostDungeonInitialize() {
        resetBattleStats();
        saveableKeeper.resetStats();

        if(AbstractDungeon.player != null){
            if(!AbstractDungeon.player.chosenClass.equals(PlayerClassEnum.MARI) && isRelicsForOtherCharactersDisabled){
                //myOwnRemoveRelicFromPool(AbstractDungeon.commonRelicPool, MariFlowerRing.ID);
                //myOwnRemoveRelicFromPool(AbstractDungeon.commonRelicPool, MariOldLollipop.ID);
                //myOwnRemoveRelicFromPool(AbstractDungeon.commonRelicPool, MariDevilsCharm.ID);
                //myOwnRemoveRelicFromPool(AbstractDungeon.uncommonRelicPool, MariJuicyTangerine.ID);
                //myOwnRemoveRelicFromPool(AbstractDungeon.uncommonRelicPool, MariToySailboat.ID);
                //myOwnRemoveRelicFromPool(AbstractDungeon.uncommonRelicPool, MariMiniaturePiano.ID);
                //myOwnRemoveRelicFromPool(AbstractDungeon.rareRelicPool, MariUmeBlossom.ID);
                //myOwnRemoveRelicFromPool(AbstractDungeon.rareRelicPool, MariDolphinTrinket.ID);
                myOwnRemoveRelicFromPool(AbstractDungeon.bossRelicPool, MariFestivalBadge.ID);
            }
        }

    }

    public void myOwnRemoveRelicFromPool(ArrayList<String> pool, String name) {
        Iterator i = pool.iterator();

        while(i.hasNext()) {
            String s = (String)i.next();
            if (s.equals(name)) {
                i.remove();
                logger.info("Relic" + s + " removed from relic pool.");
            }
        }

    }

    private void resetBattleStats(){
        goldSpentByMariThisCombat = 0;
        timesMariSpentGoldThisCombat = 0;
        timesOMGUsedThisTurn = 0;
        MariMod.recentPowers.clear();
        played0Cost = false;
        played1Cost = false;
        played2Cost = false;
        played3Cost = false;
        perfectPerformance = false;
        vulnerableAmount = 0;
        frailAmount = 0;
    }

            @Override
    public void receiveAddAudio() {
        BaseMod.addAudio("MariMod:MariTheFlyingCar", "mari_mod/audio/MariTheFlyingCar.ogg");
        BaseMod.addAudio("MariMod:MariCharacterSelect", "mari_mod/audio/MariIntro.ogg");
        BaseMod.addAudio("MariMod:MariHeavyMetal", "mari_mod/audio/MariHeavyMetal.ogg");
        BaseMod.addAudio("MariMod:MariHeliIn", "mari_mod/audio/MariHeliIn.ogg");
        BaseMod.addAudio("MariMod:MariHeliMid", "mari_mod/audio/MariHeliMid.ogg");
        BaseMod.addAudio("MariMod:MariHeliOut", "mari_mod/audio/MariHeliOut.ogg");
    }

    public static int lastGoldAmountSpent = 0;

    public static void spendGold(int goldCost){ //TODO: Work on checking if goldCost > 0 if necessary
        lastGoldAmountSpent = goldCost;
        AbstractPlayer p = AbstractDungeon.player;

        p.loseGold(goldCost);
        if(p.hasPower(Gold_Loss_Start_Of_Turn_Power.POWER_ID)){
            AbstractPower power = p.getPower(Gold_Loss_Start_Of_Turn_Power.POWER_ID);
            int reduction = Math.min(power.amount, goldCost);
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(p,p,power,reduction));
            power.flashWithoutSound();
        }
        if(p.hasPower(Delicacy_Power.POWER_ID)){
            AbstractPower power = p.getPower(Delicacy_Power.POWER_ID);
            power.onSpecificTrigger();
        }
        if(p.hasPower(Flaunt_Power.POWER_ID)){
            AbstractPower power = p.getPower(Flaunt_Power.POWER_ID);
            power.onSpecificTrigger();
        }
        if(p.hasPower(Cash_Back_Power.POWER_ID)){
            AbstractPower power = p.getPower(Cash_Back_Power.POWER_ID);
            p.gainGold ((int)((float) goldCost * power.amount * 0.01F));
            power.flashWithoutSound();
        }
        if(p.hasRelic(MariTheSpark.ID) && goldCost > 0) {
            AbstractRelic relic = p.getRelic(MariTheSpark.ID);
            relic.onSpendGold();
        }
        if(goldCost > 0){
            timesMariSpentGoldThisCombat++;
        }
        goldSpentByMariThisCombat += goldCost;
        p.hand.applyPowers();
        p.hand.glowCheck();
    }



    public static void gainGold(int goldGain){
        AbstractPlayer p = AbstractDungeon.player;
        if(goldGain > 0) {
            CardCrawlGame.sound.play("GOLD_JINGLE");
            for (int i = 0; i < goldGain; i++) {
                AbstractDungeon.effectList.add(new GainPennyEffect(p, p.hb.cX, p.hb.cY - 100.0F * Settings.scale, p.hb.cX, p.hb.cY, true));
            }
            p.gainGold(goldGain);
        }
        p.hand.applyPowers();
        p.hand.glowCheck();
    }


    @Override
    public boolean receivePreMonsterTurn(AbstractMonster abstractMonster) {
        played0Cost = false;
        played1Cost = false;
        played2Cost = false;
        played3Cost = false;
        perfectPerformance = false;
        return true;
    }

            @Override
    public void receiveCardUsed(AbstractCard card) {
        int cost = card.costForTurn;
        if(card.cost == -1) cost = card.energyOnUse;
        if(card.freeToPlayOnce) cost = 0;
        if(cost == 1) played1Cost = true;
        if(cost == 2) played2Cost = true;
        if(cost == 3) played3Cost = true;
        if(played1Cost && played2Cost && played3Cost){
            perfectPerformance = true;
        }
    }

    public static int costReductionOnDraw = 0;
    public static int minimumCostAfterReductionOnDraw = 0;
    @Override
    public void receivePostDraw(AbstractCard c) {
        if(costReductionOnDraw > 0) {
            if (c.cost > minimumCostAfterReductionOnDraw) {
                c.cost = Math.max(minimumCostAfterReductionOnDraw,c.cost - costReductionOnDraw);
                c.isCostModified = true;
            }
            if (c.costForTurn > minimumCostAfterReductionOnDraw) {
                c.costForTurn = Math.max(minimumCostAfterReductionOnDraw,c.costForTurn - costReductionOnDraw);
                c.isCostModified = true;
            }
        }
    }

            public static String cardImage(String id) {
        return "mari_mod/images/cards/" + removeModId(id) + ".png";
    }
    public static String relicImage(String id) {
        return "mari_mod/images/relics/" + removeModId(id) + ".png";
    }
    public static String relicLargeImage(String id) {
        return "mari_mod/images/relics/large/" + removeModId(id) + ".png";
    }
    public static String relicOutlineImage(String id) {
        return "mari_mod/images/relics/outline/" + removeModId(id) + ".png";
    }

    public MariMod() {
        BaseMod.subscribe(this);
        receiveEditColors();

        logger.info("Adding mod settings");
        // This loads the mod settings.
        // The actual mod Button is added below in receivePostInitialize()
        disableRelicForOtherCharacters.setProperty(DISABLE_RELIC_SETTINGS, "FALSE");
        try {
            SpireConfig config = new SpireConfig("mariMod", "mariOharaConfig", disableRelicForOtherCharacters); // ...right here
            // the "fileName" parameter is the name of the file MTS will create where it will save our setting.
            config.load(); // Load the setting and set the boolean to equal it
            isRelicsForOtherCharactersDisabled = config.getBool(DISABLE_RELIC_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Done adding mod settings");
    }

    public static void initialize() {
        new MariMod();
    }

    public static Texture cardMedalDramaTexture;
    public static Texture cardMedalDramaLargeTexture;
    public static Texture cardMedalEnergyTexture;
    public static Texture cardMedalEnergyLargeTexture;
    public static ModPanel settingsPanel;
    public static ShaderProgram goldShader;
    @Override
    public void receivePostInitialize() {
        goldShader = new ShaderProgram(Gdx.files.internal("mari_mod/shaders/golden/vertexShader.vs").readString(),Gdx.files.internal("mari_mod/shaders/golden/fragShader.fs").readString());


        cardMedalDramaTexture = ImageMaster.loadImage("mari_mod/images/cardui/512/cardDramaMedal.png");
        cardMedalDramaLargeTexture = ImageMaster.loadImage("mari_mod/images/cardui/1024/cardDramaMedal.png");
        cardMedalEnergyTexture = ImageMaster.loadImage("mari_mod/images/cardui/512/cardEnergyMedal.png");
        cardMedalEnergyLargeTexture = ImageMaster.loadImage("mari_mod/images/cardui/1024/cardEnergyMedal.png");

        logger.info("initialize mod badge");
        // Mod badge

        Texture badgeTexture = new Texture("mari_mod/images/MariBadge.png");
        ModPanel settingsPanel = new ModPanel();
        ModLabeledToggleButton enableRelicsForOthersButton = new ModLabeledToggleButton(DISABLE_RELIC_SETTINGS,
            350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
            isRelicsForOtherCharactersDisabled, // Boolean it uses
            settingsPanel, // The mod panel in which this button will be in
            (label) -> {}, // thing??????? idk
            (button) -> { // The actual button:

            isRelicsForOtherCharactersDisabled = button.enabled; // The boolean true/false will be whether the button is enabled or not
            try {
                // And based on that boolean, set the settings and save them
                SpireConfig config = new SpireConfig("mariMod", "mariOharaConfig", disableRelicForOtherCharacters);
                config.setBool(DISABLE_RELIC_SETTINGS, isRelicsForOtherCharactersDisabled);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        settingsPanel.addUIElement(enableRelicsForOthersButton);

        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
        logger.info("done with mod badge");

        BaseMod.addSaveField("saveableKeeper", this);

        BaseMod.addEvent(MariFallingEvent.ID, MariFallingEvent.class, TheBeyond.ID);
        BaseMod.addEvent(MariShiningLightEvent.ID, MariShiningLightEvent.class, Exordium.ID);
        BaseMod.addEvent(MariSssserpent.ID, MariSssserpent.class, Exordium.ID);
        BaseMod.addEvent(MariMaskedBandits.ID, MariMaskedBandits.class, TheCity.ID);
        /*for(int i = 0; i < 100; i++) {
            BaseMod.addEvent(AllMariModEvents.ID + i, AllMariModEvents.class);
        }*/
    }

    public void receiveEditColors() {
        logger.info("begin editing colors");

        BaseMod.addColor(
                CardColorEnum.MARI,
                Color.RED, Color.ORANGE, Color.YELLOW, new Color(0.567F,0.286F,0.604F,1.0F), Color.BLUE, Color.PURPLE, Color.BROWN,
                "mari_mod/images/cardui/512/bg_attack_gold.png",
                "mari_mod/images/cardui/512/bg_skill_gold.png",
                "mari_mod/images/cardui/512/bg_power_gold.png",
                "mari_mod/images/cardui/512/card_gold_orb.png",
                "mari_mod/images/cardui/1024/bg_attack_gold.png",
                "mari_mod/images/cardui/1024/bg_skill_gold.png",
                "mari_mod/images/cardui/1024/bg_power_gold.png",
                "mari_mod/images/cardui/1024/card_gold_orb.png",
                "mari_mod/images/cardui/card_small_orb.png");


        logger.info("done editing colors");
    }

    @Override
    public void receiveEditCharacters() {
        logger.info("begin editing characters");

        BaseMod.addCharacter(
                new Mari(CardCrawlGame.playerName),
                "mari_mod/images/charSelect/MariButton.png",
                "mari_mod/images/charSelect/MariPortraitBg.png",
                PlayerClassEnum.MARI);

        logger.info("done editing characters");
        receiveEditPotions();
    }

    @Override
    public void receiveEditRelics() {
        logger.info("begin editing relics");
        BaseMod.addRelicToCustomPool(new MariTheSpark(), CardColorEnum.MARI);
        BaseMod.addRelicToCustomPool(new MariDiploma(), CardColorEnum.MARI);
        BaseMod.addRelicToCustomPool(new MariStewshine(), CardColorEnum.MARI);

        BaseMod.addRelicToCustomPool(new MariJarOfLight(), CardColorEnum.MARI);
        //BaseMod.addRelicToCustomPool(new MariCursedDoll(), CardColorEnum.MARI);
        BaseMod.addRelic(new MariOldLollipop(), RelicType.SHARED);
        BaseMod.addRelic(new MariFlowerRing(), RelicType.SHARED);
        BaseMod.addRelic(new MariDevilsCharm(), RelicType.SHARED);

        BaseMod.addRelic(new MariJuicyTangerine(), RelicType.SHARED);
        BaseMod.addRelic(new MariMiniaturePiano(), RelicType.SHARED);
        BaseMod.addRelic(new MariToySailboat(), RelicType.SHARED);

        BaseMod.addRelic(new MariDolphinTrinket(), RelicType.SHARED);
        BaseMod.addRelic(new MariUmeBlossom(), RelicType.SHARED);

        BaseMod.addRelic(new MariFestivalBadge(), RelicType.SHARED);
        logger.info("end editing relics");
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addDynamicVariable(new MariGoldCostKey());
        BaseMod.addDynamicVariable(new MariRadianceKey());
        logger.info("begin editing cards");
        try {
            //Attacks BASE
            BaseMod.addCard(new Mari_Strike());
            BaseMod.addCard(new Mari_Defend());
            BaseMod.addCard(new Mari_Debut());
            BaseMod.addCard(new Mari_Glow());
            BaseMod.addCard(new Mari_Glimmer());

            BaseMod.addCard(new Mari_Shine());
            BaseMod.addCard(new Mari_Sparkle());
            BaseMod.addCard(new Mari_SHINY());
            BaseMod.addCard(new Mari_Self_Care());
            BaseMod.addCard(new Mari_Awe());


            BaseMod.addCard(new Mari_Research());
            BaseMod.addCard(new Mari_Shining_Tornado());
            BaseMod.addCard(new Mari_Choreography());
            BaseMod.addCard(new Mari_Flawless_Form());
            BaseMod.addCard(new Mari_Mic_Check());

            BaseMod.addCard(new Mari_Lets_Go());
            BaseMod.addCard(new Mari_The_FLYING_CAR());
            BaseMod.addCard(new Mari_The_MANSION());
            BaseMod.addCard(new Mari_The_GOLDEN_STATUE());
            BaseMod.addCard(new Mari_The_HELICOPTER());


            BaseMod.addCard(new Mari_Well_Kept_Secret());
            BaseMod.addCard(new Mari_Tough_Front());
            BaseMod.addCard(new Mari_Lock_On());
            BaseMod.addCard(new Mari_Character_Development());
            //BaseMod.addCard(new Mari_Desserts());
            BaseMod.addCard(new Mari_Excitement());

            BaseMod.addCard(new Mari_Reckless_Driving());
            BaseMod.addCard(new Mari_Heavy_Price());
            BaseMod.addCard(new Mari_zzz());
            BaseMod.addCard(new Mari_Undying_Spark());
            BaseMod.addCard(new Mari_Limelight());


            BaseMod.addCard(new Mari_Flaunt());
            BaseMod.addCard(new Mari_Cash_Back());
            BaseMod.addCard(new Mari_Golden_Glint());
            //BaseMod.addCard(new Mari_Idolize());
            BaseMod.addCard(new Mari_Perfect_Performance());

            BaseMod.addCard(new Mari_Heavy_Metal());
            BaseMod.addCard(new Mari_Boundless_Energy());
            BaseMod.addCard(new Mari_Practice_Outfit());
            BaseMod.addCard(new Mari_Shut_Up());
            BaseMod.addCard(new Mari_Collect_Thoughts());


            BaseMod.addCard(new Mari_Administrator_Privilege());
            BaseMod.addCard(new Mari_Approved());
            BaseMod.addCard(new Mari_All_Or_Nothing());
            BaseMod.addCard(new Mari_The_Planisphere());
            BaseMod.addCard(new Mari_Expenses());

            BaseMod.addCard(new Mari_Grand_Scheme());
            BaseMod.addCard(new Mari_Higher_Ups());
            BaseMod.addCard(new Mari_Supervision());
            BaseMod.addCard(new Mari_Expensive_Tastes());
            BaseMod.addCard(new Mari_Kyu());


            BaseMod.addCard(new Mari_Devilish_Assistant());
            BaseMod.addCard(new Mari_Delicacy());
            BaseMod.addCard(new Mari_Wishing_Fountain());
            BaseMod.addCard(new Mari_Reminisce());
            BaseMod.addCard(new Mari_From_Zero());

            BaseMod.addCard(new Mari_Pretty_Bomber_Head());
            BaseMod.addCard(new Mari_Tea_Time());
            BaseMod.addCard(new Mari_Overexertion());
            BaseMod.addCard(new Mari_Slap());
            BaseMod.addCard(new Mari_No_Problem());

            BaseMod.addCard(new Mari_Defiance());
            BaseMod.addCard(new Mari_Fire_Strike());
            BaseMod.addCard(new Mari_Oh_My_God());
            BaseMod.addCard(new Mari_Old_Costume());
            BaseMod.addCard(new Mari_Pent_Up_Emotions());

            BaseMod.addCard(new Mari_Hug());
            BaseMod.addCard(new Mari_The_Beach_Episode());
            BaseMod.addCard(new Mari_Its_Joke());
            BaseMod.addCard(new Mari_Second_Season());
            BaseMod.addCard(new Mari_Dolphin_Strike());


            BaseMod.addCard(new Mari_Closure());
            BaseMod.addCard(new Mari_The_Light_We_Chase());
            BaseMod.addCard(new Mari_Withdrawal());
            BaseMod.addCard(new Mari_Fragile_Hope());
            BaseMod.addCard(new Mari_Reflection());

            BaseMod.addCard(new Mari_Lie_In_Wait());
            BaseMod.addCard(new Mari_Emotional_Episode());
            BaseMod.addCard(new Mari_Unveiled_Secret());
            BaseMod.addCard(new Mari_Reckless_Spending());

            /*
            //ATTACKS COMMON
            //ATTACKS UNCOMMON
            //ATTACKS RARE


//BaseMod.addCard(new Mari_Formation_Book());




            //Skills BASE


            BaseMod.addCard(new Mari_Emotional_Support());

            BaseMod.addCard(new Mari_Stewshine());

            //BaseMod.addCard(new Mari_Indulgence());

            //Skills COMMON

            //Skills UNCOMMON

            //Powers UNCOMMON
            //Powers RARE
            */

            BaseMod.addCard(new Mari_Stewshine());

        } catch (Exception e) {
            logger.error("Error while adding cards",e);
        }
        logger.info("done editing cards");
    }

    public void receiveEditPotions() {
        logger.info("begin editing potions");
        //BaseMod.addPotion(FuelPotion.class, new Color(0.3f,0.3f,0.3f,1.0f), new Color(0.1f,0.1f,0.1f,1.0f), new Color(0.5f,0.5f,0.5f,1.0f), FuelPotion.POTION_ID, PlayerClassEnum.MAD_SCIENTIST);
        logger.info("end editing potions");
    }

    @Override
    public void receiveEditKeywords() {
        logger.info("begin editing keywords");
        // Note: KeywordStrings is a horrible hardcoded class, we can't use it

        String language = getLanguage();
        Type typeToken = new TypeToken<Map<String, Keyword>>(){}.getType();
        Gson gson = new Gson();
        String strings = Gdx.files.internal("mari_mod/localization/" + language + "/mari-keywords.json").readString(String.valueOf(StandardCharsets.UTF_8));

        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(strings, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (com.evacipated.cardcrawl.mod.stslib.Keyword keyword : keywords) {
                BaseMod.addKeyword(keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                logger.info("adding keyword: " + keyword.PROPER_NAME);
            }
        }

        /*
        Map<String,Keyword> keywords = (Map<String,Keyword>)gson.fromJson(strings, typeToken);
        for (Keyword kw : keywords.values()) {
            BaseMod.addKeyword(kw.NAMES, kw.DESCRIPTION);
        }
        logger.info("done editing keywords");*/
    }

    public String getLanguage(){
        switch (Settings.language) {
            case ZHS:
                return "zhs";
            default:
                return "eng";
        }
    }

    @Override
    public void receiveEditStrings() {
        logger.info("begin editing strings");
        String language = getLanguage();
        BaseMod.loadCustomStrings(CardStrings.class, loadJson("mari_mod/localization/" + language + "/mari-cards.json"));
        BaseMod.loadCustomStrings(RelicStrings.class, loadJson("mari_mod/localization/" + language + "/mari-relics.json"));
        BaseMod.loadCustomStrings(PowerStrings.class, loadJson("mari_mod/localization/" + language + "/mari-powers.json"));
        //BaseMod.loadCustomStrings(PotionStrings.class, loadJson("mari_mod/localization/" + language + "/mari-potions.json"));
        BaseMod.loadCustomStrings(CharacterStrings.class, loadJson("mari_mod/localization/" + language + "/mari-characters.json"));
        BaseMod.loadCustomStrings(EventStrings.class, loadJson("mari_mod/localization/" + language + "/mari-events.json"));
        logger.info("done editing strings");
    }


    @Override
    public MariSavables onSave() {
        return saveableKeeper;
    }

    @Override
    public void onLoad(MariSavables saved) {
        logger.info("LOADING VALUES!!!");
        if(saved != null) {
            saveableKeeper.copyStats(saved);
        }else{
            saveableKeeper = new MariSavables();
        }
    }

    private static String loadJson(String jsonPath) {
        return Gdx.files.internal(jsonPath).readString(String.valueOf(StandardCharsets.UTF_8));
    }

    public static void setPowerImages(AbstractPower power, String powerID){
        powerID = powerID.replace("MariMod:","");
        power.region128 = new TextureAtlas.AtlasRegion(new Texture("mari_mod/images/powers/128/" +(powerID)+".png"), 0, 0, 128, 128);
        power.region48 = new TextureAtlas.AtlasRegion(new Texture("mari_mod/images/powers/48/" +(powerID)+".png"), 0, 0, 48, 48);
    }

    public static void setPowerImages(AbstractPower power){
        setPowerImages(power, removeModId(power.ID));
    }

    public static MariCharacterSelectScreen selectScreen = new MariCharacterSelectScreen();

    public static void renderCharacterSelectScreenElements(SpriteBatch sb){
        for(CharacterOption c: CardCrawlGame.mainMenuScreen.charSelectScreen.options) {
            if(c.selected && c.c.chosenClass.equals(PlayerClassEnum.MARI)) selectScreen.render(sb);
        }
    }

    public static void updateCharacterSelectScreenElements(){
        for(CharacterOption c: CardCrawlGame.mainMenuScreen.charSelectScreen.options) {
            if(c.selected && c.c.chosenClass.equals(PlayerClassEnum.MARI)) selectScreen.update();
        }
    }

    public void performBattleStartEvents(){
        //HIRED GUN
        int curX = AbstractDungeon.currMapNode.x;
        int curY = AbstractDungeon.currMapNode.y;
        MariSavables save = saveableKeeper;
        //logger.info("CURRENT X = " + curX);
        //logger.info("CURRENT Y = " + curY);
        //logger.info("CHECKING HIRED GUN X = " + save.hiredGunX);
        //logger.info("CHECKING HIRED GUN Y = " + save.hiredGunY);
        if(saveableKeeper.hiredGunHired && curX == save.hiredGunX && curY == save.hiredGunY){
            //logger.info("SUCCESS: HIRED GUN MURDER");
            int[] tmp = new int[AbstractDungeon.getCurrRoom().monsters.monsters.size()];
            for(int i = 0; i < tmp.length; i++){
                tmp[i] = 999;
            }
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(AbstractDungeon.player, tmp, DamageInfo.DamageType.HP_LOSS, AbstractGameAction.AttackEffect.SLASH_HEAVY));
        }
    }

}