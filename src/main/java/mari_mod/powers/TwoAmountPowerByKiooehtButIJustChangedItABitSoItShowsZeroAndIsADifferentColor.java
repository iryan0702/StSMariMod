//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;

public abstract class TwoAmountPowerByKiooehtButIJustChangedItABitSoItShowsZeroAndIsADifferentColor extends AbstractPower {
    public int amount2 = 0;
    public boolean canGoNegative2 = false;
    protected Color redColor2;
    protected Color greenColor2;
    public Color displayColor2;

    public TwoAmountPowerByKiooehtButIJustChangedItABitSoItShowsZeroAndIsADifferentColor() {
        this.redColor2 = Color.RED.cpy();
        this.greenColor2 = Color.GREEN.cpy();
        this.displayColor2 = Color.WHITE.cpy();
    }

    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        super.renderAmount(sb, x, y, c);
        if (this.amount2 >= 0) {
            if (!this.isTurnBased) {
                this.displayColor2.a = c.a;
                c = this.displayColor2;
            }

            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.amount2), x, y + 15.0F * Settings.scale, this.fontScale, c);
        } else if (this.amount2 < 0 && this.canGoNegative2) {
            this.displayColor2.a = c.a;
            c = this.displayColor2;
            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.amount2), x, y + 15.0F * Settings.scale, this.fontScale, c);
        }

    }
}
