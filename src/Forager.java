import java.util.*;

public class Forager extends Ant {
    boolean returnToNest;
    ArrayList<ColonyNode> moveHistory;
    int retracePosition;


    public Forager(ColonyNode node) {
        location = node;
        lastTurn = -1;
        returnToNest = false;
        moveHistory = new ArrayList<ColonyNode>();
        moveHistory.add(location);

    }

    Forager() {

    }

    public ColonyNode findMostPheromone() {
        ArrayList<ColonyNode> adjacentList = location.getAdjacentNodes();
        ArrayList<ColonyNode> randomNode = new ArrayList<ColonyNode>();
        Random chooseNode = new Random();
    
        for (Iterator<ColonyNode> iterator = adjacentList.iterator(); iterator.hasNext();) {
            ColonyNode s = iterator.next();
            if (moveHistory.contains(s) || !s.isVisible()) {
                iterator.remove();
            }
        }
       
        if (adjacentList.size() == 0) {
            adjacentList = location.getAdjacentNodes();
        }
     
        ColonyNode maxPher = adjacentList.get(0);

     
        for (int i = 1; i < adjacentList.size(); i++) {
            if (maxPher.isVisible() && maxPher.getPheromone() < adjacentList.get(i).getPheromone()) {
                maxPher = adjacentList.get(i);
            }
        }

        for (int j = 0; j < adjacentList.size(); j++) {
            if ((adjacentList.get(j).getPheromone() == maxPher.getPheromone()) && adjacentList.get(j).isVisible()) {
                randomNode.add(adjacentList.get(j));
            }
        }
        maxPher = randomNode.get(chooseNode.nextInt(randomNode.size()));
        return maxPher;
    }

    public void move(ColonyNode node) {
        location.removeAnt(this);
        location = node;
        location.addAnt(this);

        if (location.hasQueen() && returnToNest) {
            location.setFood(location.getFood() + 1);
            returnToNest = false;
            moveHistory.clear();
        }
        moveHistory.add(location);
    }

    public boolean foundFood() {
        if (location.getFood() > 0 && !(location.hasQueen())) {
            location.setFood(location.getFood() - 1);
            returnToNest = true;
            retracePosition = moveHistory.size() - 1;
            return true;

        } else return false;
    }


    public void nextTurn(int curTurn) {
        ColonyNode destination;
        if (lastTurn == curTurn)
            return;

        lastTurn = curTurn;
        if (returnToNest) {
            
            retracePosition --;
            destination = moveHistory.get(retracePosition);
            depositPheromones();
        } else {
            
            destination = findMostPheromone();
        }

        
        move(destination);
        if (!(returnToNest)) {
            foundFood();
        }
        
        if ((curTurn - birth) > 10 * 365) {
            death();
        }
    }

    public void depositPheromones() {
        if (!(location.hasQueen())) {
            if (location.getPheromone() < 1000) {
                location.setPheromone(location.getPheromone() + 10);
            }
        }
    }

    public void death() {
        if (returnToNest == true) {
            location.setFood(location.getFood() + 1);
        }
        location.removeAnt(this);
    }
}