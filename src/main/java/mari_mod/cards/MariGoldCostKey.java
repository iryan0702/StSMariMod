package mari_mod.cards;

import basemod.abstracts.DynamicVariable;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

public class MariGoldCostKey extends DynamicVariable {
    @Override
    public String key() {
        return "MARI_GOLD_COST";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractMariCard) card).goldCost != ((AbstractMariCard) card).baseGoldCost;
    }

    @Override
    public void setIsModified(AbstractCard card, boolean v) {
        ((AbstractMariCard) card).upgradedGoldCost = v;
        // Do something such that isModified will return the value v.
        // This method is only necessary if you want smith upgrade previews to function correctly.
    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractMariCard) card).goldCost;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractMariCard) card).baseGoldCost;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractMariCard) card).upgradedGoldCost;
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
        return Settings.GREEN_TEXT_COLOR;
    }

    public Color getDecreasedValueColor()
    {
        return Settings.RED_TEXT_COLOR;
    }
}
