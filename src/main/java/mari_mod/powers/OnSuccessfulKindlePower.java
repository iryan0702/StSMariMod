package mari_mod.powers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;

public interface OnSuccessfulKindlePower
{
    public void onSuccessfulKindle(AbstractPlayer player, AbstractCreature kindleTarget);
}
