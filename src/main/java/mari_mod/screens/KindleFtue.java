//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.ui.FtueTip;
import com.megacrit.cardcrawl.vfx.combat.BattleStartEffect;

public class KindleFtue extends FtueTip {
    private static final TutorialStrings tutorialStrings;
    public static final String[] MSG;
    public static final String[] LABEL;
    private static final int W = 760;
    private static final int H = 580;
    private Texture img1 = ImageMaster.loadImage("mari_mod/images/tutorial/kindle1.png");
    private Texture img2 = ImageMaster.loadImage("mari_mod/images/tutorial/kindle2.png");
    private Texture img3 = ImageMaster.loadImage("mari_mod/images/tutorial/kindle3.png");
    private Color screen = Color.valueOf("1c262a00");
    private float x;
    private float x1;
    private float x2;
    private float x3;
    private float targetX;
    private float startX;
    private float scrollTimer = 0.0F;
    private static final float SCROLL_TIME = 0.3F;
    private int currentSlot = 0;
    private static final String msg1;
    private static final String msg2;
    private static final String msg3;

    public KindleFtue() {
        AbstractDungeon.player.releaseCard();
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }

        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = CurrentScreen.FTUE;
        AbstractDungeon.overlayMenu.showBlackScreen();
        this.x = 0.0F;
        this.x1 = 567.0F * Settings.scale;
        this.x2 = this.x1 + (float)Settings.WIDTH;
        this.x3 = this.x2 + (float)Settings.WIDTH;
        AbstractDungeon.overlayMenu.proceedButton.show();
        AbstractDungeon.overlayMenu.proceedButton.setLabel(LABEL[0]);
    }

    public void update() {
        if (this.screen.a != 0.8F) {
            Color var10000 = this.screen;
            var10000.a += Gdx.graphics.getDeltaTime();
            if (this.screen.a > 0.8F) {
                this.screen.a = 0.8F;
            }
        }

        if (AbstractDungeon.overlayMenu.proceedButton.isHovered && InputHelper.justClickedLeft || CInputActionSet.proceed.isJustPressed()) {
            CInputActionSet.proceed.unpress();
            if (this.currentSlot == -2) {
                CardCrawlGame.sound.play("DECK_CLOSE");
                AbstractDungeon.closeCurrentScreen();
                AbstractDungeon.overlayMenu.proceedButton.hide();
                AbstractDungeon.effectList.clear();
                AbstractDungeon.topLevelEffects.add(new BattleStartEffect(false));
                return;
            }

            AbstractDungeon.overlayMenu.proceedButton.hideInstantly();
            AbstractDungeon.overlayMenu.proceedButton.show();
            CardCrawlGame.sound.play("DECK_CLOSE");
            --this.currentSlot;
            this.startX = this.x;
            this.targetX = (float)(this.currentSlot * Settings.WIDTH);
            this.scrollTimer = 0.3F;
            if (this.currentSlot == -2) {
                AbstractDungeon.overlayMenu.proceedButton.setLabel(LABEL[1]);
            }
        }

        if (this.scrollTimer != 0.0F) {
            this.scrollTimer -= Gdx.graphics.getDeltaTime();
            if (this.scrollTimer < 0.0F) {
                this.scrollTimer = 0.0F;
            }
        }

        this.x = Interpolation.fade.apply(this.targetX, this.startX, this.scrollTimer / 0.3F);
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.screen);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, (float)Settings.WIDTH, (float)Settings.HEIGHT);
        sb.setColor(Color.WHITE);
        sb.draw(this.img1, this.x + this.x1 - 380.0F, (float)Settings.HEIGHT / 2.0F - 290.0F, 380.0F, 290.0F, 760.0F, 580.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 760, 580, false, false);
        sb.draw(this.img2, this.x + this.x2 - 380.0F, (float)Settings.HEIGHT / 2.0F - 290.0F, 380.0F, 290.0F, 760.0F, 580.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 760, 580, false, false);
        sb.draw(this.img3, this.x + this.x3 - 380.0F, (float)Settings.HEIGHT / 2.0F - 290.0F, 380.0F, 290.0F, 760.0F, 580.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 760, 580, false, false);
        FontHelper.renderSmartText(sb, FontHelper.panelNameFont, msg1, this.x + this.x1 + 400.0F * Settings.scale, (float)Settings.HEIGHT / 2.0F - FontHelper.getSmartHeight(FontHelper.panelNameFont, msg1, 700.0F * Settings.scale, 40.0F * Settings.scale) / 2.0F, 700.0F * Settings.scale, 40.0F * Settings.scale, Settings.CREAM_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.panelNameFont, msg2, this.x + this.x2 + 400.0F * Settings.scale, (float)Settings.HEIGHT / 2.0F - FontHelper.getSmartHeight(FontHelper.panelNameFont, msg2, 700.0F * Settings.scale, 40.0F * Settings.scale) / 2.0F, 700.0F * Settings.scale, 40.0F * Settings.scale, Settings.CREAM_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.panelNameFont, msg3, this.x + this.x3 + 400.0F * Settings.scale, (float)Settings.HEIGHT / 2.0F - FontHelper.getSmartHeight(FontHelper.panelNameFont, msg3, 700.0F * Settings.scale, 40.0F * Settings.scale) / 2.0F, 700.0F * Settings.scale, 40.0F * Settings.scale, Settings.CREAM_COLOR);
        FontHelper.renderFontCenteredWidth(sb, FontHelper.panelNameFont, LABEL[2], (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F - 360.0F * Settings.scale, Settings.GOLD_COLOR);
        FontHelper.renderFontCenteredWidth(sb, FontHelper.tipBodyFont, LABEL[3] + Integer.toString(Math.abs(this.currentSlot - 1)) + LABEL[4], (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F - 400.0F * Settings.scale, Settings.CREAM_COLOR);
        AbstractDungeon.overlayMenu.proceedButton.render(sb);
    }

    static {
        tutorialStrings = CardCrawlGame.languagePack.getTutorialString("Main Tutorial");
        MSG = tutorialStrings.TEXT;
        LABEL = tutorialStrings.LABEL;
        msg1 = MSG[0];
        msg2 = MSG[1];
        msg3 = MSG[2];
    }
}
