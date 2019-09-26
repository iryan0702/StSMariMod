package mari_mod.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import mari_mod.actions.MariHeavyMetalAction;
import mari_mod.actions.MariHeavyPriceAction;
import mari_mod.actions.MariSFXActionVolume;
import mari_mod.actions.MariWaitAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Heavy_Metal extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Heavy_Metal.class.getName());
    public static final String ID = "MariMod:Mari_Heavy_Metal";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final int DAMAGE_AMT = 12;
    private static final int UPGRADE_DAMAGE_AMT = 4;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    public Mari_Heavy_Metal(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseDamage = DAMAGE_AMT;
        this.damage = this.baseDamage;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new MariSFXActionVolume("MariMod:MariHeavyMetal",  0.65F));
        AbstractDungeon.actionManager.addToBottom(new MariWaitAction(0.15F));
        if (Settings.FAST_MODE) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Color.PURPLE, ShockWaveEffect.ShockWaveType.CHAOTIC), 0.3F));
        } else {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Color.PURPLE, ShockWaveEffect.ShockWaveType.CHAOTIC), 1.5F));
        }
        AbstractDungeon.actionManager.addToBottom(new MariHeavyMetalAction(m, this.baseDamage)); //POWERS APPLY IN ACTION
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Heavy_Metal();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE_AMT);
            this.initializeDescription();
        }
    }
}