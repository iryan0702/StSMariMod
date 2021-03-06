//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class MariEphemeralExhaustEffect extends AbstractGameEffect {
    private static final float DUR = 0.25F;
    private boolean darkFlame = false;

    public MariEphemeralExhaustEffect() {
        this(false);
    }

    public MariEphemeralExhaustEffect(boolean darkFlame) {
        this.darkFlame = darkFlame;
        this.duration = DUR;
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            CardCrawlGame.sound.playAV("ATTACK_FLAME_BARRIER", 0.2F, 0.5f);
            CardCrawlGame.sound.play("CARD_EXHAUST", 0.2F);

            int i;
            for(i = 0; i < 90; ++i) {
                AbstractDungeon.effectsQueue.add(new MariEphemeralExhaustFlameEffect(this.darkFlame));
            }
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
