//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import mari_mod.MariMod;
import mari_mod.MariSavables;
import mari_mod.cards.Mari_Stewshine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.UUID;

public class MariClosureAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariClosureAction.class.getName());
    private int miscIncrease;
    private UUID uuid;

    public MariClosureAction(UUID targetUUID, int miscValue, int miscIncrease) {
        this.miscIncrease = miscIncrease;
        this.uuid = targetUUID;
    }

    public void update() {
        // REGULAR INTERACTIONS
        Iterator var1 = AbstractDungeon.player.masterDeck.group.iterator();

        AbstractCard c;
        while(var1.hasNext()) {
            c = (AbstractCard)var1.next();
            if (c.uuid.equals(this.uuid)) {
                c.misc += this.miscIncrease;
                c.applyPowers();
                c.baseBlock = c.misc;
                c.isBlockModified = false;
                logger.info("REGULAR CLOSURE FOUND");
            }
        }

        for(var1 = GetAllInBattleInstances.get(this.uuid).iterator(); var1.hasNext(); c.baseBlock = c.misc) {
            c = (AbstractCard)var1.next();
            c.misc += this.miscIncrease;
            c.applyPowers();
        }

        // STEWSHINE INTERACTIONS

        var1 = AbstractDungeon.player.masterDeck.group.iterator();

        Mari_Stewshine stewshineVersion = null;

        while(var1.hasNext()) {
            AbstractCard nextCard =(AbstractCard)var1.next();
            if (nextCard instanceof Mari_Stewshine) {
                stewshineVersion = (Mari_Stewshine)nextCard;
                logger.info("STEWSHINE FOUND IN MASTER DECK");
                break;
            }
        }

        if(stewshineVersion != null) {
            UUID stewshineuuid = stewshineVersion.uuid;

            //MASTER DECK STEWSHINE

            AbstractCard stewshineClosure = null;
            if (stewshineVersion.card1.uuid == this.uuid) {
                stewshineClosure = stewshineVersion.card1;
            }
            if (stewshineVersion.card2.uuid == this.uuid) {
                stewshineClosure = stewshineVersion.card2;
            }
            if (stewshineVersion.card3.uuid == this.uuid) {
                stewshineClosure = stewshineVersion.card3;
            }
            if (stewshineClosure != null) {
                logger.info("CLOSURE IN STEWSHINE FOUND");
                stewshineClosure.misc += this.miscIncrease;
                stewshineClosure.applyPowers();
                stewshineClosure.baseBlock = stewshineClosure.misc;
                stewshineClosure.isBlockModified = false;
            }

            //IN BATTLE STEWSHINE

            Mari_Stewshine inBattleStewshine = null;
            for (var1 = GetAllInBattleInstances.get(stewshineuuid).iterator(); var1.hasNext(); ) {
                inBattleStewshine = (Mari_Stewshine) var1.next();
                logger.info("IN BATTLE STEWSHINE FOUND");
            }

            MariSavables saves = MariMod.saveableKeeper;
            stewshineClosure = null;
            if (inBattleStewshine != null) {
                if (inBattleStewshine.card1.uuid == this.uuid) {
                    stewshineClosure = inBattleStewshine.card1;
                    saves.stewshineCardA.misc += this.miscIncrease;
                }
                if (inBattleStewshine.card2.uuid == this.uuid) {
                    stewshineClosure = inBattleStewshine.card2;
                    saves.stewshineCardB.misc += this.miscIncrease;
                }
                if (inBattleStewshine.card3.uuid == this.uuid) {
                    stewshineClosure = inBattleStewshine.card3;
                    saves.stewshineCardC.misc += this.miscIncrease;
                }
            }

            if (stewshineClosure != null) {
                logger.info("CLOSURE IN STEWSHINE FOUND");
                stewshineClosure.misc += this.miscIncrease;
                stewshineClosure.applyPowers();
                stewshineClosure.baseBlock = stewshineClosure.misc;
                stewshineClosure.isBlockModified = false;
            }

        }
        this.isDone = true;
    }
}
