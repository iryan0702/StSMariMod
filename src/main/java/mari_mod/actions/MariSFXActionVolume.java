//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class MariSFXActionVolume extends AbstractGameAction {
    private String key;
    private float volume = 0.0F;

    public MariSFXActionVolume(String key) {
        this.key = key;
        this.actionType = ActionType.WAIT;
    }

    public MariSFXActionVolume(String key, float volume) {
        this.key = key;
        this.volume = volume;
        this.actionType = ActionType.WAIT;
    }

    public void update() {
        CardCrawlGame.sound.playV(this.key, this.volume);
        this.isDone = true;
    }
}
