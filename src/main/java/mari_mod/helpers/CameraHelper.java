package mari_mod.helpers;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;

public class CameraHelper {

    public static void setCameraCentered(float xInProportionWidth, float yInProportionHeight){
        int screenWidth = CardCrawlGame.viewport.getScreenWidth();
        int screenHeight = CardCrawlGame.viewport.getScreenHeight();
        float widthProportion = screenWidth/(float) Settings.M_W;
        float heightProportion = screenHeight/(float) Settings.M_H;
        float startWidth = 0.5f - 1.0f/(widthProportion * 2.0f);
        float startHeight = 0.5f - 1.0f/(heightProportion * 2.0f);

        CardCrawlGame.viewport.getCamera().position.x = Settings.M_W * (startWidth + xInProportionWidth);
        CardCrawlGame.viewport.getCamera().position.y = Settings.M_H * (startHeight + yInProportionHeight);
        CardCrawlGame.viewport.update(screenWidth,screenHeight);
    }

    public static void setCameraScale(float scale){
        setCameraScale(scale,scale);
    }

    public static void setCameraScale(float scaleWidth, float scaleHeight){
        CardCrawlGame.viewport.update((int) (Settings.M_W * scaleWidth),(int) (Settings.M_H * scaleHeight));
    }
    public static void resetCamera(){
        CardCrawlGame.viewport.getCamera().position.x = Settings.M_W * 0.5f;
        CardCrawlGame.viewport.getCamera().position.y = Settings.M_H * 0.5f;
        CardCrawlGame.viewport.update(Settings.M_W,Settings.M_H);
    }

    public static void printStatForTest(){
        System.out.println("Screen width: " + CardCrawlGame.viewport.getScreenWidth());
        System.out.println("Screen height: " + CardCrawlGame.viewport.getScreenHeight());
        System.out.println("Window width: " + Settings.M_W);
        System.out.println("Window height: " + Settings.M_H);
        System.out.println("Camera x: " + CardCrawlGame.viewport.getCamera().position.x);
        System.out.println("Camera y: " + CardCrawlGame.viewport.getCamera().position.y);
    }
}
