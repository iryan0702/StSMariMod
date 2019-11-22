package mari_mod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GrandFinalEffect;
import mari_mod.MariMod;
import mari_mod.effects.PerfectPerformanceEffect;
import mari_mod.patches.MariMusicalAttackEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class Mari_Perfect_Performance extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Perfect_Performance.class.getName());
    public static final String ID = "MariMod:Mari_Perfect_Performance";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 0;
    private static final int DAMAGE_AMT = 40;
    private static final int UPGRADE_DAMAGE_AMT = 10;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    public Mari_Perfect_Performance(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseDamage = DAMAGE_AMT;
        this.damage = this.baseDamage;
        this.isMultiDamage = true;

        String desc;
        if(Settings.language.equals(Settings.GameLanguage.ZHS)){
            desc = EXTENDED_DESCRIPTION[3];
        }else {
            desc = DESCRIPTION;
            if (MariMod.played1Cost) {
                desc += "[#7fff00]";
            } else {
                desc += "[#ff6563]";
            }
            desc += EXTENDED_DESCRIPTION[0];
            if (MariMod.played2Cost) {
                desc += "[#7fff00]";
            } else {
                desc += "[#ff6563]";
            }
            desc += EXTENDED_DESCRIPTION[1];
            if (MariMod.played3Cost) {
                desc += "[#7fff00]";
            } else {
                desc += "[#ff6563]";
            }
            desc += EXTENDED_DESCRIPTION[2];
        }
        this.rawDescription = desc;
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new PerfectPerformanceEffect(), 1.0F));
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, DamageInfo.DamageType.NORMAL, MariMusicalAttackEffect.MUSICAL));

    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.retain = true;
        String desc;
        if(Settings.language.equals(Settings.GameLanguage.ZHS)){
            desc = EXTENDED_DESCRIPTION[3];
        }else {
            desc = DESCRIPTION;
            if (MariMod.played1Cost) {
                desc += "[#7fff00]";
            } else {
                desc += "[#ff6563]";
            }
            desc += EXTENDED_DESCRIPTION[0];
            if (MariMod.played2Cost) {
                desc += "[#7fff00]";
            } else {
                desc += "[#ff6563]";
            }
            desc += EXTENDED_DESCRIPTION[1];
            if (MariMod.played3Cost) {
                desc += "[#7fff00]";
            } else {
                desc += "[#ff6563]";
            }
            desc += EXTENDED_DESCRIPTION[2];
        }
        this.rawDescription = desc;
        this.initializeDescription();
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);

        if(!canUse){
            return false;
        }
        canUse = MariMod.perfectPerformance;
        if(!canUse){
            this.cantUseMessage = UPGRADE_DESCRIPTION;
        }
        return canUse;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Perfect_Performance();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE_AMT);
        }
    }

    @Override
    public float getTitleFontSize()
    {
        return 20;
    }
}