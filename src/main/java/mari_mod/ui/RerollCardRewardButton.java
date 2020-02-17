//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.ui;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.PrismaticShard;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.ExhaustPileViewScreen;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.LightFlareParticleEffect;
import mari_mod.MariMod;
import mari_mod.patches.MariRerollCardRewardPatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class RerollCardRewardButton {
    private static final Color HOVER_BLEND_COLOR = new Color(1.0F, 1.0F, 1.0F, 0.4F);
    private static final float SHOW_X;
    private static final float DRAW_Y;
    private static final float HIDE_X;
    private float current_x;
    private float target_x;
    private boolean isHidden;
    private float particleTimer;
    public Hitbox hb;
    public CardGroup orderedGroup;
    public CardGroup rarityGroup;
    public ArrayList<PowerTip> buttonTips;
    public ArrayList<AbstractGameEffect> buttonEffects;
    public static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("MariRerollCardRewardButton");
    public static final String tipHeader = uiStrings.TEXT[0];


    public RerollCardRewardButton() {
        this.current_x = HIDE_X;
        this.target_x = this.current_x;
        this.isHidden = true;
        this.particleTimer = 0.0F;
        this.hb = new Hitbox(170.0F * Settings.scale, 170.0F * Settings.scale);
        this.hb.move(SHOW_X, DRAW_Y);
        buttonEffects = new ArrayList<>();
        buttonTips = new ArrayList<>();
        updateTip();
    }

    public void update() {
        if (!this.isHidden) {
            this.hb.update();
            if (InputHelper.justClickedLeft && this.hb.hovered) {
                this.hb.clickStarted = true;
                CardCrawlGame.sound.play("UI_CLICK_1");
            }

            if (this.hb.justHovered) {
                CardCrawlGame.sound.play("UI_HOVER");
            }

            if (InputActionSet.peek.isJustPressed() || CInputActionSet.peek.isJustPressed()) {
                CInputActionSet.peek.unpress();
                this.hb.clicked = true;
            }
        }

        if (!MariRerollCardRewardPatch.isMostRecentRewardRerolled() && MariMod.saveableKeeper.goldInvested >= MariMod.saveableKeeper.rewardRerollCost) {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0F) {
                this.particleTimer = 2F;
                for(int i = 0; i < 20; i++) {
                    buttonEffects.add(new LightFlareParticleEffect(this.hb.cX, this.hb.cY, Color.YELLOW));
                    buttonEffects.add(new LightFlareParticleEffect(this.hb.cX, this.hb.cY, Color.GOLD));
                    buttonEffects.add(new LightFlareParticleEffect(this.hb.cX, this.hb.cY, Color.ORANGE));
                    if(MathUtils.randomBoolean(0.02f)){
                        buttonEffects.add(new LightFlareParticleEffect(this.hb.cX, this.hb.cY, Color.GOLD));
                    }
                }
            }
        }

        if (this.current_x != this.target_x) {
            this.current_x = MathUtils.lerp(this.current_x, this.target_x, Gdx.graphics.getDeltaTime() * 9.0F);
            if (Math.abs(this.current_x - this.target_x) < Settings.UI_SNAP_THRESHOLD) {
                this.current_x = this.target_x;
            }
        }

        Iterator ita = buttonEffects.iterator();
        while(ita.hasNext()){
            AbstractGameEffect e = (AbstractGameEffect)ita.next();
            e.update();
            if(e.isDone) ita.remove();
        }

    }

    public void updateTip(){
        if(buttonTips.size() <= 0){
            buttonTips.add(new PowerTip(tipHeader, uiStrings.TEXT[1] + MariMod.saveableKeeper.rewardRerollCost + uiStrings.TEXT[2]));
        }else{
            buttonTips.set(0, new PowerTip(tipHeader, uiStrings.TEXT[1] + MariMod.saveableKeeper.rewardRerollCost + uiStrings.TEXT[2]));
        }
    }

    public void hideInstantly() {
        this.current_x = HIDE_X;
        this.target_x = HIDE_X;
        this.isHidden = true;
    }

    public void hide() {
        if (!this.isHidden) {
            this.target_x = HIDE_X;
            this.isHidden = true;
        }

    }

    public void show() {
        if (this.isHidden) {
            this.target_x = SHOW_X;
            this.isHidden = false;
        }

    }

    public void render(SpriteBatch sb) {
        for(AbstractGameEffect e: buttonEffects){
            e.render(sb);
        }
        sb.setColor(Color.WHITE);
        this.renderButton(sb);
        if (!MariRerollCardRewardPatch.isMostRecentRewardRerolled() && MariMod.saveableKeeper.goldInvested >= MariMod.saveableKeeper.rewardRerollCost) {
            sb.setBlendFunction(770, 1);
            sb.setColor(new Color(1.0F, 0.6F, 1.0F, 1.0F));
            float derp = Interpolation.swingOut.apply(1.0F, 1.1F, MathUtils.cosDeg((float)(System.currentTimeMillis() / 4L % 360L)) / 12.0F);
            sb.draw(MariMod.exhaustViewOrderButtonTexture, this.current_x - 64.0F, (float)Settings.HEIGHT / 2.0F - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale * derp, Settings.scale * derp, 0.0F, 0, 0, 128, 128, false, false);
            sb.setBlendFunction(770, 771);
        }

        if (this.hb.hovered && !this.hb.clickStarted && !MariRerollCardRewardPatch.isMostRecentRewardRerolled() && MariMod.saveableKeeper.goldInvested >= MariMod.saveableKeeper.rewardRerollCost) {
            sb.setBlendFunction(770, 1);
            sb.setColor(HOVER_BLEND_COLOR);
            this.renderButton(sb);
            sb.setBlendFunction(770, 771);
        }

        if (this.hb.clicked && !MariRerollCardRewardPatch.isMostRecentRewardRerolled() && MariMod.saveableKeeper.goldInvested >= MariMod.saveableKeeper.rewardRerollCost) {
            this.hb.clicked = false;
            MariRerollCardRewardPatch.setMostRecentRewardRerolled(true);
            rerollCards();
        }
        this.renderControllerUi(sb);
        if(this.hb.hovered) {
            TipHelper.queuePowerTips((float) InputHelper.mX + 50.0F * Settings.scale, (float) InputHelper.mY + 50.0F * Settings.scale, this.buttonTips);
        }
        if (!this.isHidden) {
            this.hb.render(sb);
        }
    }

    private void rerollCards(){

        MariMod.saveableKeeper.goldInvested -= MariMod.saveableKeeper.rewardRerollCost;
        MariMod.saveableKeeper.rewardRerollCost += 10;
        CardCrawlGame.sound.play("GOLD_JINGLE");

        RewardItem item = MariRerollCardRewardPatch.mostRecentReward;
        ArrayList<AbstractCard> newItem = new ArrayList<>();
        for(AbstractCard c: item.cards){
            boolean isDupe;
            AbstractCard newCard;
            do {

                isDupe = false;
                if (AbstractDungeon.player.hasRelic(PrismaticShard.ID)) {
                    newCard = (CardLibrary.getAnyColorCard(c.rarity));
                } else {
                    newCard = (AbstractDungeon.getCard(c.rarity));
                }

                for(AbstractRelic r: AbstractDungeon.player.relics){
                    r.onPreviewObtainCard(newCard);
                }

                for(AbstractCard ac: newItem){
                    if(ac.cardID.equals(newCard.cardID)){
                        isDupe = true;
                    }
                }
                for(AbstractCard ac: item.cards){
                    if(ac.cardID.equals(newCard.cardID)){
                        isDupe = true;
                    }
                }

            }while(isDupe);
            newItem.add(newCard);

        }

        item.cards.clear();
        for(AbstractCard c: newItem){
            item.cards.add(c);
            c.current_x = Settings.WIDTH / 2f;
            c.current_y = Settings.HEIGHT / 2f;
        }
        AbstractDungeon.cardRewardScreen.rewardGroup = newItem;
    }

    private void renderButton(SpriteBatch sb) {
        sb.draw(MariMod.exhaustViewOrderButtonTexture, this.current_x - 64.0F, (float)Settings.HEIGHT / 2.0F - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
    }

    private void renderControllerUi(SpriteBatch sb) {
        if (Settings.isControllerMode && !this.isHidden) {
            sb.setColor(Color.WHITE);
            sb.draw(CInputActionSet.peek.getKeyImg(), 20.0F * Settings.scale, this.hb.cY - 56.0F * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        }

    }

    static {
        SHOW_X = 140.0F * Settings.scale;
        DRAW_Y = (float)Settings.HEIGHT / 2.0F;
        HIDE_X = SHOW_X - 400.0F * Settings.scale;
    }
}
