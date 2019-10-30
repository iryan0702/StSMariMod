package mari_mod.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class IconBubble {

    public boolean animationPlaying;
    public animation currentAnimation;
    public float animationTimer;
    public float maxAnimationTimer;
    public float scale;
    public float rotation;
    public float alpha;

    public float anchorDeltaX;
    public float anchorDeltaY;

    AtlasRegion ellipses;
    AtlasRegion music;
    AtlasRegion angry;
    AtlasRegion exclamation;
    AtlasRegion sweat;
    AtlasRegion vertical;
    AtlasRegion heart;
    AtlasRegion question;

    AtlasRegion currentImage;

    public IconBubble(float deltaX, float deltaY){

        Texture ellipsesTexture = ImageMaster.loadImage("mari_mod/images/effects/Bubble1.png");
        ellipses = ImageMaster.vfxAtlas.addRegion("EllipsesBubble", ellipsesTexture, 0,0,85,90);
        Texture musicTexture = ImageMaster.loadImage("mari_mod/images/effects/Bubble2.png");
        music = ImageMaster.vfxAtlas.addRegion("MusicBubble", musicTexture, 0,0,85,90);
        Texture angryTexture = ImageMaster.loadImage("mari_mod/images/effects/Bubble3.png");
        angry = ImageMaster.vfxAtlas.addRegion("AngryBubble", angryTexture, 0,0,85,90);
        Texture exclamationTexture = ImageMaster.loadImage("mari_mod/images/effects/Bubble4.png");
        exclamation = ImageMaster.vfxAtlas.addRegion("ExclamationBubble", exclamationTexture, 0,0,85,90);
        Texture sweatTexture = ImageMaster.loadImage("mari_mod/images/effects/Bubble5.png");
        sweat = ImageMaster.vfxAtlas.addRegion("SweatBubble", sweatTexture, 0,0,85,90);
        Texture verticalTexture = ImageMaster.loadImage("mari_mod/images/effects/Bubble6.png");
        vertical = ImageMaster.vfxAtlas.addRegion("VerticalBubble", verticalTexture, 0,0,85,90);
        Texture heartTexture = ImageMaster.loadImage("mari_mod/images/effects/Bubble7.png");
        heart = ImageMaster.vfxAtlas.addRegion("HeartBubble", heartTexture, 0,0,85,90);
        Texture questionTexture = ImageMaster.loadImage("mari_mod/images/effects/Bubble8.png");
        question = ImageMaster.vfxAtlas.addRegion("QuestionBubble", questionTexture, 0,0,85,90);

        animationPlaying = false;
        currentAnimation = animation.NONE;
        anchorDeltaX = deltaX;
        anchorDeltaY = deltaY;
        scale = 0.5f;
        rotation = 0f;
        alpha = 1.0f;
    }

    public void playAnimation(animation animationType){
        if(!animationPlaying && animationType != animation.NONE){
            animationPlaying = true;
            animationTimer = 2.0f;
            maxAnimationTimer = animationTimer;
            switch(animationType){
                case ELLIPSES:
                    this.currentImage = ellipses;
                    break;
                case MUSIC:
                    this.currentImage = music;
                    break;
                case EXCLAMATION:
                    this.currentImage = exclamation;
                    break;
                case ANGRY:
                    this.currentImage = angry;
                    break;
                case SWEAT:
                    this.currentImage = sweat;
                    break;
                case VERTICAL:
                    this.currentImage = vertical;
                    break;
                case HEART:
                    this.currentImage = heart;
                    break;
                case QUESTION:
                    this.currentImage = question;
                    break;
            }
        }
    }

    public static void playAnimationWithCheck(animation animationType){
        AbstractPlayer p = AbstractDungeon.player;
        if(p instanceof Mari){
            ((Mari) p).iconBubble.playAnimation(animationType);
        }
    }

    public void update(){
        if(animationPlaying){
            if(animationTimer == maxAnimationTimer){
                this.scale = 0;
                this.alpha = 1;
            }
            animationTimer -= Gdx.graphics.getDeltaTime();
            this.scale = MathUtils.lerp(this.scale, 0.5f, Gdx.graphics.getDeltaTime() * 20.0f);

            if(animationTimer <= 1.0f){
                alpha = animationTimer/1.0f;
            }

            if(animationTimer <= 0){
                animationTimer = 0;
                animationPlaying = false;
            }
        }
    }

    public void render(SpriteBatch sb, float playerX, float playerY){
        if(this.currentImage != null && animationPlaying) {
            sb.setColor(new Color(1.0f, 1.0f, 1.0f, this.alpha));
            sb.draw(this.currentImage, this.anchorDeltaX + playerX, this.anchorDeltaY + playerY, this.currentImage.packedWidth * 0.5F, this.currentImage.packedHeight * 0.5F, this.currentImage.packedWidth, this.currentImage.packedHeight, this.scale, this.scale, this.rotation);
        }
    }

    public static enum animation{
        ELLIPSES,
        MUSIC,
        ANGRY,
        EXCLAMATION,
        SWEAT,
        VERTICAL,
        HEART,
        QUESTION,
        NONE
    }
}
