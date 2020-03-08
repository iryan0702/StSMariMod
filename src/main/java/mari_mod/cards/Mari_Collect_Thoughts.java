package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import mari_mod.actions.MariCollectThoughtsAction;
import mari_mod.actions.MariSuccessfulKindleAction;
import mari_mod.actions.MariUnsuccessfulKindleAction;
import mari_mod.patches.EphemeralCardPatch;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Collect_Thoughts extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Collect_Thoughts.class.getName());
    public static final String ID = "MariMod:Mari_Collect_Thoughts";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Mari_Collect_Thoughts(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.isAnyTarget = true;
        this.tags.add(MariCustomTags.KINDLE);
        EphemeralCardPatch.EphemeralField.ephemeral.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {



        AbstractCreature target;
        if(m != null) {
            target = m;
        }else{
            target = p;
        }
        if(target.hasPower(Radiance_Power.POWER_ID) && target.getPower(Radiance_Power.POWER_ID).amount >= 1){
            this.successfulKindle(target);
        }

        AbstractDungeon.actionManager.addToBottom(new MariUnsuccessfulKindleAction(target, new MariCollectThoughtsAction(false)));
        AbstractDungeon.actionManager.addToBottom(new MariSuccessfulKindleAction(target, new MariCollectThoughtsAction(true)));

    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Collect_Thoughts();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeBaseCost(UPGRADE_COST);
            upgradeName();
        }
    }
}