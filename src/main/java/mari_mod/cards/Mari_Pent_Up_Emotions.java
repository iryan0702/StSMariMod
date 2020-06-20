package mari_mod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import mari_mod.actions.MariReducePowerIfHavePowerAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Pent_Up_Emotions extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Pent_Up_Emotions.class.getName());
    public static final String ID = "MariMod:Mari_Pent_Up_Emotions";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final int DAMAGE = 11;
    private static final int UPGRADE_DAMAGE = 3;
    private static final int ENERGY_GAIN = 1;
    private static final int UNFRAIL_AMOUNT = 1;
    private static final int UNFRAIL_AMOUNT_UPGRADE = 1;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public Mari_Pent_Up_Emotions(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.damage = this.baseDamage;

        this.baseMagicNumber = UNFRAIL_AMOUNT;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p,p,new FrailPower(p, 1, false), 1));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(ENERGY_GAIN));
        AbstractDungeon.actionManager.addToBottom(new MariReducePowerIfHavePowerAction(p,p,FrailPower.POWER_ID,this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m,new DamageInfo(p,this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
            upgradeMagicNumber(UNFRAIL_AMOUNT_UPGRADE);
        }
    }
}