package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.actions.MariSuccessfulKindleAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Defend extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Defend.class.getName());
    public static final String ID = "MariMod:Mari_Defend";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int BLOCK = 4;
    private static final int UPGRADE_BLOCK = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Mari_Defend(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseBlock = BLOCK;
        this.block =  this.baseBlock;
        this.tags.add(CardTags.STARTER_DEFEND);
        this.isAnyTarget = true;
        this.isKindle = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCreature target = null;
        if(m != null) {
            target = m;
        }else if(this.target == CardTarget.SELF){
            target = p;
        }
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p,p,this.block, true));

        AbstractDungeon.actionManager.addToBottom(new MariSuccessfulKindleAction(target, new GainBlockAction(p,p,this.block, true), this));

    }



    @Override
    public AbstractCard makeCopy() {
        return new Mari_Defend();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
        }
    }
}