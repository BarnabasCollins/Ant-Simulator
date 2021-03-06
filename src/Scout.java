import java.util.*;

import javax.swing.ImageIcon;

public class Scout extends Ant {

    Scout(ColonyNode node) {
        location = node;
        lastTurn = -1;
    }

    Scout() {

  
    }

    public void nextTurn(int curTurn) {
        if (lastTurn == curTurn)
            return;
      
        if ((curTurn - birth) > 10 * 365) {
            death();
            return;
        }
        lastTurn = curTurn;
        Random moveGen = new Random();
        ArrayList<ColonyNode> adjacentList = location.getAdjacentNodes();
        ColonyNode destination;
        destination = adjacentList.get(moveGen.nextInt(adjacentList.size()));
        move(destination);

    }

    public void move(ColonyNode node) {
        location.removeAnt(this);
        location = node;
        location.addAnt(this);

        if (!(location.isVisible())) {
            location.setVisible(true);
        }

    }


}