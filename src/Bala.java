
import java.util.*;

import javax.swing.ImageIcon;
public class Bala extends Ant {

    Bala(ColonyNode node) {
        location = node;
        lastTurn = -1;

    }

    Bala() {


    }

    public void nextTurn(int curTurn) {
     
        if (lastTurn == curTurn)
            return;

      
        if ((curTurn - birth) > 10 * 365) {
            death();
            return;
        }

        lastTurn = curTurn;
        ArrayList<Ant> nonBala = location.getNonBalaAnts();
        
        if (nonBala.size() > 0) {
            fight();
        }

        else {
            Random moveGen = new Random();
            ArrayList<ColonyNode> adjacentList = location.getAdjacentNodes();
            ColonyNode destination;
            destination = adjacentList.get(moveGen.nextInt(adjacentList.size()));
            move(destination);
        }
    }

    public void move(ColonyNode node) {
        location.removeAnt(this);
        location = node;
        location.addAnt(this);

    }

    public void fight() {
        ArrayList<Ant> nonBala = location.getNonBalaAnts();
        Random kill = new Random();
        int half = kill.nextInt(2);
        if (half == 0) {
            nonBala.get(0).death();
        }
    }
}