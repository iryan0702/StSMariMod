package mari_mod.cards;

import basemod.abstracts.DynamicVariable;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

public class MariRadianceKey extends DynamicVariable {
    @Override
    public String key() {
        return "MARI_RADIANCE";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractMariCard) card).radiance != ((AbstractMariCard) card).baseRadiance;
    }

    @Override
    public void setIsModified(AbstractCard card, boolean v) {
        ((AbstractMariCard) card).upgradedRadiance = v;
        // Do something such that isModified will return the value v.
        // This method is only necessary if you want smith upgrade previews to function correctly.
    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractMariCard) card).radiance;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractMariCard) card).baseRadiance;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractMariCard) card).upgradedRadiance;
    }

    public Color getNormalColor()
    {
        return Settings.CREAM_COLOR;
    }

    public Color getUpgradedColor()
    {
        return Settings.GREEN_TEXT_COLOR;
    }

    public Color getIncreasedValueColor()
    {
        return Color.GOLD.cpy();
    }

    public Color getDecreasedValueColor()
    {
        return Settings.RED_TEXT_COLOR;
    }
}
