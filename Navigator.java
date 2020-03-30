import java.util.ArrayList;

/**
 * il suo compito è far muovere il robot fino alla destinazione
 * 
 * @author Marco Schouten
 *
 */

public class Navigator {
    GridWorld myRobot;
    MazeMap myMap;
    AirLineHeuristic myHeuristic;
    ArrayList<GridWorld.Direction> backtrack;

    public void printWorld() {
	this.myRobot.print();
    }

    /**
     * Constructor
     * 
     * @param size
     * @param density
     * @param seed
     */
    Navigator(int size, double density, long seed) {
	this.myRobot = new GridWorld(size, density, seed);
	this.myMap = new MazeMap();
	this.myMap.add(myRobot.getCurrentCell());
	this.myHeuristic = new AirLineHeuristic(size);
	this.backtrack = new ArrayList<GridWorld.Direction>();
    }

    /**
     * 
     * metodo#1 fa spostare il robot di una cella. ("il cuore del navigatore")
     * 
     * @return
     */
    public boolean computeNextStep() {
	// check if the robot is arrived
	if (this.myRobot.targetReached() == true)
	    return true;
	else {
	    // scelgo su quale cella muovermi
	    ArrayList<GridWorld.Direction> DeepDirections = new ArrayList<GridWorld.Direction>();

	    // 1 - calcolo l'euristica
	    // priority[0] è la direzione più preferita
	    // priority[3] è la direzione meno preferita
	    GridWorld.Direction[] priorityDirection = this.myHeuristic.compute(myRobot.getCurrentCell());

	    // 2 - esamino tutte le celle adiacenti
	    for (GridWorld.Coordinate adjacentCell : myRobot.getAdjacentFreeCells()) {
		// 2.1 - per ogni cella calcolo la direzione da prendere per raggiungerla
		GridWorld.Direction adjacentCellDirection = computeDirection(myRobot.getCurrentCell(), adjacentCell);

		// 2.2 - per ogni cella valuto se è una cella "deep"
		if (!myMap.contains(adjacentCell)) {
		    DeepDirections.add(adjacentCellDirection);
		}
	    }

	    // 3 - se ho celle deep, il robot si muove in profondità.
	    // se ho più di una cella deep, il robot si muove sulla direzione più preferita
	    // possibile (stabilita dall'euristica calcolata precedentemente)
	    if (!DeepDirections.isEmpty()) {
		GridWorld.Direction move = null;
		boolean exit = false;
		for (int i = 0; i < priorityDirection.length && exit == false; i++) {
		    for (int j = 0; j < DeepDirections.size() && exit == false; j++) {
			if (priorityDirection[i] == DeepDirections.get(j)) {
			    move = priorityDirection[i];
			    exit = true;
			}
		    }
		}
		myRobot.moveToAdjacentCell(move);
		backtrack.add(computeBackDir(move));
		// System.out.println("robot-moved:" + move.toString());
		myMap.add(myRobot.getCurrentCell());
	    }

	    // 4 - se non ho celle deep, faccio backtracking
	    else if (DeepDirections.isEmpty()) {
		myMap.setInvalid(myRobot.getCurrentCell()); // la cella attuale NON verrà considerata per il path finale
							    // perché mi ha fatto finire in un vicolo cieco
		GridWorld.Direction move = backtrack.get(backtrack.size() - 1); // get last element of list
		backtrack.remove(backtrack.size() - 1); // remove last element
		myRobot.moveToAdjacentCell(move); // torno sulla celle precedente
		// System.out.println("robot-moved:" + move.toString());
	    }
	    return false; // ritorna false perché non è ancora arrivato a destinazione
	}
    }

    /**
     * metodo#2 è una utility per il metodo #1 calcola la direzione opposta da
     * quella in cui si è mosso. ( serve per fare il backtracking)
     * 
     * @param move
     * @return
     */
    private GridWorld.Direction computeBackDir(GridWorld.Direction move) {
	if (move == GridWorld.Direction.SOUTH)
	    return GridWorld.Direction.NORTH;

	if (move == GridWorld.Direction.NORTH)
	    return GridWorld.Direction.SOUTH;

	if (move == GridWorld.Direction.EAST)
	    return GridWorld.Direction.WEST;

	if (move == GridWorld.Direction.WEST)
	    return GridWorld.Direction.EAST;

	return null;
    }

    /**
     * metodo#3 è una utility per il metodo #1 calcola la direzione in cui muoversi
     * data la cella attuale e quella dove vorrebbe muoversi
     * 
     * @param currentCell
     * @param adjacentCell
     * @return
     */
    private GridWorld.Direction computeDirection(GridWorld.Coordinate currentCell, GridWorld.Coordinate adjacentCell) {
	int y = adjacentCell.row - currentCell.row;
	int x = adjacentCell.col - currentCell.col;

	if (x == 1 && y == 0)
	    return GridWorld.Direction.EAST;
	if (x == -1 && y == 0)
	    return GridWorld.Direction.WEST;
	if (x == 0 && y == -1)
	    return GridWorld.Direction.NORTH;
	if (x == 0 && y == 1)
	    return GridWorld.Direction.SOUTH;
	return null;
    }

    /**
     * metodo 4
     * 
     * @return la mappa
     */
    public MazeMap returnMap() {
	return myMap;
    };

    /**
     * metodo#5 serve per fare il check preliminare. verifica se esiste un percorso
     * valido verso il la coordianta goal
     * 
     * @return true se non esiste un percorso
     */
    public boolean targetNotReachable() {
	return this.myRobot.getMinimumDistanceToTarget() == -1;
    }

    /**
     * metodo#6 fa il check se il percorso trovato è valido.
     * 
     * @param myList
     * @return
     */
    public boolean checkAcyclic(ArrayList<GridWorld.Coordinate> myList) {
	return myRobot.checkPathAcyclic(myList);
    }
}
