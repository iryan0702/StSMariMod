package mari_mod.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.ReApplyPowersAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import mari_mod.MariMod;
import mari_mod.actions.MariHeavyPriceAction;
import mari_mod.relics.MariStewshine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class Mari_Heavy_Price extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Heavy_Price.class.getName());
    public static final String ID = "MariMod:Mari_Heavy_Price";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 1;
    private static final int UPGRADE_EXTRA_DAMAGE = 3;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public Mari_Heavy_Price(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseMagicNumber = 0;
        this.magicNumber = this.baseMagicNumber;
        this.baseDamage = 0;
        this.damage = this.baseDamage;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new MariHeavyPriceAction(m, new DamageInfo(p, this.magicNumber), 2));
    }

    @Override
    public void applyPowers() {
        int damage = 0;
        for(AbstractCard costCheckCard : AbstractDungeon.player.hand.group){
            int costCheckCost = costCheckCard.costForTurn;
            if(costCheckCost>0 && costCheckCard!=this) damage += costCheckCost;

            if(costCheckCard instanceof Mari_Stewshine){
                Mari_Stewshine stewshine = (Mari_Stewshine) costCheckCard;
                if(stewshine.card1.uuid.equals(this.uuid)){
                    damage -= costCheckCost;
                }else if(stewshine.card2.uuid.equals(this.uuid)){
                    damage -= costCheckCost;
                }else if(stewshine.card3.uuid.equals(this.uuid)){
                    damage -= costCheckCost;
                }
            }
        }
        this.baseDamage = damage*2+this.magicNumber;
        this.damage = this.baseDamage;
        super.applyPowers();
        if(damage > 0){
            if(this.upgraded) {
                this.rawDescription = UPGRADE_DESCRIPTION + EXTENDED_DESCRIPTION[0];
            }else{
                this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];
            }
        }else{
            if(this.upgraded) {
                this.rawDescription = UPGRADE_DESCRIPTION;
            }else{
                this.rawDescription = DESCRIPTION;
            }
        }
        this.initializeDescription();
    }

    @Override //TY VEX YOU CUTIE
    public void update() {
        super.update();
        if (AbstractDungeon.player != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            applyPowers();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Heavy_Price();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_EXTRA_DAMAGE);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}