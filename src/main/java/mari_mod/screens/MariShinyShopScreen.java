/*
    TODO: CREDIT CUSTOM CARD REWARD SCREEN MADE BY lionpkqq FOR THE SERVANT MOD
 */

package mari_mod.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ShaderHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.shop.StorePotion;
import com.megacrit.cardcrawl.shop.StoreRelic;
import mari_mod.helpers.FBHelper;
import mari_mod.patches.MariShinyShopPatch;

import java.util.ArrayList;


public class MariShinyShopScreen {
    public static Texture rugImg;
    public static boolean grabbingFrame = false;
    public FrameBuffer sceneFrameBuffer = null;
    public static Texture sceneImg = null;
    public float rugX;
    public static final float rugOffset = Settings.WIDTH * 1f;

    public MariShinyShopScreen() {
    }

    public void init(ArrayList<AbstractCard> coloredCards, ArrayList<AbstractCard> colorlessCards) {
    }

    public void initTextures(){
        if(sceneFrameBuffer == null) {
            sceneFrameBuffer = FBHelper.createBuffer();
        }
        rugImg = ImageMaster.loadImage("mari_mod/images/shinyShop/ShinyRug.png");
    }

    public static void resetPurgeCost() {
    }

    private void initCards() {
    }

    public static void purgeCard() {
    }

    public void updatePurge() {
    }

    public static String getCantBuyMsg() {
        return null;
    }

    public static String getBuyMsg() {
        return null;
    }

    public void applyDiscount(float multiplier, boolean affectPurge) {

    }

    private void initRelics() {
    }

    private void initPotions() {
    }

    public void getNewPrice(StoreRelic r) {
    }

    public void getNewPrice(StorePotion r) {
    }

    private int applyDiscountToRelic(int price, float multiplier) {
        return 0;
    }

    public static AbstractRelic.RelicTier rollRelicTier() {
        return null;
    }

    private void setStartingCardPositions() {
        //called from open
    }

    public void open() {
        grabSceneFrame();

        this.resetTouchscreenVars();// 524
        CardCrawlGame.sound.play("SHOP_OPEN");
        if(AbstractDungeon.screen == AbstractDungeon.CurrentScreen.COMBAT_REWARD) {
            AbstractDungeon.closeCurrentScreen();
            AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
        }
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = MariShinyShopPatch.SHINY_SHOP;
        AbstractDungeon.overlayMenu.proceedButton.hide();
        AbstractDungeon.overlayMenu.cancelButton.show("value");
        AbstractDungeon.overlayMenu.showBlackScreen();// 548
        AbstractDungeon.dungeonMapScreen.closeInstantly();

        rugX = rugOffset;
//        var1 = this.coloredCards.iterator();// 551
//
//        AbstractCard c;
//        while(var1.hasNext()) {
//            c = (AbstractCard)var1.next();
//            UnlockTracker.markCardAsSeen(c.cardID);// 552
//        }
//
//        var1 = this.colorlessCards.iterator();
//
//        while(var1.hasNext()) {
//            c = (AbstractCard)var1.next();// 554
//            UnlockTracker.markCardAsSeen(c.cardID);// 555
//        }
//
//        var1 = this.relics.iterator();// 557
//
//        while(var1.hasNext()) {
//            r = (StoreRelic)var1.next();
//            if (r.relic != null) {// 558
//                UnlockTracker.markRelicAsSeen(r.relic.relicId);// 559
//            }
//        }


    }// 565

    public void reopen(){
        CardCrawlGame.sound.play("SHOP_OPEN");
        AbstractDungeon.topPanel.unhoverHitboxes();// 275
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = MariShinyShopPatch.SHINY_SHOP;
        AbstractDungeon.overlayMenu.proceedButton.hide();
        AbstractDungeon.overlayMenu.cancelButton.show("value");
        AbstractDungeon.dungeonMapScreen.closeInstantly();
    }

    private void resetTouchscreenVars() {
    }// 576

    public void update() {
//
//        this.f_effect.update();// 611
        this.updateControllerInput();// 614
        this.updatePurgeCard();// 615
        this.updatePurge();// 616
        this.updateRelics();// 617
        this.updatePotions();// 618
        this.updateRug();// 619
        this.updateSpeech();// 620
        this.updateCards();// 621
        AbstractCard hoveredCard = null;// 626

        if (hoveredCard != null && InputHelper.justClickedLeft) {// 655
            hoveredCard.hb.clickStarted = true;// 656
        }

        if (hoveredCard != null && (InputHelper.justClickedRight || CInputActionSet.proceed.isJustPressed())) {// 659
            InputHelper.justClickedRight = false;// 660
            CardCrawlGame.cardPopup.open(hoveredCard);// 661
        }

        if (hoveredCard != null && (hoveredCard.hb.clicked || CInputActionSet.select.isJustPressed())) {// 664
            hoveredCard.hb.clicked = false;// 665
            this.purchaseCard(hoveredCard);// 667
        }

    }// 682

    private void purchaseCard(AbstractCard hoveredCard) {
    }

    private void updateCards() {
    }// 764

    private void setPrice(AbstractCard card) {
    }// 794

    private void updateControllerInput() {
    }// 808 1139

    private void updatePurgeCard() {
    }

    private void purchasePurge() {
//        this.purgeHovered = false;// 1194
//        if (AbstractDungeon.player.gold >= actualPurgeCost) {// 1195
//            AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.SHOP;// 1196
//            AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 1, NAMES[13], false, false, true, true);// 1198 1199
//        } else {
//            this.playCantBuySfx();// 1207
//            this.createSpeech(getCantBuyMsg());// 1208
//        }

    }// 1210

    private void updateRelics() {
    }

    private void updatePotions() {
    }

    public void createSpeech(String msg) {
    }

    private void updateSpeech() {
    }

    private void welcomeSfx() {
        int roll = MathUtils.random(2);// 1298
        if (roll == 0) {// 1299
            CardCrawlGame.sound.play("VO_MERCHANT_3A");// 1300
        } else if (roll == 1) {// 1301
            CardCrawlGame.sound.play("VO_MERCHANT_3B");// 1302
        } else {
            CardCrawlGame.sound.play("VO_MERCHANT_3C");// 1304
        }

    }
    private void updateRug() {
        if (this.rugX != 0.0F) {// 1352
            this.rugX = MathUtils.lerp(this.rugX, 0, Gdx.graphics.getDeltaTime() * 5.0F);// 1353 1356
            if (Math.abs(this.rugX - 0.0F) < 0.5F) {// 1357
                this.rugX = 0.0F;// 1358
            }
        }
    }

    public void render(SpriteBatch sb) {
        if(grabbingFrame) return;

        sb.setColor(Color.BLACK);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, (float)Settings.WIDTH, (float)Settings.HEIGHT);
        sb.setColor(Color.WHITE);
        sb.draw(rugImg, rugX, 0.0F, (float)Settings.WIDTH, (float)Settings.HEIGHT);
        if(sceneImg != null){
            sb.end();
            //sb.setShader(MariMod.orangeShader);
            sb.begin();
            sb.draw(sceneImg, rugX-rugOffset, 0.0F, Settings.WIDTH, Settings.HEIGHT, 0, 0, Settings.WIDTH, Settings.HEIGHT, false, true);// 1381
            ShaderHelper.setShader(sb, ShaderHelper.Shader.DEFAULT);
        }
        this.renderCardsAndPrices(sb);// 1383
        this.renderRelics(sb);// 1384
        this.renderPotions(sb);// 1385
        this.renderPurge(sb);// 1386
    }

    public void grabSceneFrame(){
        FBHelper.beginBuffer(sceneFrameBuffer);

        SpriteBatch sb = new SpriteBatch();
        sb.begin();
        grabbingFrame = true;
        CardCrawlGame.dungeon.render(sb);
        grabbingFrame = false;

        sb.end();
        sceneFrameBuffer.end();

        System.out.println("grabby grab");
        TextureRegion txt = FBHelper.getBufferTexture(sceneFrameBuffer);
        sceneImg = txt.getTexture();
    }

    private void renderRelics(SpriteBatch sb) {
    }// 1407

    private void renderPotions(SpriteBatch sb) {
    }// 1413

    private void renderCardsAndPrices(SpriteBatch sb) {

    }

    private void renderPurge(SpriteBatch sb) {

    }

    static {
    }

    private static enum StoreSelectionType {
        ;

        private StoreSelectionType() {
        }// 801
    }
}
