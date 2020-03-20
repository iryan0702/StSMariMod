//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mari_mod.actions;

import basemod.BaseMod;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.PlayerTurnEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DrawAndUpgradeCardAction extends AbstractGameAction {
    private boolean shuffleCheck;
    private static final Logger logger = LogManager.getLogger(DrawAndUpgradeCardAction.class.getName());
    private static final int MAX = BaseMod.MAX_HAND_SIZE;

    public DrawAndUpgradeCardAction(AbstractCreature source, int amount, boolean endTurnDraw) {
        this.shuffleCheck = false;
        if (endTurnDraw) {
            AbstractDungeon.topLevelEffects.add(new PlayerTurnEffect());
        } else if (AbstractDungeon.player.hasPower("No Draw")) {
            AbstractDungeon.player.getPower("No Draw").flash();
            this.setValues(AbstractDungeon.player, source, amount);
            this.isDone = true;
            this.duration = 0.0F;
            this.actionType = ActionType.WAIT;
            return;
        }

        this.setValues(AbstractDungeon.player, source, amount);
        this.actionType = ActionType.DRAW;
        if (Settings.FAST_MODE) {
            this.duration = Settings.ACTION_DUR_XFAST;
        } else {
            this.duration = Settings.ACTION_DUR_FASTER;
        }

    }

    public DrawAndUpgradeCardAction(AbstractCreature source, int amount) {
        this(source, amount, false);
    }

    public void update() {
        if (this.amount <= 0) {
            this.isDone = true;
        } else {
            int deckSize = AbstractDungeon.player.drawPile.size();
            int discardSize = AbstractDungeon.player.discardPile.size();
            if (!SoulGroup.isActive()) {
                if (deckSize + discardSize == 0) {
                    this.isDone = true;
                } else if (AbstractDungeon.player.hand.size() == MAX) {
                    AbstractDungeon.player.createHandIsFullDialog();
                    this.isDone = true;
                } else {
                    if (!this.shuffleCheck) {
                        int tmp;
                        if (this.amount + AbstractDungeon.player.hand.size() > MAX) {
                            tmp = MAX - (this.amount + AbstractDungeon.player.hand.size());
                            this.amount += tmp;
                            AbstractDungeon.player.createHandIsFullDialog();
                        }

                        if (this.amount > deckSize) {
                            tmp = this.amount - deckSize;
                            AbstractDungeon.actionManager.addToTop(new DrawAndUpgradeCardAction(AbstractDungeon.player, tmp));
                            AbstractDungeon.actionManager.addToTop(new EmptyDeckShuffleAction());
                            if (deckSize != 0) {
                                AbstractDungeon.actionManager.addToTop(new DrawAndUpgradeCardAction(AbstractDungeon.player, deckSize));
                            }

                            this.amount = 0;
                            this.isDone = true;
                        }

                        this.shuffleCheck = true;
                    }

                    this.duration -= Gdx.graphics.getDeltaTime();
                    if (this.amount != 0 && this.duration < 0.0F) {
                        if (Settings.FAST_MODE) {
                            this.duration = Settings.ACTION_DUR_XFAST;
                        } else {
                            this.duration = Settings.ACTION_DUR_FASTER;
                        }

                        --this.amount;
                        if (!AbstractDungeon.player.drawPile.isEmpty()) {
                            if(AbstractDungeon.player.drawPile.group.get(AbstractDungeon.player.drawPile.group.size()-1).canUpgrade()) {
                                AbstractDungeon.player.drawPile.group.get(AbstractDungeon.player.drawPile.group.size() - 1).upgrade();
                            }
                            AbstractDungeon.player.draw();
                            AbstractDungeon.player.hand.refreshHandLayout();
                        } else {
                            logger.warn("Player attempted to draw from an empty drawpile mid-DrawAction?MASTER DECK: " + AbstractDungeon.player.masterDeck.getCardNames());
                            this.isDone = true;
                        }

                        if (this.amount == 0) {
                            this.isDone = true;
                        }
                    }

                }
            }
        }
    }
}
