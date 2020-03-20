package mari_mod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Drama2 extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Drama2.class.getName());
    public static final String ID = "MariMod:Mari_Drama2";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK = 6;
    private static final int BLOCK = 6;
    private static final int ATTACK_UP = 2;
    private static final int BLOCK_UP = 2;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public Mari_Drama2(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(MariCustomTags.DRAMA);
        this.baseDamage = ATTACK;
        this.damage = this.baseDamage;
        this.baseBlock = BLOCK;
        this.block = this.baseBlock;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p,p,this.block));

        AbstractPower vuln = AbstractDungeon.player.getPower(VulnerablePower.POWER_ID);
        AbstractPower frail = AbstractDungeon.player.getPower(FrailPower.POWER_ID);
        int vulnCount = (vuln == null) ? 0 : vuln.amount;
        int frailCount = (frail == null) ? 0 : frail.amount;
        if(vulnCount != 0 || frailCount != 0){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p, new VulnerablePower(p, frailCount, false), frailCount));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p, new FrailPower(p, vulnCount, false), vulnCount));
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, vuln, vulnCount));
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, frail, frailCount));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p, new FrailPower(p, 1, false), 1));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeDamage(ATTACK_UP);
            this.upgradeBlock(BLOCK_UP);
            upgradeName();
        }
    }
}