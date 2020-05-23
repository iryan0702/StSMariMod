package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import mari_mod.actions.MariApplyRandomRadianceAction;
import mari_mod.actions.MariExhaustToHandAction;
import mari_mod.patches.EphemeralCardPatch;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Undying_Spark extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Undying_Spark.class.getName());
    public static final String ID = "MariMod:Mari_Undying_Spark";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 0;
    private static final int RADIANCE = 2;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL;

    public Mari_Undying_Spark(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(MariCustomTags.RADIANCE);
        this.baseRadiance = RADIANCE;
        this.radiance = this.baseRadiance;
        this.selfRetain = true;
        EphemeralCardPatch.EphemeralField.ephemeral.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(!this.upgraded) {
            addToBot(new MariApplyRandomRadianceAction(this.radiance, true));
        }else{
            AbstractCreature target;
            if(m != null) {
                target = m;
            }else{
                target = p;
            }
            addToBot(new ApplyPowerAction(target, p, new Radiance_Power(target, this.radiance), this.radiance));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Undying_Spark();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.isAnyTarget = true;
            this.target = CardTarget.SELF;
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}