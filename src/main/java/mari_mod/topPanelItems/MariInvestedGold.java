package mari_mod.topPanelItems;

import basemod.TopPanelItem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import mari_mod.MariMod;
import mari_mod.patches.MariShinyShopPatch;
import mari_mod.screens.MariShinyShopScreen;

public class MariInvestedGold extends TopPanelItem {
    private static final Texture IMG = new Texture("mari_mod/images/ui/mariInvestedGold.png");
    private static final String ID = "MariMod:MariBrilliance";
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private static final float TIP_Y;
    private static final float TOP_RIGHT_TIP_X;

    private static int displayBrilliance;
    private static String updatedTip;
    private static boolean justUpdatedTip;

    public MariInvestedGold(){
        super(IMG, ID);
        displayBrilliance = MariMod.saveableKeeper.investedGold;
        updatedTip = updateTips();
        justUpdatedTip = false;
    }
    @Override
    protected void onClick() {
        if(!AbstractDungeon.isFadingOut && !AbstractDungeon.player.viewingRelics) {
            if (AbstractDungeon.screen == MariShinyShopPatch.SHINY_SHOP) {
                AbstractDungeon.closeCurrentScreen();
            }else{
                if (MariMod.shinyShopScreen == null) {
                    MariMod.shinyShopScreen = new MariShinyShopScreen();
                    MariMod.shinyShopScreen.initTextures();
                }
                if (AbstractDungeon.previousScreen == MariShinyShopPatch.SHINY_SHOP) {
                    AbstractDungeon.closeCurrentScreen();
                    MariMod.shinyShopScreen.reopen();
                } else {
                    AbstractDungeon.closeCurrentScreen();
                    MariMod.shinyShopScreen.open();
                }
            }
        }
    }

    @Override
    protected void onHover() {
        super.onHover();

        if(!justUpdatedTip){
            updatedTip = updateTips();
            justUpdatedTip = true;
        }
        TipHelper.renderGenericTip(TOP_RIGHT_TIP_X, TIP_Y, TEXT[0], updatedTip);
    }

    @Override
    protected void onUnhover() {
        super.onUnhover();

        if(justUpdatedTip){
            justUpdatedTip = false;
        }
    }

    private String updateTips(){
        String construct = "";
        construct += TEXT[1] + MariMod.saveableKeeper.investedGold;
        construct += TEXT[2] + MariMod.saveableKeeper.investedGold/10;
        construct += TEXT[3] + MariMod.saveableKeeper.lifetimeInvestedGold;
        construct += TEXT[4] + MariMod.saveableKeeper.lifetimeInvestedGoldReturns;
        construct += TEXT[5];
        return construct;
    }

    @Override
    public void update() {
        super.update();
        if (MariMod.saveableKeeper.investedGold < displayBrilliance) {
            if (displayBrilliance - MariMod.saveableKeeper.investedGold > 99) {
                displayBrilliance -= 10;
            } else if (displayBrilliance - MariMod.saveableKeeper.investedGold > 9) {
                displayBrilliance -= 3;
            } else {
                --displayBrilliance;
            }
        } else if (MariMod.saveableKeeper.investedGold > displayBrilliance) {
            if (MariMod.saveableKeeper.investedGold - displayBrilliance > 99) {
                displayBrilliance += 10;
            } else if (MariMod.saveableKeeper.investedGold - displayBrilliance > 9) {
                displayBrilliance += 3;
            } else {
                ++displayBrilliance;
            }
        }
    }

    @Override
    public void render(SpriteBatch sb, Color color) {
        super.render(sb, color);
        float centeredX = this.x + this.hb_w/2 + (14f * Settings.scale);
        float centeredY = this.y + this.hb_h/2 + (-16f * Settings.scale);
        sb.setColor(new Color(0.0f,0f,0f,0.4f));
        //FontHelper.renderFontCentered(sb, FontHelper.topPanelInfoFont, MariMod.saveableKeeper.goldInvested + "", centeredX, centeredY,  new Color(0f, 0f, 0f, 0f), 0.75f);
        //sb.draw(ImageMaster.WHITE_SQUARE_IMG,  centeredX - FontHelper.layout.width/2 - bgBuffer, centeredY - FontHelper.layout.height/2 - bgBuffer, FontHelper.layout.width + bgBuffer * 2, FontHelper.layout.height + bgBuffer * 2, 0, 0, ImageMaster.WHITE_SQUARE_IMG.getWidth(), ImageMaster.WHITE_SQUARE_IMG.getHeight(), false, false);
        FontHelper.renderFontCentered(sb, FontHelper.topPanelAmountFont, displayBrilliance + "", centeredX, centeredY,  Color.GOLD.cpy(), 1.0f);
    }

    static{
        TIP_Y = (float)Settings.HEIGHT - 120.0F * Settings.scale;
        TOP_RIGHT_TIP_X = 1550.0F * Settings.scale;
        uiStrings = CardCrawlGame.languagePack.getUIString("MariInvestedGoldTip");
        TEXT = uiStrings.TEXT;
    }
}
