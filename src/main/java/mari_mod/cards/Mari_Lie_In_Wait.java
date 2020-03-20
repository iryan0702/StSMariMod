package mari_mod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.actions.MariSuccessfulKindleAction;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Lie_In_Wait extends AbstractMariCard implements OnRecallCard {
    public static final Logger logger = LogManager.getLogger(Mari_Lie_In_Wait.class.getName());
    public static final String ID = "MariMod:Mari_Lie_In_Wait";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int BLOCK = 10;
    private static final int UPGRADE_BLOCK = 2;
    private static final int DAMAGE = 12;
    private static final int UPGRADE_DAMAGE = 3;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Mari_Lie_In_Wait(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseMagicNumber = DAMAGE;
        this.magicNumber = this.baseMagicNumber;
        this.baseBlock = BLOCK;
        this.block = this.baseBlock;
        this.exhaust = true;
        this.tags.add(MariCustomTags.KINDLE);
        this.isAnyTarget = true;
        tags.add(MariCustomTags.GLARING);
    }

    @Override
    public void onRecall() {
        addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(this.magicNumber, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCreature target;
        if(m != null) {
            target = m;
        }else{
            target = p;
        }

        //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Lie_In_Wait_Power(p, this.magicNumber), this.magicNumber));
        if(target.hasPower(Radiance_Power.POWER_ID) && target.getPower(Radiance_Power.POWER_ID).amount >= 1){
            this.successfulKindle(target);
        }
        AbstractDungeon.actionManager.addToBottom(new MariSuccessfulKindleAction(target, new GainBlockAction(p,p,this.block, true)));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_DAMAGE);
            upgradeBlock(UPGRADE_BLOCK);
        }
    }
}