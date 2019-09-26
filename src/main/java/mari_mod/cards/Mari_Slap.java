package mari_mod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import mari_mod.actions.MariDefianceAction;
import mari_mod.actions.MariSlapAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Slap extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Slap.class.getName());
    public static final String ID = "MariMod:Mari_Slap";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 1;
    private static final int DAMAGE = 9;
    private static final int UPGRADE_DAMAGE = 3;
    private static final int SCALING = 6;
    private static final int UPGRADE_SCALING = 1;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public Mari_Slap(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseBlock = SCALING;
        this.block = this.baseBlock;

        this.baseDamage = DAMAGE;
        this.damage = this.baseDamage;

        this.baseMagicNumber = DAMAGE;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) { //TODO: FIX INTERACTION WITH STEWSHINE

        /*int scaling = SCALING;
        if(upgraded) scaling += UPGRADE_SCALING;

        int dam = DAMAGE;
        if(upgraded) dam += UPGRADE_DAMAGE;

        AbstractDungeon.actionManager.addToBottom(new MariSlapAction(m,dam,scaling));
        */
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));

    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        forgetBlock();
    }

    @Override
    public void applyPowers() {

        int scaling = SCALING;
        if(upgraded) scaling += UPGRADE_SCALING;

        int dam = DAMAGE;
        if(upgraded) dam += UPGRADE_DAMAGE;

        int vulnStacks = 0;
        if(AbstractDungeon.player.hasPower(VulnerablePower.POWER_ID)) vulnStacks += AbstractDungeon.player.getPower(VulnerablePower.POWER_ID).amount;


        this.baseDamage = dam + scaling * vulnStacks;
        this.damage =  this.baseDamage;

        super.applyPowers();

        if(this.damage > 0) {
            String desc;
            if(upgraded){
                desc = UPGRADE_DESCRIPTION;
            }else{
                desc = DESCRIPTION;
            }
            this.rawDescription = desc + EXTENDED_DESCRIPTION[0];
            this.initializeDescription();
        }

        forgetBlock();
    }

    public void forgetBlock(){
        this.baseBlock = SCALING;
        if(this.upgraded) this.baseBlock += UPGRADE_SCALING;
        this.block = this.baseBlock;
        this.isBlockModified = false;
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_DAMAGE);
            upgradeBlock(UPGRADE_SCALING);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}