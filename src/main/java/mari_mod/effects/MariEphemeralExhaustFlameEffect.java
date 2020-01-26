//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.ui.panels.DiscardPilePanel;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class MariEphemeralExhaustFlameEffect extends AbstractGameEffect {
    public static final Logger logger = LogManager.getLogger(MariEphemeralExhaustFlameEffect.class.getName());
    private TextureAtlas.AtlasRegion img = this.getImg();
    private static final float EFFECT_DUR = 3.0F;
    private float x;
    private float y;
    private float vX;
    private float vY;
    private float timeUntilImgChange;


    public MariEphemeralExhaustFlameEffect() {
        this(false);
    }

    public MariEphemeralExhaustFlameEffect(boolean darkFlame){
        this.duration = EFFECT_DUR/MathUtils.random(1.0f,2.0f);
        if(darkFlame){
            float brightness = MathUtils.random(0.30f, 0.40f);
            this.color = new Color(brightness + MathUtils.random(-0.20f, 0.10f), MathUtils.random(0.0f, 0.1f), brightness + MathUtils.random(-0.20f, 0.10f), MathUtils.random(0.8f, 1.0f));
        }else {
            this.color = new Color(MathUtils.random(0.8f, 1.0f), MathUtils.random(0.4f, 0.8f), MathUtils.random(0.0f, 0.2f), MathUtils.random(0.8f, 1.0f));
        }
        this.scale = MathUtils.random(1.0f,2.0f) * Settings.scale;
        DiscardPilePanel p = AbstractDungeon.overlayMenu.discardPilePanel;
        this.x = p.current_x + (132.0F * Settings.scale) + (ImageMaster.DISCARD_BTN_BASE.getWidth() * Settings.scale * 0.5f) - (10.0f * Settings.scale); //taken from DECK_X and Y from DiscardPilePanel
        this.y = p.current_y + (22.0F * Settings.scale) + (ImageMaster.DISCARD_BTN_BASE.getHeight() * Settings.scale * 0.5f) - (20.0f * Settings.scale);
        this.vX = MathUtils.random(-250.0f,250.0f) * Settings.scale;
        this.vY = MathUtils.random(0.0f,110.0f - Math.abs(vX/15f)) * Settings.scale;
        this.timeUntilImgChange = MathUtils.random(0.1f,0.25f);

    }

    private TextureAtlas.AtlasRegion getImg() {
        switch(MathUtils.random(0, 2)) {
            case 0:
                return ImageMaster.TORCH_FIRE_1;
            case 1:
                return ImageMaster.TORCH_FIRE_2;
            default:
                return ImageMaster.TORCH_FIRE_3;
        }
    }

    public void update() {
        this.x += this.vX * Gdx.graphics.getDeltaTime();
        this.y += this.vY * Gdx.graphics.getDeltaTime();
        this.vX = MathUtils.lerp(this.vX, 0, Gdx.graphics.getDeltaTime() * 3.4f);
        this.vY = MathUtils.lerp(this.vY, 0, Gdx.graphics.getDeltaTime() * 0.8f);

        this.duration -= Gdx.graphics.getDeltaTime();
        this.timeUntilImgChange -= Gdx.graphics.getDeltaTime();
        if(timeUntilImgChange <= 0){
            timeUntilImgChange += MathUtils.random(0.1f,0.25f);
            this.img = getImg();
        }
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

        if(this.duration < EFFECT_DUR/2f) {
            this.color.a = this.duration/(EFFECT_DUR/2f);
            this.color.g = Math.min(this.duration/(EFFECT_DUR/2f),this.color.g);
        }
    }

    public void render(SpriteBatch sb) {

        sb.setBlendFunction(770, 1);
        sb.setColor(this.color);
        sb.draw(img, this.x - (float)(img.packedWidth / 2), this.y - (float)(img.packedHeight / 2) - 24.0F * Settings.scale, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight, this.scale, this.scale, this.rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
        System.out.println("disposed flame!");
    }
}
