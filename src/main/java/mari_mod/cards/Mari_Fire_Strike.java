package mari_mod.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import mari_mod.actions.MariFireStrikeAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Fire_Strike extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Fire_Strike.class.getName());
    public static final String ID = "MariMod:Mari_Fire_Strike";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = -1;
    private static final int RADIANCE_PER_ENERGY = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public Mari_Fire_Strike(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(CardTags.STRIKE);
        this.tags.add(MariCustomTags.RADIANCE);
        this.baseRadiance = RADIANCE_PER_ENERGY;
        this.radiance = this.baseRadiance;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.energyOnUse < EnergyPanel.totalCount) {
            this.energyOnUse = EnergyPanel.totalCount;
        }
        AbstractDungeon.actionManager.addToBottom(new MariFireStrikeAction(p, m, this.energyOnUse, this.upgraded, this.freeToPlayOnce, this.radiance));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Fire_Strike();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
            upgradeName();
        }
    }
}