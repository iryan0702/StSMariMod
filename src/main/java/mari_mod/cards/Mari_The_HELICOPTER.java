package mari_mod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.actions.MariPreHelicopterAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public class Mari_The_HELICOPTER extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_The_HELICOPTER.class.getName());
    public static final String ID = "MariMod:Mari_The_HELICOPTER";
    private static final CardStrings cardStrings = languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 3;
    private static final int BASE_GOLD_COST = 15;
    private static final int ATTACK_DMG = 18;
    private static final int ATTACK_DMG_UPGRADE = 6;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public Mari_The_HELICOPTER(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(MariCustomTags.SPEND);
        this.baseDamage = ATTACK_DMG;
        this.baseGoldCost = BASE_GOLD_COST;
        this.goldCost = this.baseGoldCost;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
//        AbstractDungeon.actionManager.addToBottom(new MariInvestGoldAction(this));

        AbstractDungeon.actionManager.addToBottom(new MariPreHelicopterAction(this.damage, m));

    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_The_HELICOPTER();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(ATTACK_DMG_UPGRADE);
        }
    }
}