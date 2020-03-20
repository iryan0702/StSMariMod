package mari_mod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlickCoinEffect;
import mari_mod.MariStatTracker;
import mari_mod.actions.MariWaitAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Flaunt extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Flaunt.class.getName());
    public static final String ID = "MariMod:Mari_Flaunt";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 1;
    private static final int AMOUNT = 3;
    private static final int UPGRADE_AMOUNT = -1;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    public Mari_Flaunt(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseMagicNumber = AMOUNT;
        this.magicNumber = this.baseMagicNumber;

        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster monsta) {
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!m.halfDead && !m.isDead) {
                AbstractDungeon.effectList.add(new FlickCoinEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY));
            }
        }
        this.addToBot(new MariWaitAction(0.3F));
        this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Flaunt_Power(p, this.magicNumber), this.magicNumber));
    }

    public int getCleaveDamage(){
        int damage = 0;
        int latestIndex = MariStatTracker.investAmountsThisCombat.size() - 1;
        if(this.magicNumber <= MariStatTracker.investAmountsThisCombat.size()){
            damage = Math.min(MariStatTracker.investAmountsThisCombat.get(latestIndex), MariStatTracker.investAmountsThisCombat.get(latestIndex-1));
            if(this.magicNumber == 3){
                damage = Math.min(damage, MariStatTracker.investAmountsThisCombat.get(latestIndex-2));
            }
        }
        return damage;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        this.baseDamage = getCleaveDamage();
        super.calculateCardDamage(mo);
    }

    @Override
    public void applyPowers() {
        this.baseDamage = getCleaveDamage();
        super.applyPowers();

        if(this.damage > 0){
            this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];
            this.initializeDescription();
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_AMOUNT);
        }
    }
}