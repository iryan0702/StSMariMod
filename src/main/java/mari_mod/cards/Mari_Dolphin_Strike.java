package mari_mod.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.unique.SwordBoomerangAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.actions.MariDolphinStrikeAction;
import mari_mod.actions.MariSuccessfulKindleAction;
import mari_mod.actions.MariUnsuccessfulKindleAction;
import mari_mod.powers.Radiance_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Dolphin_Strike extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Dolphin_Strike.class.getName());
    public static final String ID = "MariMod:Mari_Dolphin_Strike";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK_DMG = 4;
    private static final int DAMAGE_TIMES = 2;
    private static final int DAMAGE_TIMES_UPGRADE = 1;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Mari_Dolphin_Strike(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseDamage = ATTACK_DMG;
        this.damage = this.baseDamage;
        this.baseMagicNumber = DAMAGE_TIMES;
        this.magicNumber = this.baseMagicNumber;
        this.isAnyTarget = true;
        this.tags.add(CardTags.STRIKE);
        this.tags.add(MariCustomTags.KINDLE);
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
            this.successfulKindle();
        }
        AbstractDungeon.actionManager.addToBottom(new MariUnsuccessfulKindleAction(target, new SwordBoomerangAction(AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null,true, AbstractDungeon.cardRandomRng), new DamageInfo(p, this.baseDamage),this.magicNumber)));

        AbstractDungeon.actionManager.addToBottom(new MariSuccessfulKindleAction(target, new SwordBoomerangAction(AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null,true, AbstractDungeon.cardRandomRng), new DamageInfo(p, this.baseDamage),this.magicNumber+1)));

    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Dolphin_Strike();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(DAMAGE_TIMES_UPGRADE);
        }
    }
}