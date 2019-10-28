package mari_mod;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardSave;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MariSavables {
    public int tester;

    //GAME STATES
    public boolean firstEventYet;
    public AbstractCard.CardTags currentClass;

    //STEWSHINE SAVES
    public CardSave stewshineCardA;
    public CardSave stewshineCardB;
    public CardSave stewshineCardC;
    public int stewshineCost;
    public int stewshineCards;
    public boolean stewshineSetup;

    //EVENT SAVES -----------
    //HIRED GUN
    public boolean hiredGunVisited;
    public boolean hiredGunHired;
    public int hiredGunX;
    public int hiredGunY;

    public boolean tunnelOfStarsVisited;

    public boolean snackShackVisited;

    public MariSavables(){
        tester = 0;
    }


    public void copyStats(MariSavables loadedSavables){
        this.tester = loadedSavables.tester;

        this.firstEventYet = loadedSavables.firstEventYet;
        this.currentClass = loadedSavables.currentClass;

        this.stewshineCardA = loadedSavables.stewshineCardA;
        this.stewshineCardB = loadedSavables.stewshineCardB;
        this.stewshineCardC = loadedSavables.stewshineCardC;
        this.stewshineCost = loadedSavables.stewshineCost;
        this.stewshineCards = loadedSavables.stewshineCards;
        this.stewshineSetup = loadedSavables.stewshineSetup;

        this.hiredGunVisited = loadedSavables.hiredGunVisited;
        this.hiredGunHired = loadedSavables.hiredGunHired;
        this.hiredGunX = loadedSavables.hiredGunX;
        this.hiredGunY = loadedSavables.hiredGunY;

        this.tunnelOfStarsVisited = loadedSavables.tunnelOfStarsVisited;
        this.snackShackVisited = loadedSavables.snackShackVisited;
    }

    public void resetStats(){
        this.firstEventYet = false;
        //this.currentClass = null; //do not reset – setter is handled beforehand in initializeCardPoolsPatch
        this.stewshineCardA = null;
        this.stewshineCardB = null;
        this.stewshineCardC = null;
        this.stewshineCost = 0;
        this.stewshineCards = 0;
        this.stewshineSetup = false;

        this.hiredGunVisited = false;
        this.hiredGunHired = false;
        this.hiredGunX = 0;
        this.hiredGunY = 0;

        this.tunnelOfStarsVisited = false;
        this.snackShackVisited = false;
    }

    public int getTester() {
        return tester;
    }

    public void setTester(int tester) {
        this.tester = tester;
    }

    public int getStewshineCost() {
        return stewshineCost;
    }

    public void setStewshineCost(int stewshineCost) {
        this.stewshineCost = stewshineCost;
    }

    public void increaseStewshineCost(int increase){
        this.stewshineCost += increase;
    }
}
