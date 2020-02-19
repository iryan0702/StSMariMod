package mari_mod.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.MariMod;
import mari_mod.actions.*;
import mari_mod.patches.EphemeralCardPatch;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Debut extends AbstractMariCard implements OnRecallCard{
    public static final Logger logger = LogManager.getLogger(Mari_Debut.class.getName());
    public static final String ID = "MariMod:Mari_Debut";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;
    //private static final int BASE_GOLD_COST = 10;
    private static final int BASE_RADIANCE = 3;
    //private static final int RADIANCE_UPGRADE = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ALL;

    public Mari_Debut(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(MariCustomTags.RADIANCE);
        this.baseRadiance = BASE_RADIANCE;
        this.radiance = this.baseRadiance;
        //this.baseGoldCost = BASE_GOLD_COST;
        //this.goldCost = this.baseGoldCost;
        EphemeralCardPatch.EphemeralField.ephemeral.set(this, true);
        this.isInnate = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //AbstractDungeon.actionManager.addToBottom(new MariSpendGoldAction(this.goldCost));
        addToTop(new ApplyPowerAction(p, p, new Radiance_Power(p, this.radiance), this.radiance));
        addToTop(new MariDebutAction(this.radiance, false));
        //addToBot(new MariDebutAction(this.radiance, false));
        //AbstractDungeon.actionManager.addToBottom(new MariDebutAction(this.radiance, false));
        //addToBot(new ModifyRadianceAction(this.uuid, RADIANCE_DECAY));
    }

    @Override
    public void onRecall() {
        AbstractPlayer p = AbstractDungeon.player;
        this.purgeOnUse = true;
        this.setAngle(0f, true);
        addToTop(new NewQueueCardAction(this, null));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Debut();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeBaseCost(UPGRADE_COST);
            //this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
            upgradeName();
        }
    }
}