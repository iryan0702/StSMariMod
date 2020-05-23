package mari_mod.charSelectScreen;

import basemod.BaseMod;
import basemod.ModPanel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import mari_mod.MariMod;
import mari_mod.cards.MariCustomTags;
import mari_mod.characters.Mari;
import mari_mod.patches.PlayerClassEnum;

import java.util.ArrayList;
import java.util.Iterator;

public class MariCharacterSelectScreen {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private static final float DEST_INFO_X;
    private static final float START_INFO_X;
    private static final float START_INFO_Y;
    private static final float NAME_OFFSET_Y;

    //INFOS

    public String name;
    private String hp;
    private int gold;
    private String flavorText;

    public float infoX;
    public float infoY;
    public boolean wasJustSelected;

    //HITBOX

    public static Hitbox relicUpHb;
    public static Hitbox relicDownHb;

    //SELECT INFO

    public static int chosenRelic;
    public static int chosenClass;
    public static final int OPTIONS = 3;
    public static ModPanel settingsPanel;
    //TEXTURES

    public Texture backgroundTexture;
    public TextureAtlas.AtlasRegion background;
    public Texture backgroundGlowTexture;
    public TextureAtlas.AtlasRegion backgroundGlow;
    public Texture foregroundTexture;
    public TextureAtlas.AtlasRegion foreground;
    public Texture particleTexture;
    public TextureAtlas.AtlasRegion particleImg;
    public Texture upArrowTexture;
    public TextureAtlas.AtlasRegion upArrowImg;
    public Texture downArrowTexture;
    public TextureAtlas.AtlasRegion downArrowImg;
    public Texture settingsTexture;
    public TextureAtlas.AtlasRegion settingsImg;

    public Texture testGridTexture;
    public TextureAtlas.AtlasRegion testGridImg;

    //COLORS

    public Color backgroundColor;

    public Color foregroundColor;
    public float foregroundColorTimer;

    public Color foregroundGlowColor;
    public float foregroundGlowColorTimer;

    //PARTICLES

    public ArrayList<GlowyParticle> particles;
    public float particleTimer;

    public static float particleTimeDilation = 80.0f;

    public MariCharacterSelectScreen(){

        chosenRelic = 0;

        relicUpHb = new Hitbox(DEST_INFO_X + (-10.0F + 60.0f) * Settings.scale - 32.0F, START_INFO_Y + (40.0F + 120.0F - 160f) * Settings.scale, 55.0F * Settings.scale, 55.0F * Settings.scale);
        relicDownHb = new Hitbox(DEST_INFO_X + (-10.0F + 60.0f) * Settings.scale - 32.0F, START_INFO_Y + (40.0F - 160f) * Settings.scale,55.0F * Settings.scale, 55.0F * Settings.scale);

        this.infoX = START_INFO_X;
        this.infoY = START_INFO_Y;

        name = Mari.characterStrings.NAMES[0];
        hp = Mari.START_HP + "/" + Mari.START_HP;
        gold = Mari.START_GOLD;
        flavorText = Mari.characterStrings.TEXT[0];

        settingsPanel = MariMod.settingsPanel;
        wasJustSelected = false;

        foregroundColorTimer = 0;
        foregroundGlowColorTimer = 0;
        particleTimer = 0;
        particles = new ArrayList<>();
    }

    //RELICS, CLASS IDENTIFIERS, and SKIN
    //0 - The Spark, None, School Uniform
    //1 – Stage Directions, Drama, Mijuku Dreamer
    //2 - Pink Handbag, Energy, Casual Outfit

    public AbstractCard.CardTags getSelectedClass(){
        switch (chosenClass){
            case 0:
                return null;
            case 1:
                return MariCustomTags.DRAMA;
            case 2:
                return MariCustomTags.ENERGY;
            default:
                return null;
        }
    }

    public void update(){

        relicUpHb.update();
        relicDownHb.update();
        if (InputHelper.justClickedLeft) {
            if (relicUpHb.hovered) {
                relicUpHb.clickStarted = true;
            } else if (relicDownHb.hovered) {
                relicDownHb.clickStarted = true;
            }
        }
        if(relicUpHb.clicked){
            chosenRelic--;
            relicUpHb.clicked = false;
            if(chosenRelic < 0) chosenRelic = OPTIONS-1;
            chosenClass = chosenRelic;
        }
        if(relicDownHb.clicked){
            chosenRelic++;
            relicDownHb.clicked = false;
            if(chosenRelic >= OPTIONS) chosenRelic = 0;
            chosenClass = chosenRelic;

            //BaseMod.modSettingsUp = true;
            //MariMod.settingsPanel.isUp = true;
        }

        boolean selected = false;
        for(CharacterOption c: CardCrawlGame.mainMenuScreen.charSelectScreen.options) {
            if(c.selected && c.c.chosenClass.equals(PlayerClassEnum.MARI)) selected = true;
        }

        if (selected) {
            this.infoX = MathHelper.uiLerpSnap(this.infoX, DEST_INFO_X);
        } else {
            this.infoX = MathHelper.uiLerpSnap(this.infoX, START_INFO_X);
        }

        if(selected) {
            wasJustSelected = true;

            particleTimer -= Gdx.graphics.getRawDeltaTime() * particleTimeDilation;
            particleTimeDilation = MathUtils.lerp(particleTimeDilation, 1.0f, Gdx.graphics.getRawDeltaTime() * 3.0f);
            if (particleTimer <= 0) {
                particles.add(new GlowyParticle(MathUtils.random(0, 7)));
                particleTimer += 0.12f;
            }
            Iterator itr = particles.iterator();
            while (itr.hasNext()) {
                GlowyParticle p = (GlowyParticle) itr.next();
                p.update();
                if (p.toBeDeleted) {
                    itr.remove();
                }
            }

            switch (chosenRelic){
                case 0:
                    backgroundColor.r = MathUtils.lerp(backgroundColor.r, 1.0f, Gdx.graphics.getRawDeltaTime() * 0.5f);
                    backgroundColor.g = MathUtils.lerp(backgroundColor.g, 1.0f, Gdx.graphics.getRawDeltaTime() * 0.5f);
                    backgroundColor.b = MathUtils.lerp(backgroundColor.b, 1.0f, Gdx.graphics.getRawDeltaTime() * 0.5f);
                    break;
                case 1:
                    backgroundColor.r = MathUtils.lerp(backgroundColor.r, 0.5f, Gdx.graphics.getRawDeltaTime() * 0.5f);
                    backgroundColor.g = MathUtils.lerp(backgroundColor.g, 1.0f, Gdx.graphics.getRawDeltaTime() * 0.5f);
                    backgroundColor.b = MathUtils.lerp(backgroundColor.b, 0.8f, Gdx.graphics.getRawDeltaTime() * 0.5f);
                    break;
                case 2:
                    backgroundColor.r = MathUtils.lerp(backgroundColor.r, 1.0f, Gdx.graphics.getRawDeltaTime() * 0.5f);
                    backgroundColor.g = MathUtils.lerp(backgroundColor.g, 0.6f, Gdx.graphics.getRawDeltaTime() * 0.5f);
                    backgroundColor.b = MathUtils.lerp(backgroundColor.b, 0.8f, Gdx.graphics.getRawDeltaTime() * 0.5f);
                    break;
                default:
                    break;
            }

            foregroundColorTimer += Gdx.graphics.getRawDeltaTime();
            float foregroundBrightness = 0.90f + MathUtils.sin(foregroundColorTimer * MathUtils.PI / 4.0f) * 0.10f;
            foregroundColor = new Color(foregroundBrightness, foregroundBrightness, foregroundBrightness, 1.0f);

            foregroundGlowColorTimer += Gdx.graphics.getRawDeltaTime();
            float foregroundGlowBrightness = 0.90f + MathUtils.sin(foregroundGlowColorTimer * MathUtils.PI / 2.4f) * 0.10f;
            foregroundGlowColor = new Color(foregroundGlowBrightness, foregroundGlowBrightness, foregroundGlowBrightness, 0.50f);
        }else{
            if(wasJustSelected){
                wasJustSelected = false;
                BaseMod.modSettingsUp = false;
                MariMod.settingsPanel.isUp = false;
            }
        }

        if(settingsPanel != null && settingsPanel.isUp){
            settingsPanel.update();
        }
    }

    public void render(SpriteBatch sb){
        Color prevColor = sb.getColor();

        boolean selected = false;
        for(CharacterOption c: CardCrawlGame.mainMenuScreen.charSelectScreen.options) {
            if(c.selected && c.c.chosenClass.equals(PlayerClassEnum.MARI)) selected = true;
        }
        if(selected) {
            if (backgroundColor == null) {
                backgroundColor = Color.WHITE.cpy();
                foregroundColor = Color.WHITE.cpy();
                foregroundGlowColor = Color.WHITE.cpy();
            }
            if (backgroundTexture == null) {
                backgroundTexture = ImageMaster.loadImage("mari_mod/images/charSelect/MariPortraitBg.png");
                background = ImageMaster.vfxAtlas.addRegion("MariSelectBg", backgroundTexture, 0, 0, 1920, 1200);
                foregroundTexture = ImageMaster.loadImage("mari_mod/images/charSelect/MariPortraitFront.png");
                foreground = ImageMaster.vfxAtlas.addRegion("MariSelectFront", foregroundTexture, 0, 0, 1920, 1200);
                particleTexture = ImageMaster.loadImage("mari_mod/images/charSelect/MariGlowyParticle.png");
                particleImg = ImageMaster.vfxAtlas.addRegion("MariSelectParticles", particleTexture, 0, 0, 256, 256);
                backgroundGlowTexture = ImageMaster.loadImage("mari_mod/images/charSelect/MariPortraitGlow.png");
                backgroundGlow = ImageMaster.vfxAtlas.addRegion("MariSelectGlow", backgroundGlowTexture, 0, 0, 1920, 1200);
                upArrowTexture = ImageMaster.loadImage("mari_mod/images/charSelect/tinyUpArrow.png");
                upArrowImg = ImageMaster.vfxAtlas.addRegion("MariUpArrow", upArrowTexture, 0, 0, 48, 48);
                downArrowTexture = ImageMaster.loadImage("mari_mod/images/charSelect/tinyDownArrow.png");
                downArrowImg = ImageMaster.vfxAtlas.addRegion("MariDownArrow", downArrowTexture, 0, 0, 48, 48);
                settingsTexture = ImageMaster.loadImage("mari_mod/images/charSelect/settingsButton.png");
                settingsImg = ImageMaster.vfxAtlas.addRegion("MariSettingsButton", settingsTexture, 0, 0, 160, 138);
                testGridTexture = ImageMaster.loadImage("mari_mod/images/charSelect/testGrid.png");
                testGridImg = ImageMaster.vfxAtlas.addRegion("MariTestGrid", testGridTexture, 0, 0, 3000, 3000);
            }

            sb.setColor(backgroundColor);
            sb.draw(this.background, (float) Settings.WIDTH / 2.0F - 960.0F, (float) Settings.HEIGHT / 2.0F - 600.0F, 960.0F, 600.0F, 1920.0F, 1200.0F, Settings.scale, Settings.scale, 0.0F);

            //sb.setColor(foregroundGlowColor);
            //sb.draw(this.backgroundGlow, (float) Settings.WIDTH / 2.0F - 960.0F, (float) Settings.HEIGHT / 2.0F - 600.0F, 960.0F, 600.0F, 1920.0F, 1200.0F, Settings.scale, Settings.scale, 0.0F);


            Iterator itr = particles.iterator();
            while (itr.hasNext()) {
                ((GlowyParticle) itr.next()).render(sb, particleImg);
            }

            sb.setColor(foregroundColor);
            sb.draw(this.foreground, (float) Settings.WIDTH / 2.0F - 960.0F, (float) Settings.HEIGHT / 2.0F - 600.0F, 960.0F, 600.0F, 1920.0F, 1200.0F, Settings.scale, Settings.scale, 0.0F);

            renderButtons(sb);
            //sb.draw(this.testGridImg, 0, 0, 0, 0, 3000, 3000, Settings.scale, Settings.scale, 0.0F);
        }

        renderInfo(sb);
        renderRelics(sb);
        renderSettings(sb);

        sb.setColor(prevColor);


    }

    public void renderInfo(SpriteBatch sb){

        sb.setColor(Color.WHITE);

        FontHelper.renderSmartText(sb, FontHelper.bannerNameFont, this.name, this.infoX + (-35.0F + 200.0f) * Settings.scale, this.infoY + NAME_OFFSET_Y, 99999.0F, 38.0F * Settings.scale, Settings.GOLD_COLOR.cpy());

        sb.draw(ImageMaster.TP_HP, this.infoX + (-10.0F) * Settings.scale - 32.0F, this.infoY + (90.0f + 95.0F + 9.0f) * Settings.scale - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, TEXT[4] + this.hp, this.infoX + 18.0F * Settings.scale, this.infoY + (90.0f + 102.0F + 9.0f) * Settings.scale, 10000.0F, 10000.0F, Settings.RED_TEXT_COLOR);

        sb.draw(ImageMaster.TP_GOLD, this.infoX + (-10.0F - 4.0F) * Settings.scale - 32.0F, this.infoY + (50.0f + 95.0F + 9.0f) * Settings.scale - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, TEXT[5] + this.gold, this.infoX + 18.0F * Settings.scale, this.infoY + (50.0f + 102.0F + 9.0f) * Settings.scale, 10000.0F, 10000.0F, Settings.GOLD_COLOR);

        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, this.flavorText, this.infoX - 26.0F * Settings.scale, this.infoY + (40.0F + 80.0F) * Settings.scale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);

    }

    public void renderButtons(SpriteBatch sb){
        if (!relicUpHb.hovered) {
            sb.setColor(Color.LIGHT_GRAY);
        } else {
            sb.setColor(Color.WHITE);
        }
        sb.draw(upArrowTexture, (this.infoX - DEST_INFO_X) + relicUpHb.cX + (-24.0F * Settings.scale), relicUpHb.cY + (-24.0F * Settings.scale), 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
        if (!relicDownHb.hovered) {
            sb.setColor(Color.LIGHT_GRAY);
        } else {
            sb.setColor(Color.WHITE);
        }
        sb.draw(downArrowTexture, (this.infoX - DEST_INFO_X) + relicDownHb.cX + (-24.0F * Settings.scale), relicDownHb.cY + (-24.0F * Settings.scale), 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);

        sb.draw(settingsTexture, (1710.0F * Settings.scale), (220.0F * Settings.scale), 80.0F, 69.0F, 160.0F, 138.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 160, 138, false, false);

        relicUpHb.render(sb);
        relicDownHb.render(sb);
    }

    public void renderRelics(SpriteBatch sb){

        AbstractRelic r = RelicLibrary.getRelic((Mari.getChosenRelic(chosenRelic)));
        r.updateDescription(PlayerClassEnum.MARI);
        Hitbox relicHitbox = new Hitbox(80.0F * Settings.scale * (0.01F + (1.0F - 0.019F * 1)), 80.0F * Settings.scale);
        relicHitbox.move(this.infoX + (18.0F) * Settings.scale, this.infoY + (-60.0F + 28.0F) * Settings.scale);
        relicHitbox.render(sb);
        relicHitbox.update();
        if (relicHitbox.hovered) {
            if ((float) InputHelper.mX < 1400.0F * Settings.scale) {
                TipHelper.queuePowerTips((float)InputHelper.mX + 60.0F * Settings.scale, (float)InputHelper.mY - 50.0F * Settings.scale, r.tips);
            } else {
                TipHelper.queuePowerTips((float)InputHelper.mX - 350.0F * Settings.scale, (float)InputHelper.mY - 50.0F * Settings.scale, r.tips);
            }
        }

        sb.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F));
        sb.draw(r.outlineImg, this.infoX + (-50.0F) * Settings.scale, this.infoY + (-60.0F + 28.0F -64.0f) * Settings.scale, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale * (0.01F + (1.0F - 0.019F * 1)), Settings.scale * (0.01F + (1.0F - 0.019F * 1)), 0.0F, 0, 0, 128, 128, false, false);
        sb.setColor(Color.WHITE);
        sb.draw(r.img, this.infoX + (-50.0F) * Settings.scale, this.infoY + (-60.0F + 28.0F -64.0f) * Settings.scale, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale * (0.01F + (1.0F - 0.019F * 1)), Settings.scale * (0.01F + (1.0F - 0.019F * 1)), 0.0F, 0, 0, 128, 128, false, false);

    }

    public void renderSettings(SpriteBatch sb){
        if(settingsPanel != null && settingsPanel.isUp){
            settingsPanel.render(sb);
        }
    }

    public class GlowyParticle{

        public static final boolean COLOR_TEST_MODE = false;

        float w16 = Settings.WIDTH/16.0f;
        float h16 = Settings.HEIGHT/16.0f;

        float w64 = Settings.WIDTH/64.0f;
        float h64 = Settings.HEIGHT/64.0f;

        float w256 = Settings.WIDTH/256f;
        float h256 = Settings.HEIGHT/256f;

        float aX = 0;
        float aY = 0;
        float vX = 0;
        float maxVX = 9999.0f;
        float maxVY = 9999.0f;
        float vY = 0;
        float cX = 0;
        float cY = 0;

        float inherentAlpha = 1f;
        float changingAlpha = 1f;
        float inherentScale = 1f;
        float changingScale = 1f;

        Color currentColor;
        float cR = 1f;
        float cG = 0.7f;
        float cB = 1f;

        boolean blinker = false;
        float blinkTimer = 0;

        boolean toBeDeleted = false;
        int pathMode = 0;

        public GlowyParticle(){
            this(0);
        }

        public GlowyParticle(int forcedMode){
            this.inherentAlpha = MathUtils.random(0.75f, 1.0f);
            if(COLOR_TEST_MODE) currentColor = Color.RED.cpy();
            currentColor = Color.WHITE.cpy();
            pathMode = forcedMode;
            if(pathMode == 0){
                this.cX = 13 * w16;
                this.cY = 0 * h16;
                this.vX = w64 * -2;
                this.aY = h256 * 2.5f;
                cR = 0.2f;
                cG = 0.0f;
                cB = 0.5f;
                this.inherentScale = MathUtils.random(0.05f, MathUtils.random(0.02f, 0.15f));
            }
            if(pathMode == 1){
                this.cX = 11 * w16;
                this.cY = 3 * h16;
                this.vX = w64 * -2.5f;
                this.vY = h64 * 2.0f;
                this.aY = h256 * -0.5f;
                cR = 0.2f;
                cG = 0.0f;
                cB = 0.5f;
                this.inherentScale = MathUtils.random(0.05f, MathUtils.random(0.02f, 0.15f));
            }
            if(pathMode == 2){
                this.cX = 10 * w16;
                this.cY = 10 * h16;
                this.vX = w64 * -2.5f;
                this.vY = h64 * -0.5f;
                this.aY = h256 * 1f;
                this.inherentScale = MathUtils.random(0.05f, MathUtils.random(0.02f, 0.15f));
            }
            if(pathMode == 3){
                this.cX = 10 * w16;
                this.cY = 10 * h16;
                this.vX = w64 * -2.5f;
                this.vY = h64 * 0.5f;
                this.aY = h256 * 0.1f;
                this.inherentScale = MathUtils.random(0.05f, MathUtils.random(0.02f, 0.15f));
            }
            if(pathMode == 4){
                this.cX = 12 * w16;
                this.cY = 10 * h16;
                this.vX = w64 * -2.4f;
                this.vY = h64 * 0.7f;
                this.aY = h256 * 2.2f;
                this.inherentScale = MathUtils.random(0.05f, MathUtils.random(0.02f, 0.15f));
            }
            if(pathMode == 5){
                this.cX = 12 * w16;
                this.cY = 10 * h16;
                this.vX = w64 * -1.5f;
                this.vY = h64 * 4.7f;
                this.aY = h256 * -2.4f;
                this.inherentScale = MathUtils.random(0.05f, MathUtils.random(0.02f, 0.15f));
            }
            if(pathMode == 6){
                this.cX = 14 * w16;
                this.cY = 0 * h16;
                this.vX = w64 * 1.8f;
                this.aX = w256 * -1.3f;
                this.vY = h64 * 2.8f;
                this.inherentScale = MathUtils.random(0.05f, MathUtils.random(0.02f, 0.15f));
            }
            if(pathMode == 7){
                this.cX = 13 * w16;
                this.cY = 9 * h16;
                this.vX = w64 * 1.8f;
                this.aX = w256 * -1.3f;
                this.vY = h64 * 2.8f;
                this.inherentScale = MathUtils.random(0.05f, MathUtils.random(0.02f, 0.15f));
            }
            if(MathUtils.randomBoolean(0.12f)){
                blinker = true;
            }

            this.cX += MathUtils.random(w16 * -0.2f, w16 * 0.2f);
            this.cY += MathUtils.random(h16 * -0.2f, h16 * 0.2f);
        }

        public float offset = 300 * Settings.scale;
        public void update(){

            float dTime = Gdx.graphics.getRawDeltaTime() * particleTimeDilation;

            switch(pathMode) {
                case 0:
                    if(this.cX > w16 * 6 && this.cX < w16 * 10){
                        if(COLOR_TEST_MODE) currentColor = Color.ORANGE.cpy();
                        this.aY = h256 * -2.5f;
                        this.aX = h256 * -0.5f;
                        if(this.vY < 0){
                            this.vY = 0;
                            this.aY = 0;
                        }
                    }else if(this.cX > w16 * 3 && this.cX <= w16 * 6){
                        if(COLOR_TEST_MODE) currentColor = Color.YELLOW.cpy();
                        this.aY = h256 * -0.25f;
                    }else if(this.cX < w16 * 3){
                        if(COLOR_TEST_MODE) currentColor = Color.GREEN.cpy();
                        this.aY = h256 * -3.0f;
                    }
                    break;
                case 1:
                    if(this.cX > w16 * 5 && this.cX < w16 * 7){
                        if(COLOR_TEST_MODE) currentColor = Color.ORANGE.cpy();
                        this.aY = h256 * 2.5f;
                        this.aX = h256 * 0.5f;
                        if(this.vX > w64 * -1.5f){
                            this.vX = -1.5f;
                        }
                    }else if(this.cX < w16 * 5){
                        if(COLOR_TEST_MODE) currentColor = Color.YELLOW.cpy();
                        this.aY = h256 * -4f;
                        this.aX = h256 * 0.3f;
                    }
                    break;
                case 2:
                    if(this.cX < w16 * 8) {
                        if(COLOR_TEST_MODE) currentColor = Color.ORANGE.cpy();
                        this.aY = h256 * 2.8f;
                        this.aX = h256 * 2f;
                        if(this.vX > w64 * -1){
                            this.vX = w64 * -1;
                        }
                        if(this.vY > w64 * 3){
                            this.vY = w64 * 3;
                        }
                    }
                    break;
                case 3:
                    if(this.cX < w16 * 6) {
                        if(COLOR_TEST_MODE) currentColor = Color.ORANGE.cpy();
                        this.aY = h256 * 1.9f;
                        this.aX = h256 * 1f;
                        if(this.vX > w64 * -1){
                            this.vX = w64 * -1;
                        }
                        if(this.vY > w64 * 3){
                            this.vX = w64 * 4;
                        }
                    }
                    break;
                case 4:
                    if(this.cX < w16 * 5) {
                        if(COLOR_TEST_MODE) currentColor = Color.YELLOW.cpy();
                        this.aY = h256 * 0.4f;
                        this.aX = h256 * -0.2f;
                    }else if(this.cX < w16 * 9) {
                        if(COLOR_TEST_MODE) currentColor = Color.ORANGE.cpy();
                        this.aY = h256 * -2.6f;
                        this.aX = h256 * -0.1f;
                    }
                    break;
                case 5:
                    if(this.cX < w16 * 6) {
                        if(COLOR_TEST_MODE) currentColor = Color.YELLOW.cpy();
                        this.aY = h256 * 0.4f;
                        this.aX = h256 * -0.3f;
                    }else if(this.cX < w16 * 9) {
                        if(COLOR_TEST_MODE) currentColor = Color.ORANGE.cpy();
                        this.aY = 0;
                        this.vY = MathUtils.lerp(this.vY, 0, dTime * 0.1f);
                        this.aX = h256 * -0.3f;
                    }
                    break;
                case 6:
                    if(this.cY > h16 * 8) {
                        if(COLOR_TEST_MODE) currentColor = Color.ORANGE.cpy();
                        this.aY = h256 * 0.2f;
                        this.aX = w256 * 1.2f;
                    }
                    break;
                case 7:
                    break;

                default:
                    break;
            }

            vX += aX * dTime;
            vY += aY * dTime;

            if(vX != 0 && Math.abs(vX) > maxVX) vX = maxVX * (vX/Math.abs(vX));
            if(vY != 0 && Math.abs(vY) > maxVY) vY = maxVY * (vY/Math.abs(vY));

            cX += vX * dTime;
            cY += vY * dTime;

            if(this.cX < -1 * offset || this.cX > Settings.WIDTH + offset || this.cY < -1 * offset || this.cY > Settings.HEIGHT + offset){
                this.toBeDeleted = true;
            }

            if(blinker){
                if(this.inherentScale > 0.1f){
                    this.inherentScale = 0.1f;
                }
                blinkTimer += dTime;
                this.changingScale = (MathUtils.sin(blinkTimer) + 1.0f)/1.5f;
                this.changingAlpha = (MathUtils.sin(blinkTimer) + 1.0f)/2.0f;
            }

            if(cX < w16 * 6 && cY < h16 * 10){
                changingAlpha = MathUtils.lerp(changingAlpha, 0.2f, dTime * 0.1f);
            }

            if(cX > w16 * 6 && cX < w16 * 10 && cY < h16 * 8){
                cR = MathUtils.lerp(cR, 0.8f, dTime * 0.15f);
                cG = MathUtils.lerp(cG, 0.0f, dTime * 0.15f);
                cB = MathUtils.lerp(cB, 1.0f, dTime * 0.15f);
            }else if(cX > w16 * 6 && cX < w16 * 12 && cY > h16 * 10){
                cG = MathUtils.lerp(cG, 0.4f, dTime * 0.1f);
            }else if(cX < w16 * 6 && cY > h16 * 10){
                cG = MathUtils.lerp(cG, 1.0f, dTime * 0.1f);
            }

            if(!COLOR_TEST_MODE){
                this.currentColor = new Color(cR, cG, cB, changingAlpha * inherentAlpha);
            }
        }

        public void render(SpriteBatch sb, TextureAtlas.AtlasRegion img){

            float masterAlpha = inherentAlpha * changingAlpha;
            float masterScale = inherentScale * changingScale;
            this.currentColor.a = masterAlpha;
            float settingScale = masterScale * Settings.scale;
            sb.setColor(this.currentColor);
            sb.draw(img, cX - (128 * settingScale), cY - (128 * settingScale), 0,0,256,256, masterScale, masterScale, 0.0f);
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("CharacterOption");
        TEXT = uiStrings.TEXT;
        DEST_INFO_X = 200.0F * Settings.scale;
        START_INFO_X = -800.0F * Settings.scale;
        START_INFO_Y = (float)Settings.HEIGHT / 2.0F;
        NAME_OFFSET_Y = 200.0F * Settings.scale;
    }
}
