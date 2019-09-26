package mari_mod.cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Colors;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.random.Random;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.MariMod;
import mari_mod.patches.CardColorEnum;
import mari_mod.powers.Radiance_Power;

import java.util.ArrayList;

//TODO: CREDIT CARD KINDLE PARTICLES: BLANK THE EVIL

public abstract class AbstractMariCard extends CustomCard {

    public int baseGoldCost = 0;
    public int goldCost = 0;
    public boolean upgradedGoldCost = false;

    public int baseRadiance = 0;
    public int radiance = 0;
    public boolean upgradedRadiance = false;

    public boolean isAnyTarget = false;
    private boolean targetingEnemy = false;
    private boolean isKindled = false;


    private static final float FPS_SCALE = (240f / Settings.MAX_FPS);
    public ArrayList<KindleParticle> particles;

    protected void upgradeGoldCost(int amount) {
        this.baseGoldCost += amount;
        this.goldCost = this.baseGoldCost;
        this.upgradedGoldCost = true;
    }

    protected void upgradeRadiance(int amount) {
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

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if(!canUse){
            return false;
        }else {
            if (AbstractDungeon.player.gold < this.goldCost) {
                canUse = false;
                this.cantUseMessage = "I don't have enough gold!";
            }
            return canUse;
        }
    }

    @Override
    public void update() {
        super.update();
        isKindled = false;
        if(MariMod.currentlyKindledCard == this) {
            MariMod.currentlyKindledCard = null;
            MariMod.currentKindleTarget = null;
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
                        this.targetingEnemy = true;
                        p.inSingleTargetMode = true;
                        this.target = CardTarget.ENEMY;
                        this.target_x = hoveredEnemy.hb.cX - this.hb.width * 0.6F - hoveredEnemy.hb_w * 0.6F;
                        this.target_y = hoveredEnemy.hb.cY;
                        this.applyPowers();
                    }
                    if(hoveredEnemy.hasPower(Radiance_Power.POWER_ID) && this.hasTag(MariCustomTags.KINDLE)){
                        MariMod.currentlyKindledCard = this;
                        MariMod.currentKindleTarget = hoveredEnemy;
                        isKindled = true;
                    }
                } else {
                    if(this.targetingEnemy) {
                        this.targetingEnemy = false;
                        p.inSingleTargetMode = false;
                        this.target = CardTarget.SELF;
                        this.applyPowers();
                    }
                    if(p.hasPower(Radiance_Power.POWER_ID) && this.hasTag(MariCustomTags.KINDLE)){
                        MariMod.currentlyKindledCard = this;
                        MariMod.currentKindleTarget = p;
                        isKindled = true;
                    }
                }
            }else if(this.targetingEnemy){
                this.targetingEnemy = false;
                this.target = CardTarget.SELF;
            }
        }

        for(KindleParticle p : particles){
            p.update();
        }
        particles.removeIf(KindleParticle::isDead);
        if(isKindled) {
            if (this.particles.size() < 40 && !Settings.DISABLE_EFFECTS) {
                for (int i = 0; i < FPS_SCALE; i++) {
                    if (Math.random() < 0.1) {
                        Vector2 point = generateRandomPointAlongEdgeOfHitbox();
                        particles.add(new KindleParticle(point.x, point.y, this.drawScale, this.upgraded, false));
                    }
                }
            }
        }

    }

    protected void successfulKindle(){
        if (!Settings.DISABLE_EFFECTS) {
            for (int i = 0; i < 50; i++) {
                Vector2 point = generateRandomPointAlongEdgeOfHitbox();
                particles.add(new KindleParticle(point.x, point.y, this.drawScale, this.upgraded, true));
            }
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

            color = Color.YELLOW.cpy();

            double random = Math.random();

            if(random < 0.01){
                color = Colors.get("PURPLE").cpy();;
            }else if(random < 0.013){
                color = Colors.get("CYAN").cpy();;
            }else if(random < 0.015){
                color = Colors.get("RED").cpy();;
            }else if(random < 0.265) {
                color = Colors.get("ORANGE").cpy();
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