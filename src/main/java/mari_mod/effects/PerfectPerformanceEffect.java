//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.effects;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.PetalEffect;
import com.megacrit.cardcrawl.vfx.SpotlightEffect;

public class PerfectPerformanceEffect extends AbstractGameEffect {
    private float timer = 0.1F;

    public PerfectPerformanceEffect() {
        this.duration = 2.0F;
    }

    public void update() {
        if (this.duration == 2.0F) {
            AbstractDungeon.effectsQueue.add(new SpotlightEffect());
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        this.timer -= Gdx.graphics.getDeltaTime();
        if (this.timer < 0.0F) {
            this.timer += 0.1F;
            AbstractDungeon.effectsQueue.add(new ColouredPetalEffect(new Color(0.7F, MathUtils.random(0.1F, 0.2F), MathUtils.random(0.7F, 0.9F), 1.0F)));
            AbstractDungeon.effectsQueue.add(new ColouredPetalEffect(new Color(0.7F, MathUtils.random(0.1F, 0.2F), MathUtils.random(0.7F, 0.9F), 1.0F)));
        }

        if (this.duration < 0.0F) {
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
