
import java.util.*;

public class Queen extends Ant {
    int curTurn;
    int lastID;
    int ID;

    public Queen(ColonyNode node) {
        location = node;
        lastID = 0;
        ID = 0;

    }

    Queen() {

    }

    public void hatch(Ant ant) {
        Random gen = new Random();
        int randomAnt = gen.nextInt(4);
        Ant newAnt;

        if (randomAnt < 2) {
            newAnt = new Forager(location);
        } else if (randomAnt == 2) {
            newAnt = new Soldier(location);
        } else {
            newAnt = new Scout(location);
        }

        if (ant != null) newAnt = ant;

        newAnt.setBirth(curTurn);
        lastID ++;
        newAnt.setID(lastID);
        location.addAnt(newAnt);

    }

    public void nextTurn(int curTurn) {
        this.curTurn = curTurn;
      
        if ((curTurn > (20 * 10 * 365)) || (location.getFood() < 1)) {
            death();
            return;
        }

       
        if ((curTurn % 10) == 0) {
            this.hatch(null);
        }
        
        makeBala();
     
        this.eat();


    }

    public void eat() {
        int foodCount = location.getFood();
        if (foodCount <= 0) {
            this.death();
        }
        foodCount = foodCount - 1;
        location.setFood(foodCount);
    }

    public void death() {
        System.out.println("The queen is dead, long live the queen.");
        location.nodeView.hideQueenIcon();
        location.colony.sim.endSimulation();
    }

    
    public void makeBala() {
        Random balaGen = new Random();
        int newBalaGen = balaGen.nextInt(100);
        ColonyNode balaLocation = location.colony.colonyGrid[0][0];

        if (newBalaGen < 30) {
            Bala newBala = new Bala(balaLocation);
            balaLocation.addAnt(newBala);
            newBala.setBirth(curTurn);
            lastID ++;
            newBala.setID(lastID);
        }
    }
}