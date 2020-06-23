package mari_mod.cards;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.patches.EphemeralCardPatch;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static mari_mod.MariMod.energySpentThisTurn;

public class Mari_Sparkle extends AbstractMariCard implements OnRecallCard {
    public static final Logger logger = LogManager.getLogger(Mari_Sparkle.class.getName());
    public static final String ID = "MariMod:Mari_Sparkle";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 1;
    private static final int RADIANCE = 4;
    private static final int UPGRADE_RADIANCE = 1;
    private static final int BLOCK = 4;
    private static final int BLOCK_UPGRADE = 2;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Mari_Sparkle(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(MariCustomTags.RADIANCE);

        this.magicNumber = this.baseMagicNumber = BLOCK;
        this.radiance = this.baseRadiance = RADIANCE;

        EphemeralCardPatch.EphemeralField.ephemeral.set(this, true);
        this.isAnyTarget = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractCreature target;
        if(m != null) {
            target = m;
        }else{
            target = p;
        }
        addToBot(new ApplyPowerAction(target, p, new Radiance_Power(target, this.radiance), this.radiance));

    }

    @Override
    public void onRecall() {
        addToBot(new GainBlockAction(AbstractDungeon.player, this.magicNumber));
    }

    //    public void triggerOnGlowCheck() {
//        if (energySpentThisTurn >= this.magicNumber) {
//            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
//            this.defaultGlowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
//        } else {
//            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
//            this.defaultGlowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
//        }
//    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Sparkle();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(BLOCK_UPGRADE);
            upgradeRadiance(UPGRADE_RADIANCE);
        }
    }
}