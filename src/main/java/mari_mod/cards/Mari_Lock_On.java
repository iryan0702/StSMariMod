package mari_mod.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import mari_mod.effects.MariLockOnEffect;
import mari_mod.powers.Lock_On_Power;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static mari_mod.effects.MariLockOnEffect.TOTAL_TIME;

public class Mari_Lock_On extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_Strike.class.getName());
    public static final String ID = "MariMod:Mari_Lock_On";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public Mari_Lock_On(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(MariCustomTags.QUOTATIONS);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Lock_On_Power(p, 1, m, AbstractPower.PowerType.BUFF), 1));
        addToBot(new VFXAction(new MariLockOnEffect(m.hb.cX, m.hb.cY), TOTAL_TIME));

        boolean enemyLockedOn = false;
        for(AbstractMonster enemy : AbstractDungeon.getCurrRoom().monsters.monsters){
            if(enemy.hasPower(Lock_On_Power.POWER_ID)){
                enemyLockedOn = true;
            }
        }
        if(!enemyLockedOn){
            if(this.upgraded){
                addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, 1, false), 1));
            }
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new Lock_On_Power(m, 0, m, null), 0));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_Lock_On();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
            upgradeName();
        }
    }
}