//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ModifyBlockAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

public class MariClosureAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(MariClosureAction.class.getName());
    private int miscIncrease;
    private int blockBefore;
    private UUID uuid;

    public MariClosureAction(UUID targetUUID, int blockBefore, int miscIncrease) {
        this.miscIncrease = miscIncrease;
        this.blockBefore = blockBefore;
        this.uuid = targetUUID;
    }

    public void update() {
        if(blockBefore == 0) {
            addToTop(new ModifyBlockAction(uuid, this.miscIncrease));
        }
        this.isDone = true;
    }
}
