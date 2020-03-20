package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Old_Costume extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Old_Costume.class.getName());
    public static final String ID = "MariMod:Mari_Old_Costume";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 0;
    private static final int BLOCK = 7;
    private static final int SELF_BLOCK = 3;
    private static final int VULNERABLE = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Mari_Old_Costume(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseBlock = SELF_BLOCK;
        this.block = this.baseBlock;
        this.baseMagicNumber = BLOCK;
        this.magicNumber = this.baseMagicNumber;
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
        if(m != null) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(target, p, this.magicNumber));
        }else{
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.magicNumber));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, p, new VulnerablePower(target, VULNERABLE, false), this.VULNERABLE));
        if(this.upgraded) AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
    }

    @Override
    public void applyPowers() {
        this.baseBlock = BLOCK;
        super.applyPowers();
        if(this.target == CardTarget.SELF){
            this.magicNumber = this.block;
            if(this.magicNumber != this.baseMagicNumber) {
                this.isMagicNumberModified = true;
            }
        }else{
            this.magicNumber = this.baseMagicNumber;
            this.isMagicNumberModified = false;
        }
        this.baseBlock = SELF_BLOCK;
        super.applyPowers();

        this.initializeDescription();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}