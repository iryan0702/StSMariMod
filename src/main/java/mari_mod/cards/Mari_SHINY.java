package mari_mod.cards;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.scenes.TheBottomScene;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.scene.InteractableTorchEffect;
import mari_mod.actions.MariShinyAction;
import mari_mod.patches.EphemeralCardPatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class Mari_SHINY extends AbstractMariCard {
    public static final Logger logger = LogManager.getLogger(Mari_SHINY.class.getName());
    public static final String ID = "MariMod:Mari_SHINY";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Mari_SHINY(){
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(MariCustomTags.QUOTATIONS);
        this.tags.add(MariCustomTags.RADIANCE);
        this.radiance = this.baseRadiance = 0;
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

        AbstractDungeon.actionManager.addToBottom(new MariShinyAction(target, this.upgraded, this.radiance, this.faded));
        AbstractDungeon.effectList.add(new BorderFlashEffect(new Color(1.0f, 1.0f, 0.6f, 1.0f), true));
        AbstractDungeon.effectList.add(new BorderFlashEffect(new Color(1.0f, 1.0f, 0.6f, 1.0f), true));
        AbstractDungeon.effectList.add(new BorderFlashEffect(new Color(1.0f, 1.0f, 0.6f, 1.0f), true));
        if(AbstractDungeon.scene instanceof TheBottomScene){
            ArrayList<InteractableTorchEffect> torches = (ArrayList<InteractableTorchEffect>)ReflectionHacks.getPrivate(AbstractDungeon.scene,TheBottomScene.class, "torches");
            for(InteractableTorchEffect torch: torches){
                ReflectionHacks.setPrivate(torch, InteractableTorchEffect.class, "activated", true);
            }
        }
    }

    @Override
    public void setFadedStats() {
        this.rawDescription = this.upgraded ? EXTENDED_DESCRIPTION[1] : EXTENDED_DESCRIPTION[0];
        super.setFadedStats();
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mari_SHINY();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.target = CardTarget.ALL;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.isAnyTarget = false;
            this.initializeDescription();
        }
    }
}