package mari_mod.cards;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Colors;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.random.Random;
import mari_mod.MariMod;
import mari_mod.actions.MariRecallAction;
import mari_mod.patches.CardColorEnum;
import mari_mod.patches.MariKindleArrowPatch;
import mari_mod.powers.Radiance_Power;

import java.util.ArrayList;

//TODO: CREDIT CARD KINDLE PARTICLES: BLANK THE EVIL

public abstract class AbstractMariCard extends CustomCard {


    private static final UIStrings noGoldDialogue = CardCrawlGame.languagePack.getUIString("NoGoldDialogue");

    public static AbstractCard currentlyKindledCard;
    public static AbstractCreature currentKindleTarget;
    public static float kindleTimer;

    public int baseGoldCost = 0;
    public int goldCost = 0;
    public boolean upgradedGoldCost = false;
    public boolean limitedByGoldCost = true; //is gold cost on card always explicitly paid?

    public int baseRadiance = 0;
    public int radiance = 0;
    public boolean modifiedRadiance = false;
    public boolean upgradedRadiance = false;
    public boolean faded = false;

    public boolean isAnyTarget = false;
    private boolean targetingEnemy = false;
    private boolean isKindled = false;
    private boolean stillKindled = false;
    public Color defaultGlowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

    public boolean recallPreview = false;


    private static final float FPS_SCALE = (240f / Settings.MAX_FPS);
    public ArrayList<KindleParticle> particles;

    public void upgradeGoldCost(int amount) {
        this.baseGoldCost += amount;
        this.goldCost = this.baseGoldCost;
        this.upgradedGoldCost = true;
    }

    public void upgradeRadiance(int amount) {
        this.baseRadiance += amount;
        this.radiance = this.baseRadiance;
        this.upgradedRadiance = true;
    }
    protected void upgradeMisc(int amount) {
        this.misc += amount;
    }

    public AbstractMariCard(String id, String name, int cost, String description, CardType type, CardRarity rarity, CardTarget target) {
        super(id, name, MariMod.cardImage(id), cost, description, type, CardColorEnum.MARI, rarity, target);
        this.particles = new ArrayList<>();
    }

    public AbstractMariCard(String id, String name, int cost, String description, CardType type, CardRarity rarity, CardTarget target, CardColor differentClass) {
        super(id, name, MariMod.cardImage(id), cost, description, type, differentClass, rarity, target);
        this.particles = new ArrayList<>();
    }

    public AbstractMariCard(String id, String name, int cost, String description, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, MariMod.cardImage(id), cost, description, type, color, rarity, target);
        this.particles = new ArrayList<>();
    }

    public AbstractMariCard(String id, String name, String image, int cost, String description, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, image, cost, description, type, color, rarity, target);
        this.particles = new ArrayList<>();
    }

    @Override
    public boolean hasEnoughEnergy() {
        if (!super.hasEnoughEnergy())
            return false;
        return true;
    }


    @Override
    public void resetAttributes() {
        super.resetAttributes();
    }

    /*
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if(limitedByGoldCost) {
            if (!canUse) {
                return false;
            } else {
                if (AbstractDungeon.player.gold < this.goldCost) {
                    canUse = false;
                    this.cantUseMessage = noGoldDialogue.TEXT[0];
                }
                return canUse;
            }
        }
        return canUse;
    }*/

    @Override
    public void applyPowers() {
        super.applyPowers();

        float tmpRadiance = baseRadiance;
        radiance = MathUtils.floor(tmpRadiance);

        float tmpGoldCost = baseGoldCost;
        goldCost = MathUtils.floor(tmpGoldCost);

        this.glowColor = getNotKindledColor();

        if(recallPreview){
            AbstractCard target = MariRecallAction.findRecallTarget();
            if(target == null){
                this.cardsToPreview = null;
            }else if(this.cardsToPreview == null || this.cardsToPreview.uuid != target.uuid || (this.cardsToPreview instanceof AbstractMariCard && ((AbstractMariCard)this.cardsToPreview).faded) != ((AbstractMariCard)target).faded) {
                this.cardsToPreview = target.makeSameInstanceOf();
                if(this.cardsToPreview instanceof AbstractMariCard && ((AbstractMariCard) this.cardsToPreview).faded){
                    ((AbstractMariCard)this.cardsToPreview).baseRadiance = 1;
                    ((AbstractMariCard)this.cardsToPreview).radiance = 1;
                }
                System.out.println("creating new preview for: " + this.cardsToPreview.name);
            }
        }

        if(faded){
            setFadedStats();
        }
    }

    //Placed in a separate function as it is accessed by applyPowers, statEquivalentCopy patch, and ephemeralCard patch
    public void setFadedStats(){
        this.baseRadiance = 1;
        this.radiance = 1;
        this.upgradedRadiance = false;
        this.rawDescription = this.rawDescription.replace("Fading", "Faded");
        this.initializeDescription();
    }

    public Color getNotKindledColor(){
        if(this.goldCost > AbstractDungeon.player.gold && this.limitedByGoldCost){
            return Color.RED.cpy();
        }
        return defaultGlowColor;
    }

    @Override
    public void update() {
        super.update();
        isKindled = false;
        if(AbstractMariCard.currentlyKindledCard == this) {
            AbstractMariCard.currentlyKindledCard = null;
            AbstractMariCard.currentKindleTarget = null;
        }
        if(isAnyTarget && AbstractDungeon.player != null) {
            AbstractPlayer p = AbstractDungeon.player;
            if (p.isDraggingCard && p.hoveredCard.equals(this)) {
                AbstractMonster hoveredEnemy = null;
                for (AbstractMonster enemy : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (enemy.hb.hovered && !enemy.isDead && !enemy.halfDead) {
                        hoveredEnemy = enemy;
                    }
                }
                if (hoveredEnemy != null) {
                    if(!this.targetingEnemy) {
                        AbstractMariCard.kindleTimer = 0.0f;
                        this.targetingEnemy = true;
                        p.inSingleTargetMode = true;
                        this.target = CardTarget.ENEMY;
                        this.target_x = hoveredEnemy.hb.cX - this.hb.width * 0.6F - hoveredEnemy.hb_w * 0.6F;
                        this.target_y = hoveredEnemy.hb.cY;
                        this.applyPowers();
                    }
                    if(hoveredEnemy.hasPower(Radiance_Power.POWER_ID) && this.hasTag(MariCustomTags.KINDLE)){
                        AbstractMariCard.currentlyKindledCard = this;
                        AbstractMariCard.currentKindleTarget = hoveredEnemy;
                        this.glowColor = Color.GOLD.cpy();
                        isKindled = true;
                        stillKindled = true;
                    }
                } else {
                    if(this.targetingEnemy) {
                        AbstractMariCard.kindleTimer = 0.0f;
                        this.targetingEnemy = false;
                        p.inSingleTargetMode = false;
                        this.target = CardTarget.SELF;
                        this.applyPowers();
                    }
                    if(p.hasPower(Radiance_Power.POWER_ID) && this.hasTag(MariCustomTags.KINDLE)){
                        AbstractMariCard.currentlyKindledCard = this;
                        AbstractMariCard.currentKindleTarget = p;
                        this.glowColor = Color.GOLD.cpy();
                        isKindled = true;
                        stillKindled = true;
                    }
                }
            }else if(this.targetingEnemy){
                this.targetingEnemy = false;
                this.target = CardTarget.SELF;
            }
        }

        if(stillKindled && isKindled){
            AbstractMariCard.kindleTimer += Gdx.graphics.getDeltaTime();
        }else if(stillKindled){
            stillKindled = false;
            AbstractMariCard.kindleTimer = 0.0f;
            this.glowColor = getNotKindledColor();
        }

        for(KindleParticle p : particles){
            p.update();
        }
        particles.removeIf(KindleParticle::isDead);
        if(isKindled && kindleTimer > MariKindleArrowPatch.MariKindleArrowTailPatch.kindleTime) {
            if (this.particles.size() < 100 && !Settings.DISABLE_EFFECTS) {
                for (int i = 0; i < FPS_SCALE; i++) {
                    if (Math.random() < 0.2) {
                        Vector2 point = generateRandomPointAlongEdgeOfHitbox();
                        particles.add(new KindleParticle(point.x, point.y, this.drawScale, this.upgraded, false));
                    }
                }
            }
        }

    }

    protected void successfulKindle(AbstractCreature kindledTarget){
        if (!Settings.DISABLE_EFFECTS) {
            for (int i = 0; i < 50; i++) {
                Vector2 point = generateRandomPointAlongEdgeOfHitbox();
                particles.add(new KindleParticle(point.x, point.y, this.drawScale, this.upgraded, true));
            }
        }
        AbstractPower p = kindledTarget.getPower(Radiance_Power.POWER_ID);
        if(p != null){
            ((Radiance_Power)p).kindleSeek();
        }
    }

    private Vector2 generateRandomPointAlongEdgeOfHitbox() {
        Vector2 result = new Vector2();
        Random random = new Random();
        boolean topOrBottom = random.randomBoolean();
        boolean leftOrRight = random.randomBoolean();
        boolean tbOrLr = random.randomBoolean();

        if(tbOrLr){
            result.x = random.random(this.hb.cX - (this.hb.width / 2f), this.hb.cX + (this.hb.width / 2f));
            result.y = topOrBottom ? this.hb.cY + (this.hb.height / 2f) : this.hb.cY - (this.hb.height / 2f);
        } else {
            result.x = leftOrRight ? this.hb.cX + (this.hb.width / 2f) : this.hb.cX - (this.hb.width / 2f);
            result.y = random.random(this.hb.cY - (this.hb.height / 2f), this.hb.cY + (this.hb.height / 2f));
        }

        return result;
    }

    @Override
    public void render(SpriteBatch sb){
        sb.setColor(Color.WHITE.cpy());
        for(KindleParticle p : particles){
            p.render(sb);
        }
        super.render(sb);
    }

    public static class KindleParticle {
        private Vector2 pos;
        private Vector2 vel;
        private float lifeSpan;
        private Color color;
        private float drawScale;
        private boolean upgraded;
        private boolean visibleFlicker;
        private boolean poof;
        private TextureAtlas.AtlasRegion imgKindle;

        public KindleParticle(float x, float y, float drawScale, boolean upgraded, boolean poof) {
            pos = new Vector2(x, y);
            this.drawScale = drawScale;
            this.upgraded = upgraded;
            this.poof = poof;

            float speedScale = MathUtils.clamp(
                    Gdx.graphics.getDeltaTime() * 240f,
                    FPS_SCALE - 0.2f,
                    FPS_SCALE + 0.2f);
            float maxV = 2.0f * drawScale;
            maxV = MathUtils.clamp(maxV, 0.01f, FPS_SCALE * 2f);

            float velX = MathUtils.random(-1.0f * maxV * speedScale/20,maxV * speedScale/20);
            float velY = MathUtils.random(maxV * speedScale/8, maxV * speedScale/3);

            vel = new Vector2(velX, velY);

            lifeSpan = MathUtils.random(0.5f, 1.5f);

            color = new Color(1.0F, MathUtils.random(0.5F, 1.0F), MathUtils.random(0.0F, 0.2F), 1.0F);

            double random = Math.random();

            if(random < 0.008){
                color = Colors.get("PURPLE").cpy();
            }else if(random < 0.011){
                color = Colors.get("CYAN").cpy();
            }else if(random < 0.013){
                color = Colors.get("RED").cpy();
            }




            Texture kindleImg = ImageMaster.loadImage("mari_mod/images/effects/MariKindle.png");
            imgKindle = ImageMaster.vfxAtlas.addRegion("MariKindleEffect", kindleImg, 0,0,64,64);
        }

        public void update() {
            if(!this.poof) {
                this.lifeSpan -= Gdx.graphics.getDeltaTime();
                this.pos.x += this.vel.x;
                this.pos.y += this.vel.y;

                this.vel.x *= 0.995f;
                this.vel.y *= 0.995f;
            }else{
                for(int i = 0; i < 3; i++){
                    this.lifeSpan -= Gdx.graphics.getDeltaTime();
                    this.pos.x += this.vel.x * 8;
                    this.pos.y += this.vel.y;

                    this.vel.x *= 0.97f;
                    this.vel.y *= 0.97f;
                }
            }

            if(Math.random() < 1 - this.lifeSpan/1.5F){
                this.visibleFlicker = !this.visibleFlicker;
            }

            if(Math.random() < 1 - this.lifeSpan/1.5F){
                this.visibleFlicker = false;
            }else if(Math.random() < 1 - this.lifeSpan/1.5F){
                this.visibleFlicker = false;
            }

            if(Math.random() < this.lifeSpan/1.5F){
                this.visibleFlicker = true;
            }else if(Math.random() < this.lifeSpan/1.5F){
                this.visibleFlicker = true;
            }else if(Math.random() < this.lifeSpan/1.5F){
                this.visibleFlicker = true;
            }else if(Math.random() < this.lifeSpan/1.5F){
                this.visibleFlicker = true;
            }
        }

        public void render(SpriteBatch sb) {
            float scaleScaleScale = MathUtils.random(0.6F,1.0F);
            if(this.visibleFlicker) {
                sb.setColor(color);
                sb.draw(imgKindle,
                        pos.x - 40f,
                        pos.y - 40f,
                        32f,
                        32f,
                        64f,
                        64f,
                        Math.min(drawScale * (lifeSpan * 1.5f), 1.0f) * scaleScaleScale,
                        Math.min(drawScale * (lifeSpan * 1.5f), 1.0f) * scaleScaleScale,
                        0f);
            }
        }

        public boolean isDead() {
            return lifeSpan <= 0f;
        }
    }

}