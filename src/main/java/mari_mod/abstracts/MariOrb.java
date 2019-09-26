package mari_mod.abstracts;

import basemod.abstracts.CustomEnergyOrb;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import mari_mod.MariMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;



public class MariOrb extends CustomEnergyOrb {

    public static final Logger logger = LogManager.getLogger(MariOrb.class.getName());

    private static final float[] layerSpeeds = new float[] {-10.0F, 5.0F, 15.0F, -20.0F, 0.0F};

    ArrayList<CircleKeeper> starList;
    private TextureAtlas.AtlasRegion starImg;
    private float starTimer;

    public MariOrb(String[] orbTexturePaths, String orbVfxPath)
    {

        super(orbTexturePaths, orbVfxPath, layerSpeeds);
        starList = new ArrayList<>();
        Texture star = ImageMaster.loadImage("mari_mod/images/effects/MariOrbStar.png");
        starImg = ImageMaster.vfxAtlas.addRegion("MariOrbStar", star, 0,0,256,256);

        this.starTimer = 1.0F;

    }

    @Override
    public void updateOrb(int energyCount) {
        super.updateOrb(energyCount);

        starTimer -= Gdx.graphics.getDeltaTime();

        ArrayList<Color> possibleColors = new ArrayList<>();
        switch (energyCount) {
            default:
            case 9: possibleColors.add(Color.SALMON.cpy());
            case 8: possibleColors.add(Color.CYAN.cpy());
            case 7: possibleColors.add(Color.ORANGE.cpy());
            case 6: possibleColors.add(Color.GRAY.cpy());
            case 5: possibleColors.add(Color.PINK.cpy());
            case 4: possibleColors.add(Color.YELLOW.cpy());
            case 3: possibleColors.add(Color.RED.cpy());
            case 2: possibleColors.add(Color.TEAL.cpy());
            case 1: possibleColors.add(Color.PURPLE.cpy());
        }
        if(starTimer <= 0 && energyCount > 0){

            starTimer = MathUtils.random(7.2F/energyCount, 9.6F/energyCount);
            starList.add(new CircleKeeper((angles[3]/180.0F * MathUtils.PI) - (MathUtils.PI/2.0F) + MathUtils.random(MathUtils.PI/-3.0F,MathUtils.PI/3.0F),MathUtils.random(10,50), possibleColors.get(MathUtils.random(0,possibleColors.size()-1)), 0.3F));
        }

        for(CircleKeeper c: starList){
            c.alpha -= 0.1 * Gdx.graphics.getDeltaTime();
            c.theta += layerSpeeds[1] * Gdx.graphics.getDeltaTime() /60.0F;
        }

        starList.removeIf(CircleKeeper::isDead);
    }

    @Override
    public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y) {
        super.renderOrb(sb, enabled, current_x, current_y);
        for(CircleKeeper c: starList){
            c.calculateValues(current_x,current_y);
            sb.setBlendFunction(770, 1);
            sb.setColor(c.color);
            sb.draw(starImg, c.posX - starImg.packedWidth/2.0F, c.posY - starImg.packedHeight/2.0F, starImg.packedWidth/2.0F, starImg.packedHeight/2.0F, starImg.packedWidth, starImg.packedHeight, Settings.scale * c.scale, Settings.scale * c.scale, c.theta);
            sb.draw(starImg, c.posX - starImg.packedWidth/2.0F, c.posY - starImg.packedHeight/2.0F, starImg.packedWidth/2.0F, starImg.packedHeight/2.0F, starImg.packedWidth, starImg.packedHeight, Settings.scale * c.scale, Settings.scale * c.scale, c.theta);
            sb.setBlendFunction(770, 771);
        }
    }

    public class CircleKeeper{

        public float centerX;
        public float centerY;
        public float theta;
        public float r;

        public float posX;
        public float posY;
        public float scale;

        public Color color;
        public float alpha;
        public boolean isDead;

        public CircleKeeper(float startingTheta, float startingR, Color color, float scale){
            this.theta = startingTheta;
            this.r = startingR;
            this.color = color;
            this.scale = scale;
            this.alpha = 1.0F;
            this.isDead = false;
        }

        public void calculateValues(float centerX, float centerY){
            this.centerX = centerX;
            this.centerY = centerY;

            posX = centerX + MathUtils.cos(theta) * r * Settings.scale;
            posY = centerY + MathUtils.sin(theta) * r * Settings.scale;

            this.scale = MathUtils.random(0.22F,0.25F);

            this.color.a = this.alpha;
            if(alpha < 0){
                isDead = true;
            }
        }

        public boolean isDead() {
            return isDead;
        }
    }

    public class CloudKeeper{

    }
}