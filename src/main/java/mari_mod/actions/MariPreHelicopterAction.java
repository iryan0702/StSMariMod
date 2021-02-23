package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mari_mod.MariMod;
import mari_mod.effects.MariHelicopterEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariPreHelicopterAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariPreHelicopterAction.class.getName());
    private int damage;

    public MariPreHelicopterAction(int damage, AbstractMonster target) {
        this.actionType = ActionType.DAMAGE;
        this.duration = 0.001F;
        this.damage = damage;
        this.target = target;
    }

    public void update() {
        float averageX = 0.0f;
        float averageY = 0.0f;
        int monsters = 0;
        for(AbstractMonster mo: AbstractDungeon.getCurrRoom().monsters.monsters){
            if(!mo.halfDead && !mo.isDead){
                averageX += mo.hb.cX;
                averageY += mo.hb.cY;
                monsters++;
            }
        }
        averageX/=monsters;
        averageY/=monsters;
        MariMod.instantDamageAction(target, new DamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.NORMAL), AttackEffect.BLUNT_LIGHT);
        AbstractDungeon.actionManager.addToTop(new MariTheHelicopterAction(this.damage));
        AbstractDungeon.actionManager.addToTop(new VFXAction(new MariHelicopterEffect(averageX, averageY),1.3F));

        this.isDone = true;
    }

}
