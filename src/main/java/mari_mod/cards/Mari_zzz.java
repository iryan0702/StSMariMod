package mari_mod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import mari_mod.actions.MariReducePowerIfHavePowerAction;
import mari_mod.actions.MariSuccessfulKindleAction;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class Mari_zzz extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_zzz.class.getName());
    public static final String ID = "MariMod:Mari_zzz";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final int BLOCK = 10;
    private static final int UPGRADE_BLOCK = 3;
    private static final int ENERGY_GAIN = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Mari_zzz(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseBlock = BLOCK;
        this.block = this.baseBlock;
        this.tags.add(MariCustomTags.KINDLE);
        this.isAnyTarget = true;
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p,p,new VulnerablePower(p, 1, false), 1));
        AbstractDungeon.actionManager.addToTop(new GainBlockAction(p,p,this.block));
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

        ArrayList<AbstractGameAction> things = new ArrayList<>();
        things.add((new MariReducePowerIfHavePowerAction(p,p, VulnerablePower.POWER_ID,2)));
        things.add((new GainEnergyAction(ENERGY_GAIN)));

        AbstractDungeon.actionManager.addToBottom(new MariSuccessfulKindleAction(target, things));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
        }
    }
}