package mari_mod.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

//Code courtesy of Johnnydevo #5188
public class FBHelper {
    public static FrameBuffer createBuffer() {
        return new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false, false);
    }

    public static void beginBuffer(FrameBuffer fbo) {
        fbo.begin();
        Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glColorMask(true,true,true,true);
    }

    public static TextureRegion getBufferTexture(FrameBuffer fbo) {
        TextureRegion texture = new TextureRegion(fbo.getColorBufferTexture());
        texture.flip(false, true);
        return texture;
    }
}
