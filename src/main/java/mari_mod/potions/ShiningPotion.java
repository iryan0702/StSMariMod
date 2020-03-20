//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import mari_mod.powers.Radiance_Power;

public class ShiningPotion extends AbstractPotion {
    public static final String POTION_ID = "MariMod:ShiningPotion";
    private static final PotionStrings potionStrings;

    public ShiningPotion() {
        super(potionStrings.NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.SPIKY, PotionEffect.NONE, new Color(0.9f,0.6f,0,1), new Color(1,1,0.5f,1), (Color)null);
        this.isThrown = true;
    }

    public void initializeData() {
        this.potency = this.getPotency();
        this.description = potionStrings.DESCRIPTIONS[0] + this.potency + potionStrings.DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        //this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.THORNS.NAMES[0]), (String)GameDictionary.keywords.get(GameDictionary.THORNS.NAMES[0])));
    }

    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;
        if (AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT) {
            for(int i = 0; i < 2; i++) {
                this.addToBot(new ApplyPowerAction(p, p, new Radiance_Power(p, this.potency), this.potency));
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    this.addToBot(new ApplyPowerAction(m, p, new Radiance_Power(m, this.potency), this.potency));
                }
            }
        }

    }

    public int getPotency(int ascensionLevel) {
        return 2;
    }

    public AbstractPotion makeCopy() {
        return new ShiningPotion();
    }

    static {
        potionStrings = CardCrawlGame.languagePack.getPotionString("MariMod:ShiningPotion");
    }
}
