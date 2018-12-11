
import java.util.*;

public class Soldier extends Ant {

    Soldier(ColonyNode node) {
        location = node;
        lastTurn = -1;

    }

    Soldier() {

    }

    public void nextTurn(int curTurn) {
        if (lastTurn == curTurn)
            return;

        
        if ((curTurn - birth) > 10 * 365) {
            death();
            return;
        }

        lastTurn = curTurn;

        if (location.getAntsOfType(new Bala()).size() > 0) {
            
            combat();
        } else {
            Random moveGen = new Random();
            ArrayList<ColonyNode> adjacentList = location.getAdjacentNodes();
            ArrayList<ColonyNode> visibleList = new ArrayList<ColonyNode>();
            ColonyNode destination;
            for (int i = 0; i < adjacentList.size(); i++) {
                if (adjacentList.get(i).isVisible()) {
                    visibleList.add(adjacentList.get(i));
                }
            }
            
            destination = visibleList.get(moveGen.nextInt(visibleList.size()));
           
            for (ColonyNode n : visibleList) {
                if (n.getAntsOfType(new Bala()).size() > 0) {
                    destination = n;
                }
            }
            move(destination);
        }
    }

    public void move(ColonyNode node) {
        location.removeAnt(this);
        location = node;
        location.addAnt(this);

    }

    public void combat() {
        ArrayList<Ant> balaList = location.getAntsOfType(new Bala());
        Random kill = new Random();
        int half = kill.nextInt(2);
        if (half == 0) {
            balaList.get(0).death();
        }

    }
}