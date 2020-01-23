package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.patches.EphemeralCardPatch;
import mari_mod.powers.Practice_Outfit_Buff_Power;
import mari_mod.powers.Practice_Outfit_Debuff_Power;
import mari_mod.powers.Supervision_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Practice_Outfit extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Practice_Outfit.class.getName());
    public static final String ID = "MariMod:Mari_Practice_Outfit";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 3;
    private static final int BLOCK = 7;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Mari_Practice_Outfit(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        EphemeralCardPatch.EphemeralField.ephemeral.set(this, true);
        this.baseBlock = BLOCK;
        this.block = this.baseBlock;
        isAnyTarget = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCreature target;
        if(m != null) {
            target = m;
        }else{
            target = p;
        }
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(target,target,this.block, true));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new Practice_Outfit_Buff_Power(target, 1), 1));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
        }
    }
}