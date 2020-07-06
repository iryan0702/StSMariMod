package mari_mod.cards;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import mari_mod.actions.MariHeavyMetalAction;
import mari_mod.actions.MariSFXActionVolume;
import mari_mod.actions.MariWaitAction;
import mari_mod.characters.IconBubble;
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
    private static final int VULN = 1;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Mari_Heavy_Metal(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseDamage = DAMAGE_AMT;
        this.damage = this.baseDamage;
        this.baseMagicNumber = this.magicNumber = VULN;

        this.isAnyTarget = true;
        CardModifierManager.addModifier(this, new HeavyMetalModifier());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCreature target;
        if(m != null) {
            target = m;
        }else{
            target = p;
        }

        AbstractDungeon.actionManager.addToBottom(new MariSFXActionVolume("MariMod:MariHeavyMetal",  0.65F));
        AbstractDungeon.actionManager.addToBottom(new MariWaitAction(0.15F));
        if (Settings.FAST_MODE) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Color.PURPLE, ShockWaveEffect.ShockWaveType.CHAOTIC), 0.3F));
        } else {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Color.PURPLE, ShockWaveEffect.ShockWaveType.CHAOTIC), 1.5F));
        }
        AbstractDungeon.actionManager.addToBottom(new MariHeavyMetalAction(target, this.magicNumber, this));

        IconBubble.playAnimationWithCheck(IconBubble.animation.MUSIC);
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

    public static class HeavyMetalModifier extends AbstractCardModifier {

        private HeavyMetalModifier(){
        }

        @Override
        public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
            AbstractPower p = AbstractDungeon.player.getPower(VulnerablePower.POWER_ID);
            if(p == null) {
                return damage;
            }else{
                return p.atDamageReceive(damage, type);
            }
        }

        @Override
        public boolean isInherent(AbstractCard card) {
            return true;
        }

        @Override
        public AbstractCardModifier makeCopy() {
            return new HeavyMetalModifier();
        }
    }
}