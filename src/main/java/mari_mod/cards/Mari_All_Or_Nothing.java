package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.unique.ReprogramAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.MariMod;
import mari_mod.actions.*;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_All_Or_Nothing extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_All_Or_Nothing.class.getName());
    public static final String ID = "MariMod:Mari_All_Or_Nothing";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 0;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Mari_All_Or_Nothing(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(MariCustomTags.QUOTATIONS);
        this.isAnyTarget = true;
        this.tags.add(MariCustomTags.KINDLE);
        this.exhaust = true;
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

        AbstractDungeon.actionManager.addToBottom(new MariUnsuccessfulKindleAction(target, new MariAllOrNothingAction(false, this)));
        AbstractDungeon.actionManager.addToBottom(new MariSuccessfulKindleAction(target, new MariAllOrNothingAction(true, this)));

        if(this.upgraded){
            AbstractDungeon.actionManager.addToBottom(new MariDowngradeCardAction(this, NAME, DESCRIPTION, true));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_All_Or_Nothing();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.exhaust = false;
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    public void publicInitializeTitle() {
        this.initializeTitle();
    }
}