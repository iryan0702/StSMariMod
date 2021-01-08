package mari_mod.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.actions.MariSpendGoldAction;
import mari_mod.actions.MariTheHelicopterAction;
import mari_mod.effects.MariHelicopterEffect;
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
    private static final int ATTACK_DMG = 8;
    private static final int ATTACK_DMG_UHH = -2;
    private static final int ATTACK_TIMES = 4;
    private static final int ATTACK_TIME_UPGRADE = 2;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    public Mari_The_HELICOPTER(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(MariCustomTags.SPEND);
        this.baseDamage = ATTACK_DMG;
        this.baseGoldCost = BASE_GOLD_COST;
        this.baseMagicNumber = ATTACK_TIMES;
        this.magicNumber = this.baseMagicNumber;
        this.goldCost = this.baseGoldCost;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new MariSpendGoldAction(this));

        this.magicNumber = ATTACK_TIMES;
        if(this.upgraded) this.magicNumber += ATTACK_TIME_UPGRADE;
        float averageX = 0.0f;
        float averageY = 0.0f;
        int monsters = 0;
        for(AbstractMonster mo: AbstractDungeon.getCurrRoom().monsters.monsters){
            if(!mo.halfDead && !mo.isDead){
                averageX += mo.hb.cX;
                averageY += mo.hb.cY;
                monsters++;
            }
        }
        averageX/=monsters;
        averageY/=monsters;
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new MariHelicopterEffect(averageX, averageY),1.3F));
        AbstractDungeon.actionManager.addToBottom(new MariTheHelicopterAction(this.magicNumber,this.baseDamage));

    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_The_HELICOPTER();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(ATTACK_DMG_UHH);
            upgradeMagicNumber(ATTACK_TIME_UPGRADE);
        }
    }
}