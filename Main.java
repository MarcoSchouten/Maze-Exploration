import java.util.ArrayList;

/**
 * Il compito del main è di usare gli oggetti per risolvere il problema e
 * stampare la soluzione su console
 * 
 * @author Marco Schouten
 *
 */

public class Main {

    public static void main(String[] args) {
	Navigator myNavigator = new Navigator(Integer.parseInt(args[0]), Double.parseDouble(args[1]),
		Long.parseLong(args[2]));

	if (myNavigator.targetNotReachable() == true) {
	    // non esite un path
	    ErrorMessage myError = new ErrorMessage("Legit path does not exists.");
	    System.out.print(myError.getMessage());

	} else {
	    // esite un path
	    // step 1 - risolve il labirinto
	    boolean targetReached = false;

	    do {
		targetReached = myNavigator.computeNextStep();

	    } while (targetReached == false);

	    // step 2 - stampa il path
	    MazeMap myMap = myNavigator.returnMap();
	    // controllo se il path è valido.
	    ArrayList<GridWorld.Coordinate> myPath = myMap.getPath();
	    if (myNavigator.checkAcyclic(myPath)) {
		System.out.print("Percorso: ");
		for (GridWorld.Coordinate myCoordinate : myPath) {
		    System.out.print(myCoordinate);
		}
	    } else {
		ErrorMessage myError = new ErrorMessage("Path was found, but was invalid");
		System.out.print(myError.getMessage());
	    }
	}
    }
}
