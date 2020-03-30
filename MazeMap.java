
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * il suo compito è fornire una struttura dati efficiente in grado di
 * memorizzare il percorso esplorato
 * 
 * @author Marco Schouten
 *
 */

public class MazeMap {

    /**
     * variabili membro
     */
    Map<GridWorld.Coordinate, Boolean> visitedNodes;

    /**
     * constructor
     */
    public MazeMap() {
	visitedNodes = new LinkedHashMap<GridWorld.Coordinate, Boolean>();
    }

    /**
     * metodo#1 add a new entry (key-value). Di default la entry appartiene al
     * percorso valido.
     * 
     * @param myCoordinate
     */
    void add(GridWorld.Coordinate myCoordinate) {
	this.visitedNodes.putIfAbsent(myCoordinate, true);
    }

    /**
     * metodo#2 check if this node is already visited
     * 
     * @param myCoordinate
     * @return
     */
    public boolean contains(GridWorld.Coordinate myCoordinate) {
	return this.visitedNodes.containsKey(myCoordinate);
    }

    /**
     * metodo#3 sets a coordinate invalid such that it won't be considered for the
     * result path
     * 
     * @param myCoordinate
     */
    public void setInvalid(GridWorld.Coordinate myCoordinate) {
	visitedNodes.put(myCoordinate, false);
    }

    /**
     * metodo#4 fa un check per sapere se la coordinata passata come parametro è
     * valida
     * 
     * @param myCoordinate
     * @return
     */
    public boolean checkValid(GridWorld.Coordinate myCoordinate) {
	return visitedNodes.get(myCoordinate);
    }

    /**
     * metodo#5. restituisce un oggetto iterable per fare il check se aciclico
     * 
     * @return
     */
    public ArrayList<GridWorld.Coordinate> getPath() {
	ArrayList<GridWorld.Coordinate> result = new ArrayList<GridWorld.Coordinate>();

	for (GridWorld.Coordinate name : visitedNodes.keySet()) {
	    if (visitedNodes.get(name) == true) {
		result.add(name);
	    }
	}
	return result;
    }

    /**
     * 
     * metodo #6. Stampa il path senza overhead di copia verso una struttura dati
     * idona per la verifica se il path è valido. Penso sia valido usare questo per
     * via della natura della LinkedHashMap e visto l'algoritmo che ho usato per
     * inserire i nodi nella mappa.
     * 
     * 
     * NOTE: nella mia implementazione il main NON usa questo metodo, ma fa il check
     * per verificare se il percorso è acilcico usando AcyclycMap().
     */
    public void printPath() {
	for (GridWorld.Coordinate name : visitedNodes.keySet()) {
	    if (visitedNodes.get(name) == true) {
		String key = name.toString();
		// String value = visitedNodes.get(name).toString();
		System.out.print(key);
	    }
	}
    }

}