package mari_mod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_Lets_Go extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Strike.class.getName());
    public static final String ID = "MariMod:Mari_Lets_Go";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;
    private static final int DRAW = 3;
    private static final int UPGRADE_DRAW = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Mari_Lets_Go(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(MariCustomTags.QUOTATIONS);
        this.baseMagicNumber = DRAW;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p,this.magicNumber));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        int otherSkills = 0;
        for (AbstractCard card : AbstractDungeon.player.hand.group) {
            if (card.type == CardType.SKILL && card != this) otherSkills++;

            if(card instanceof Mari_Stewshine){
                Mari_Stewshine stewshine = (Mari_Stewshine) card;
                if(stewshine.card1.uuid.equals(this.uuid)){
                    otherSkills--;
                }else if(stewshine.card2.uuid.equals(this.uuid)){
                    otherSkills--;
                }else if(stewshine.card3.uuid.equals(this.uuid)){
                    otherSkills--;
                }
            }
        }
        this.cost = otherSkills;
        this.costForTurn = this.cost;
    }

    @Override //TY VEX YOU CUTIE
    public void update() {
        super.update();
        if (AbstractDungeon.player != null && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            applyPowers();
            this.isCostModified = true;
        }else{
            this.cost = 0;
            this.costForTurn = 0;
            this.isCostModified = false;
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Lets_Go();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_DRAW);
        }
    }
}