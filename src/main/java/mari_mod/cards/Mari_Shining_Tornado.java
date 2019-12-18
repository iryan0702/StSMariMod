package mari_mod.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.unique.BouncingFlaskAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.PotionBounceEffect;
import mari_mod.actions.MariShiningTornadoAction;
import mari_mod.actions.MariStartShiningTornadoAction;
import mari_mod.effects.MariShiningTornadoEffect;
import mari_mod.effects.MariShiningTornadoFirstTossEffect;
import mari_mod.patches.EphemeralCardPatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Shining_Tornado extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Strike.class.getName());
    public static final String ID = "MariMod:Mari_Shining_Tornado";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final int ATTACK_DMG = 2;
    private static final int RADIANCE_AMOUNT = 2;
    private static final int BOUNCE_AMOUNT = 3;
    private static final int DAMAGE_UPGRADE = 2;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    public Mari_Shining_Tornado(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(MariCustomTags.RADIANCE);
        this.tags.add(MariCustomTags.QUOTATIONS);

        this.baseDamage = ATTACK_DMG;
        this.damage = this.baseDamage;

        this.baseRadiance = RADIANCE_AMOUNT;
        this.radiance = this.baseRadiance;

        this.baseMagicNumber = BOUNCE_AMOUNT;
        this.magicNumber = this.baseMagicNumber;

        EphemeralCardPatch.EphemeralField.ephemeral.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new MariStartShiningTornadoAction(BOUNCE_AMOUNT, this.damage, this.radiance));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Shining_Tornado();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(DAMAGE_UPGRADE);
        }
    }

    @Override
    public float getTitleFontSize()
    {
        return 16;
    }
}