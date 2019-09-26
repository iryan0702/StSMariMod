package mari_mod.cards;

import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.ReApplyPowersAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.CardSave;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ConfusionPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import mari_mod.MariMod;
import mari_mod.MariSavables;
import mari_mod.actions.*;
import mari_mod.patches.CardColorEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Mari_Stewshine extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Stewshine.class.getName());
    public static final String ID = "MariMod:Mari_Stewshine";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 0;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    public AbstractMariCard card1;
    public AbstractMariCard card2;
    public AbstractMariCard card3;
    public boolean confused;
    private double hoverTimer;
    private int number; //total cards combined
    private boolean canColorCodeInThisLanguage;
    private String closingSymbol;

    public Mari_Stewshine(){
        super(ID, NAME, MariMod.saveableKeeper.stewshineCost, DESCRIPTION, TYPE, RARITY, TARGET, CardColorEnum.MARI);
        this.exhaust = true;
        canColorCodeInThisLanguage = true;
        if(Settings.language.equals(Settings.GameLanguage.ZHS)){
            canColorCodeInThisLanguage = false;
            closingSymbol = "，";
        }else{
            closingSymbol = "[]";
        }

        hoverTimer = 0;
        MariSavables saves = MariMod.saveableKeeper;
        number = saves.stewshineCards;
        int cost = 0;

        if(number >= 1) {
            this.refreshOneTimeCardStats();
            initializeDescription();

        }else{
            this.rawDescription = DESCRIPTION;
            initializeDescription();
        }

        confused = false;
        //this.cost = cost;
        //this.costForTurn = cost;
    }

    @Override
    public void triggerWhenDrawn() {
        if(AbstractDungeon.player.hasPower(ConfusionPower.POWER_ID)) {
            this.confused = true;
            int newCost = AbstractDungeon.cardRandomRng.random(3);
            logger.info("PLAYER HAS CONFUSION! NEW COST: " + newCost);
            if (this.cost != newCost) {
                this.cost = newCost;
                this.costForTurn = this.cost;
                this.isCostModified = true;
            }
        }
        super.triggerWhenDrawn();
        if(this.card1 != null){
            this.card1.triggerWhenDrawn();
            this.card2.triggerWhenDrawn();
            this.card3.triggerWhenDrawn();
        }
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        AbstractPlayer p = AbstractDungeon.player;
        if(this.card1 != null){
            if(p.hand.group.contains(this)) {
                p.hand.group.add(this.card1);
                p.hand.group.add(this.card2);
                p.hand.group.add(this.card3);
                this.card1.atTurnStart();
                this.card2.atTurnStart();
                this.card3.atTurnStart();
                p.hand.group.remove(this.card1);
                p.hand.group.remove(this.card2);
                p.hand.group.remove(this.card3);
            }else{
                this.card1.atTurnStart();
                this.card2.atTurnStart();
                this.card3.atTurnStart();
            }
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        MariSavables saves = MariMod.saveableKeeper;
        number = saves.stewshineCards;

        logger.info("STEWSHINE BEING USED!: REFRESH CARD STATS");

        this.refreshCards();

        logger.info("STEWSHINE BEING USED!: DO CARDS EXIST?!");

        if(this.card1 != null) {

            logger.info("STEWSHINE BEING USED!: CARDS EXIST!!");

            if (this.energyOnUse < EnergyPanel.totalCount) {
                this.energyOnUse = EnergyPanel.totalCount;
            }
            ArrayList<AbstractGameAction> actionQueue = new ArrayList<>();
            if(number >= 1){
                AbstractCard newCard1 = this.card1.makeSameInstanceOf();
                newCard1.purgeOnUse = true;
                newCard1.freeToPlayOnce = true;
                newCard1.current_x = Settings.WIDTH / 2.0F;
                newCard1.current_y = Settings.HEIGHT / -2.0F;
                //AbstractDungeon.actionManager.cardQueue.add(1, new CardQueueItem(newCard1, m));

                //AbstractDungeon.actionManager.addToBottom(new MariCardApplyPowersAction(this.card1,m));
                //AbstractDungeon.actionManager.addToBottom(new MariUseCardAction(this.card1,p,m,this.energyOnUse));

                actionQueue.add(new MariCardApplyPowersAction(this.card1,m));
                actionQueue.add(new MariUseCardAction(this.card1,p,m,this.energyOnUse,true));
            }
            if(number >= 2){
                AbstractCard newCard2 = this.card2.makeSameInstanceOf();
                newCard2.purgeOnUse = true;
                newCard2.freeToPlayOnce = true;
                newCard2.current_x = Settings.WIDTH / 2.0F;
                newCard2.current_y = Settings.HEIGHT / -2.0F;
                //AbstractDungeon.actionManager.cardQueue.add(2, new CardQueueItem(newCard2, m));

                //AbstractDungeon.actionManager.addToBottom(new MariCardApplyPowersAction(this.card2,m));
                //AbstractDungeon.actionManager.addToBottom(new MariUseCardAction(this.card2,p,m,this.energyOnUse));

                actionQueue.add(new MariCardApplyPowersAction(this.card2,m));
                actionQueue.add(new MariUseCardAction(this.card2,p,m,this.energyOnUse,true));
            }
            if(number >= 3){
                AbstractCard newCard3 = this.card3.makeSameInstanceOf();
                newCard3.purgeOnUse = true;
                newCard3.freeToPlayOnce = true;
                newCard3.current_x = Settings.WIDTH / 2.0F;
                newCard3.current_y = Settings.HEIGHT / -2.0F;
                //AbstractDungeon.actionManager.cardQueue.add(3, new CardQueueItem(newCard3, m));

                //AbstractDungeon.actionManager.addToBottom(new MariCardApplyPowersAction(this.card3,m));
                //AbstractDungeon.actionManager.addToBottom(new MariUseCardAction(this.card3,p,m,this.energyOnUse));

                actionQueue.add(new MariCardApplyPowersAction(this.card3,m));
                actionQueue.add(new MariUseCardAction(this.card3,p,m,this.energyOnUse,true));
            }

            AbstractDungeon.actionManager.addToBottom(new MariSaveAllFollowingActionsExceptTheImmediateNextActionThenRestoreSavedActionsAction(null));
            AbstractDungeon.actionManager.addToBottom(new MariActionQueueOrSomethingAction(actionQueue));

            if(this.card1.cost == -1 || this.card2.cost == -1 || this.card3.cost == -1){
                this.costForTurn = this.energyOnUse;
            }
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p,m);

        logger.info("SUPER CAN USE: " + canUse);
        logger.info("STEWSHINE CARD PLAYABLE: " + this.cardPlayable(m));
        logger.info("STEWSHINE CARD 1 CARD PLAYABLE: " + this.card1.cardPlayable(m));
        logger.info("STEWSHINE CARD 2 CARD PLAYABLE: " + this.card2.cardPlayable(m));
        logger.info("STEWSHINE CARD 3 CARD PLAYABLE: " + this.card3.cardPlayable(m));

        if(!canUse || !this.card1.cardPlayable(m) || !this.card2.cardPlayable(m) || !this.card3.cardPlayable(m)){
            logger.info("STEWSHINE CAN USE RETURN FALSE");
            this.stopGlowing();
            return false;
        }
        logger.info("STEWSHINE CAN USE RETURN TRUE");
        this.beginGlowing();
        return true;
    }

    @Override
    public void applyPowers(){
        this.refreshCards();
        if(this.card1 != null) {
            this.card1.applyPowers();
            if(this.card1.retain){
                this.retain = true;
            }
            this.card2.applyPowers();
            if(this.card2.retain){
                this.retain = true;
            }
            this.card3.applyPowers();
            if(this.card3.retain){
                this.retain = true;
            }
        }
        super.applyPowers();
        refreshCardCost();
    }


    public void update() {
        super.update();
        if (AbstractDungeon.player != null && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            MariSavables saves = MariMod.saveableKeeper;
            number = saves.stewshineCards;
            if(this.card1 != null) {
                AbstractPlayer p = AbstractDungeon.player;
                if(p.hoveredCard != null && p.hoveredCard.equals(this) && !p.isDraggingCard && this.isHoveredInHand(this.drawScale)){
                    if (number >= 1) {
                        this.card1.current_x = this.current_x - AbstractCard.IMG_WIDTH / 2.0F * 1.75F;
                        this.card1.current_y = this.current_y + AbstractCard.IMG_HEIGHT / 1.2F + 10.0F * Settings.scale * (float) Math.sin(hoverTimer/50);
                        this.card1.target_x = this.current_x - AbstractCard.IMG_WIDTH / 2.0F * 1.75F;
                        this.card1.target_y = this.current_y + AbstractCard.IMG_HEIGHT / 1.2F + 10.0F * Settings.scale * (float) Math.sin(hoverTimer/50);
                    }
                    if (number >= 2) {
                        this.card2.current_x = this.current_x;
                        this.card2.current_y = this.current_y + AbstractCard.IMG_HEIGHT / 1.2F + 10.0F * Settings.scale * (float) Math.sin(hoverTimer/50);
                        this.card2.target_x = this.current_x;
                        this.card2.target_y = this.current_y + AbstractCard.IMG_HEIGHT / 1.2F + 10.0F * Settings.scale * (float) Math.sin(hoverTimer/50) ;
                    }
                    if (number >= 3) {
                        this.card3.current_x = this.current_x + AbstractCard.IMG_WIDTH / 2.0F * 1.75F;
                        this.card3.current_y = this.current_y + AbstractCard.IMG_HEIGHT / 1.2F + 10.0F * Settings.scale * (float) Math.sin(hoverTimer/50);
                        this.card3.target_x = this.current_x + AbstractCard.IMG_WIDTH / 2.0F * 1.75F;
                        this.card3.target_y = this.current_y + AbstractCard.IMG_HEIGHT / 1.2F + 10.0F * Settings.scale * (float) Math.sin(hoverTimer/50);
                    }
                    hoverTimer++;
                }else {
                    if (number >= 1) {
                        this.card1.current_x = Settings.WIDTH / 2.0F;
                        this.card1.current_y = Settings.HEIGHT / -2.0F;
                        this.card1.target_x = Settings.WIDTH / 2.0F;
                        this.card1.target_y = Settings.HEIGHT / -2.0F;
                    }
                    if (number >= 2) {
                        this.card2.current_x = Settings.WIDTH / 2.0F;
                        this.card2.current_y = Settings.HEIGHT / -2.0F;
                        this.card2.target_x = Settings.WIDTH / 2.0F;
                        this.card2.target_y = Settings.HEIGHT / -2.0F;
                    }
                    if (number >= 3) {
                        this.card3.current_x = Settings.WIDTH / 2.0F;
                        this.card3.current_y = Settings.HEIGHT / -2.0F;
                        this.card3.target_x = Settings.WIDTH / 2.0F;
                        this.card3.target_y = Settings.HEIGHT / -2.0F;
                    }
                    hoverTimer = 0;
                }

            }else{
                this.refreshOneTimeCardStats();
                /*this.rawDescription = EXTENDED_DESCRIPTION[0];
                String quote = EXTENDED_DESCRIPTION[1];
                int cost = 0;
                if(number >= 1){
                    CardSave cardA = MariMod.saveableKeeper.stewshineCardA;
                    this.card1 = (AbstractMariCard)CardLibrary.getCopy(cardA.id, cardA.upgrades, cardA.misc).makeStatEquivalentCopy();
                    this.rawDescription += " NL [#fff6e2]" + quote + this.card1.name + quote + "[]";
                    cost+= Math.max(0,this.card1.cost);
                }
                if(number >= 2){
                    CardSave cardB = MariMod.saveableKeeper.stewshineCardB;
                    this.card2 = (AbstractMariCard)CardLibrary.getCopy(cardB.id, cardB.upgrades, cardB.misc).makeStatEquivalentCopy();
                    this.rawDescription += " NL [#fff6e2]" + quote + this.card2.name + quote + "[]";
                    cost+= Math.max(0,this.card2.cost);
                }
                if(number >= 3){
                    CardSave cardC = MariMod.saveableKeeper.stewshineCardC;
                    this.card3 = (AbstractMariCard)CardLibrary.getCopy(cardC.id, cardC.upgrades, cardC.misc).makeStatEquivalentCopy();
                    this.rawDescription += " NL [#fff6e2]" + quote + this.card3.name + quote + "[]";
                    cost+= Math.max(0,this.card3.cost);
                }

                saves.stewshineSetup = true;
                this.cost = cost;
                this.costForTurn = cost;
                this.rawDescription += " NL " + EXTENDED_DESCRIPTION[2];
                initializeDescription();*/
            }
        }

    }

    public void refreshOneTimeCardStats(){
        logger.info("REFRESHING CARD STATS");
        refreshCards();
        ArrayList<CardTags> tempTags = new ArrayList<>();
        for(CardTags tag: this.tags) {
            tempTags.add(tag);
        }
        for(CardTags tag: tempTags) {
            this.tags.remove(tag);
        }

        this.baseGoldCost = 0;
        this.goldCost = 0;

        CardTarget finalTargetType = CardTarget.NONE;

        if(number >= 1){
            if(this.card1.isAnyTarget) {
                this.isAnyTarget = true;
                finalTargetType = CardTarget.SELF;
            }
        }
        if(number >= 2){
            if(this.card2.isAnyTarget) {
                this.isAnyTarget = true;
                finalTargetType = CardTarget.SELF;
            }
        }
        if(number >= 3){
            if(this.card3.isAnyTarget) {
                this.isAnyTarget = true;
                finalTargetType = CardTarget.SELF;
            }
        }

        if(number >= 1){
            if(this.card1.hasTag(MariCustomTags.QUOTATIONS)){
                this.tags.add(MariCustomTags.QUOTATIONS);
            }
            if(this.card1.hasTag(MariCustomTags.RADIANCE)){
                this.tags.add(MariCustomTags.RADIANCE);
            }
            if(this.card1.hasTag(MariCustomTags.SPEND)){
                this.tags.add(MariCustomTags.SPEND);
            }
            if(this.card1.hasTag(MariCustomTags.KINDLE)){
                this.tags.add(MariCustomTags.KINDLE);
            }
            if(this.card1.isEthereal){
                this.isEthereal = true;
            }
            if(this.card1.isInnate){
                this.isInnate = true;
            }
            if(this.card1.target == CardTarget.ENEMY){
                this.isAnyTarget = false;
                finalTargetType = CardTarget.ENEMY;
            }
            this.baseGoldCost += this.card1.baseGoldCost;
        }
        if(number >= 2){
            if(this.card2.hasTag(MariCustomTags.QUOTATIONS)){
                this.tags.add(MariCustomTags.QUOTATIONS);
            }
            if(this.card2.hasTag(MariCustomTags.RADIANCE)){
                this.tags.add(MariCustomTags.RADIANCE);
            }
            if(this.card2.hasTag(MariCustomTags.SPEND)){
                this.tags.add(MariCustomTags.SPEND);
            }
            if(this.card2.hasTag(MariCustomTags.KINDLE)){
                this.tags.add(MariCustomTags.KINDLE);
            }
            if(this.card2.isEthereal){
                this.isEthereal = true;
            }
            if(this.card2.isInnate){
                this.isInnate = true;
            }
            if(this.card2.target == CardTarget.ENEMY){
                this.isAnyTarget = false;
                finalTargetType = CardTarget.ENEMY;
            }
            this.baseGoldCost += this.card2.baseGoldCost;
        }
        if(number >= 3){
            if(this.card3.hasTag(MariCustomTags.QUOTATIONS)){
                this.tags.add(MariCustomTags.QUOTATIONS);
            }
            if(this.card3.hasTag(MariCustomTags.RADIANCE)){
                this.tags.add(MariCustomTags.RADIANCE);
            }
            if(this.card3.hasTag(MariCustomTags.SPEND)){
                this.tags.add(MariCustomTags.SPEND);
            }
            if(this.card3.hasTag(MariCustomTags.KINDLE)){
                this.tags.add(MariCustomTags.KINDLE);
            }
            if(this.card3.isEthereal){
                this.isEthereal = true;
            }
            if(this.card3.isInnate){
                this.isInnate = true;
            }
            if(this.card3.target == CardTarget.ENEMY){
                this.isAnyTarget = false;
                finalTargetType = CardTarget.ENEMY;
            }
            this.baseGoldCost += this.card3.baseGoldCost;
        }
        this.goldCost = this.baseGoldCost;
        if(this.target != finalTargetType) {
            this.target = finalTargetType;
            logger.info("DIFFERENT TARGETING: IS NOW " + finalTargetType);
        }

        initializeDescription();
        refreshCardCost();
    }

    public void refreshCards(){
        MariSavables saves = MariMod.saveableKeeper;
        number = saves.stewshineCards;
        this.rawDescription = EXTENDED_DESCRIPTION[0];
        String quote = EXTENDED_DESCRIPTION[1];
        if(number >= 1) {
            CardSave cardA = MariMod.saveableKeeper.stewshineCardA;
            this.card1 = (AbstractMariCard)CardLibrary.getCopy(cardA.id, cardA.upgrades, cardA.misc).makeStatEquivalentCopy();
            if(canColorCodeInThisLanguage) this.rawDescription += " NL [#fff6e2]";
            this.rawDescription += this.card1.name;
            this.rawDescription += closingSymbol;
            }
        if(number >= 2){
            CardSave cardB = MariMod.saveableKeeper.stewshineCardB;
            this.card2 = (AbstractMariCard)CardLibrary.getCopy(cardB.id, cardB.upgrades, cardB.misc).makeStatEquivalentCopy();
            if(canColorCodeInThisLanguage) this.rawDescription += " NL [#fff6e2]";
            this.rawDescription += this.card2.name;
            this.rawDescription += closingSymbol;
            }
        if(number >= 3) {
            CardSave cardC = MariMod.saveableKeeper.stewshineCardC;
            this.card3 = (AbstractMariCard) CardLibrary.getCopy(cardC.id, cardC.upgrades, cardC.misc).makeStatEquivalentCopy();
            if(canColorCodeInThisLanguage) this.rawDescription += " NL [#fff6e2]";
            this.rawDescription += this.card3.name;
            if(canColorCodeInThisLanguage) this.rawDescription += "[]";
            }
        if(canColorCodeInThisLanguage) this.rawDescription += " NL [#fff6e2]";
        this.rawDescription += EXTENDED_DESCRIPTION[2];


    }

    public void refreshCardCost(){
        int cost = 0;
        if(this.confused) cost = this.cost;
        if(this.card1 != null) {
            if (!this.confused || this.card1.cardID.equals(Mari_Lets_Go.ID))
                cost += Math.max(0, this.card1.costForTurn);
            if (!this.confused || this.card2.cardID.equals(Mari_Lets_Go.ID))
                cost += Math.max(0, this.card2.costForTurn);
            if (!this.confused || this.card3.cardID.equals(Mari_Lets_Go.ID))
                cost += Math.max(0, this.card3.costForTurn);
            if (!this.confused) {
                this.cost = cost;
                this.costForTurn = cost;
            } else {
                this.costForTurn = cost;
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        if(AbstractDungeon.player != null && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT){
            MariSavables saves = MariMod.saveableKeeper;
            number = saves.stewshineCards;
            if(number >= 1){
                this.card1.render(sb);
            }
            if(number >= 2){
                this.card2.render(sb);
            }
            if(number >= 3){
                this.card3.render(sb);
            }
        }
    }


    @Override
    public void upgrade() {
        if (!this.upgraded && AbstractDungeon.player != null) {
            boolean isMasterUpgrade = AbstractDungeon.player.masterDeck.contains(this);
            MariSavables saves = MariMod.saveableKeeper;
            number = saves.stewshineCards;
            this.rawDescription = EXTENDED_DESCRIPTION[0];
            String quote = EXTENDED_DESCRIPTION[1];
            if(number >= 1 && this.card1.canUpgrade()){
                if(this.card1 instanceof Mari_Closure) this.card1.misc = saves.stewshineCardA.misc;
                this.card1.upgrade();
                if(isMasterUpgrade) saves.stewshineCardA = new CardSave(this.card1.cardID, this.card1.timesUpgraded, this.card1.misc);
            }
            if(canColorCodeInThisLanguage) this.rawDescription += " NL [#fff6e2]";
            this.rawDescription += this.card1.name;
            this.rawDescription += closingSymbol;
            if(number >= 2 && this.card2.canUpgrade()){
                if(this.card2 instanceof Mari_Closure) this.card2.misc = saves.stewshineCardB.misc;
                this.card2.upgrade();
                if(isMasterUpgrade) saves.stewshineCardB = new CardSave(this.card2.cardID, this.card2.timesUpgraded, this.card2.misc);
            }
            if(canColorCodeInThisLanguage) this.rawDescription += " NL [#fff6e2]";
            this.rawDescription += this.card2.name;
            this.rawDescription += closingSymbol;
            if(number >= 3 && this.card3.canUpgrade()){
                if(this.card3 instanceof Mari_Closure) this.card3.misc = saves.stewshineCardC.misc;
                this.card3.upgrade();
                if(isMasterUpgrade) saves.stewshineCardC = new CardSave(this.card3.cardID, this.card3.timesUpgraded, this.card3.misc);
            }
            if(canColorCodeInThisLanguage) this.rawDescription += " NL [#fff6e2]";
            this.rawDescription += this.card3.name;
            if(canColorCodeInThisLanguage) this.rawDescription += "[]";

            int cost = 0;

            if(number >= 1) {
                cost += Math.max(0,this.card1.cost);
            }
            if(number >= 2){
                cost+= Math.max(0,this.card2.cost);
            }
            if(number >= 3){
                cost+= Math.max(0,this.card3.cost);
            }
            if(cost != this.cost){
                this.costForTurn = cost;
                this.cost = cost;
                this.upgradedCost = true;
            }
            if(canColorCodeInThisLanguage) this.rawDescription += " NL ";
            this.rawDescription += EXTENDED_DESCRIPTION[2];
            initializeDescription();


        }
    }

    @Override
    public boolean canUpgrade() {
        if(AbstractDungeon.player != null) {
            return this.card1.canUpgrade() || this.card2.canUpgrade() || this.card3.canUpgrade();
        }
        else return false;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Stewshine();
    }
}