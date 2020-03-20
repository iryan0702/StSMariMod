package mari_mod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.actions.MariBeachEpisodeAction;
import mari_mod.patches.EphemeralCardPatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mari_The_Beach_Episode extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_The_Beach_Episode.class.getName());
    public static final String ID = "MariMod:Mari_The_Beach_Episode";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;
    private static final int GOLD_GAIN_PER = 2;
    private static final int BASE_BLOCK_REMOVAL = 15;
    private static final int UPGRADE_BLOCK_REMOVAL = 7;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Mari_The_Beach_Episode(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseMagicNumber = BASE_BLOCK_REMOVAL;
        this.magicNumber = this.baseMagicNumber;

        this.isAnyTarget = true;
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
        AbstractDungeon.actionManager.addToBottom(new MariBeachEpisodeAction(target, GOLD_GAIN_PER, this.magicNumber));
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_BLOCK_REMOVAL);
        }
    }

    /*
    public Mari_The_Beach_Episode(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseRadiance = BASE_RADIANCE_GAIN;
        this.radiance = this.baseRadiance;
        this.isAnyTarget = true;
        this.tags.add(MariCustomTags.RADIANCE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCreature target;
        if(m != null) {
            target = m;
        }else{
            target = p;
        }
        AbstractDungeon.actionManager.addToBottom(new MariTheBeachEpisodeAction(target, this.upgraded, this.radiance));
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.isAnyTarget = false;
            this.target = CardTarget.ALL;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    */
}