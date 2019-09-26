package mari_mod.powers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Colors;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.ChestShineEffect;
import mari_mod.cards.AbstractMariCard;
import mari_mod.relics.MariCursedDoll;
import mari_mod.relics.MariDiploma;
import mari_mod.relics.MariDiploma;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import mari_mod.MariMod;

import java.util.ArrayList;


public class Radiance_Power extends AbstractPower
{
    public static final String POWER_ID = "MariMod:Radiance_Power";
    private static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(MariMod.class.getName());
    private boolean fastTrigger;
    ArrayList<RadianceParticle> radianceParticles;
    private DamageInfo radianceInfo;
    private float particleDelay;

    private static final float FPS_SCALE = (240f / Settings.MAX_FPS);

    public Radiance_Power(AbstractCreature owner, int bufferAmt)
    {
        this.name = NAME;
        this.owner = owner;
        if(this.owner.isPlayer && !AbstractDungeon.player.hasRelic(MariCursedDoll.ID)) {
            this.type = PowerType.BUFF;
        }else{
            this.type = PowerType.DEBUFF;
        }
        radianceParticles = new ArrayList<>();
        this.ID = POWER_ID;
        this.amount = bufferAmt;
        this.isTurnBased = true;
        this.updateDescription();
        this.fastTrigger = false;
        MariMod.setPowerImages(this);
        if(this.amount <= 0) AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        this.particleDelay = 0.0F;
    }

    public void stackPower(int stackAmount)
    {
        logger.info("this stacks: " + stackAmount);
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if(stackAmount > 0 && (!this.owner.isPlayer || AbstractDungeon.player.hasRelic(MariCursedDoll.ID))) {
            this.flash();
            this.radianceInfo = new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS);
            AbstractDungeon.actionManager.addToTop(new DamageAction(this.owner, this.radianceInfo, AbstractGameAction.AttackEffect.FIRE, true));
        }
        if(stackAmount > 0) burstOfParticles(stackAmount*4);
        if(this.amount <= 0) AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        updateDescription();
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        if(this.amount <= 0) AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        this.reducePower((AbstractDungeon.player.hasRelic(MariDiploma.ID) ? 2 : 1));
        updateDescription();
    }

    public void onInitialApplication() {
        if(!this.owner.isPlayer || AbstractDungeon.player.hasRelic(MariCursedDoll.ID)) {
            this.radianceInfo = new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS);
            AbstractDungeon.actionManager.addToTop(new DamageAction(this.owner, this.radianceInfo, AbstractGameAction.AttackEffect.FIRE, true));
        }
        burstOfParticles(this.amount*4);
        updateDescription();
    }


    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        float damageBoost = (AbstractDungeon.player.hasRelic(MariCursedDoll.ID) ? 0.20F : 0.10F);
        if(this.owner.isPlayer) {
            return type == DamageInfo.DamageType.NORMAL ? damage * (float)(1.0F +(this.amount * damageBoost)): damage;
        }
        else
            return damage;
    }


    public void updateDescription() {
        if(this.owner.isPlayer) {
            this.description = DESCRIPTION[2] + (this.amount * 10) + DESCRIPTION[3];
        }else{
            this.description = DESCRIPTION[0];
        }
    }

    @Override
    public void update(int slot) {
        super.update(slot);
        this.particleDelay -= Gdx.graphics.getDeltaTime();
        if(this.particleDelay <= 0 && !Settings.DISABLE_EFFECTS) {
            radianceParticles.add(new RadianceParticle(this.owner, MathUtils.randomBoolean(0.75F)));
            this.particleDelay = MathUtils.random(0.0F, 2.0F/this.amount);
        }
        for(RadianceParticle p: radianceParticles){
            p.update();
        }
    }

    public void burstOfParticles(int amount){
        for (int i = 0; i < amount && i < 200 ; i++) {
            radianceParticles.add(new RadianceParticle(this.owner, false, true));
        }
    }

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        super.renderAmount(sb, x, y, c);
        for(RadianceParticle p: radianceParticles){
            p.render(sb);
        }
    }

    public static class RadianceParticle{
        private float effectDuration;
        private float x;
        private float y;
        private float vY;
        private float vX;
        private Color color;
        private Float duration;
        private Float startingDuration;
        private Float scale;
        private Float rotation;
        private float alpha;
        private float masterAlpha;
        private float targetScale;
        private float masterScale;
        private boolean isKindleSpark;
        private AbstractCreature owner;
        private TextureAtlas.AtlasRegion img;

        public RadianceParticle(AbstractCreature owner, boolean isKindleSpark, boolean isBurstSpark) {
            this(owner, isKindleSpark);
            if(isBurstSpark) {
                this.vX = MathUtils.random(-40.0F, 40.0F) * Settings.scale;
            }
            this.x += Settings.scale * (MathUtils.random(owner.hb_w * -0.2F, owner.hb_w * 0.2F) + 5.0F);
            this.y += Settings.scale * (MathUtils.random(owner.hb_h * -0.2F, owner.hb_h * 0.2F) + 5.0F);
        }

        public RadianceParticle(AbstractCreature owner, boolean isKindleSpark) {
            this.img = ImageMaster.ROOM_SHINE_2;
            this.effectDuration = MathUtils.random(1.0F, 3.0F);
            this.duration = this.effectDuration;
            this.startingDuration = this.effectDuration;
            this.x = owner.hb.cX + (owner.hb_w * MathUtils.random(-0.4F, 0.4F));
            this.y = owner.hb.cY + (owner.hb_h * MathUtils.random(-0.5F, 0.4F));
            this.vY = MathUtils.random(10.0F, 50.0F) * Settings.scale;
            this.alpha = MathUtils.random(0.7F, 1.0F);
            if(isKindleSpark && MariMod.currentlyKindledCard == null) {
                this.masterAlpha = 0.0F;
            }else{
                this.masterAlpha = 1.0F;
            }
            this.isKindleSpark = isKindleSpark;
            this.owner = owner;
            this.color = new Color(1.0F, 1.0F, MathUtils.random(0.6F, 0.9F), this.alpha);
            this.scale = 0.01F;
            this.masterScale = 1.0F;
            this.targetScale = MathUtils.random(0.4F, 0.8F);
            this.rotation = MathUtils.random(-3.0F, 3.0F);
        }

        public void update() {
            if (this.vY != 0.0F) {
                this.y += this.vY * Gdx.graphics.getDeltaTime();
                MathUtils.lerp(this.vY, 0.0F, Gdx.graphics.getDeltaTime() * 10.0F);
                if (this.vY < 0.5F) {
                    this.vY = 0.0F;
                }
            }

            if (this.vX != 0.0F) {
                this.x += this.vX * Gdx.graphics.getDeltaTime();
                MathUtils.lerp(this.vX, 0.0F, Gdx.graphics.getDeltaTime() * 10.0F);
                if (Math.abs(this.vX) < 0.01F){
                    this.vX = 0.0F;
                }
            }

            if (MariMod.currentKindleTarget != null && MariMod.currentKindleTarget.equals(this.owner) && MariMod.currentlyKindledCard != null){
                if(this.x > MariMod.currentlyKindledCard.current_x){
                    vX -= 0.8F * Settings.scale;
                }else{
                    vX += 0.8F * Settings.scale;
                }
                if(this.masterScale <= 1.5F){
                    this.masterScale += 0.01F;
                }

                if(this.masterAlpha < 1.0F){
                    this.masterAlpha += 0.01F * Gdx.graphics.getDeltaTime();
                }

            }else{
                if(this.masterScale > 1.0F){
                    this.masterScale -= 0.01F;
                }
                if(this.isKindleSpark){
                    this.masterAlpha -= 0.01F * Gdx.graphics.getDeltaTime();
                }
            }

            float t = (this.effectDuration - this.duration) * 2.0F;
            if (t > 1.0F) {
                t = 1.0F;
            }

            float tmp = Interpolation.bounceOut.apply(0.01F, this.targetScale, t);
            this.scale = tmp * tmp * Settings.scale;
            this.duration -= Gdx.graphics.getDeltaTime();
            if (this.duration < 0.0F) {
            } else if (this.duration < this.effectDuration / 2.0F) {
                this.color.a = Interpolation.exp5In.apply(0.0F, this.alpha, this.duration / (this.effectDuration / 2.0F)) * this.masterAlpha;
            }
        }

        public void render(SpriteBatch sb) {
            if(this.masterAlpha > 0.0F) {
                sb.setColor(this.color);
                sb.setBlendFunction(770, 1);
                sb.draw(this.img, this.x - (float) this.img.packedWidth / 2.0F, this.y - (float) this.img.packedHeight / 2.0F, (float) this.img.packedWidth / 2.0F, (float) this.img.packedHeight / 2.0F, (float) this.img.packedWidth, (float) this.img.packedHeight, this.scale * this.masterScale * MathUtils.random(0.9F, 1.1F) , this.scale * this.masterScale * MathUtils.random(0.7F, 1.3F), this.rotation);
                sb.setBlendFunction(770, 771);
            }
        }

        public void dispose() {
        }
    }
}
