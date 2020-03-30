import java.util.Arrays;

/**
 * il suo compito è determinare ad ogni passo qual é la direzione "migliore"
 * secondo l'euristica scelta. L'euristica calcola in linea d'aria la distanza e
 * definisce le direzioni di priorità
 * 
 * @author Marco Schouten
 *
 */

public class AirLineHeuristic implements Heuristic {

    /**
     * member variables
     * 
     * Inizialmente avevo definito un vettore di priorità statico.
     * GridWorld.Direction[] test = { GridWorld.Direction.SOUTH,
     * GridWorld.Direction.EAST, GridWorld.Direction.NORTH, GridWorld.Direction.WEST
     * };
     * 
     * Tuttavia, sperimentalmente, la miaeuristica fornisce con maggiori probabilità
     * il percorso più veloce verso la coordinata goal.
     * 
     */
    public GridWorld.Coordinate goal;
    public Record[] priority;
    GridWorld build = new GridWorld(1, 0.5, 0); // serve solo per creare una nuova cooridnata

    /**
     * Costruttore. dim è la dimensione del lato del labirinto
     */
    AirLineHeuristic(int dim) {
	goal = build.new Coordinate(dim - 1, dim - 1);
	priority = new Record[4];
	for (int i = 0; i < 4; i++) {
	    priority[i] = new Record();
	}
    }

    /**
     * prende come input la coordinata attuale dove si trova il robot. poi simula
     * uno spostamento nelle 4 direzioni assumendo che può spostarsi in ogni
     * direzione per calcolare ipoteticamente la scelta migliore definendo un ordine
     * di priorità di scelta (Se effettivamente il robot si muove o meno nella
     * direzione di prefernza lo stabilisce il Navigator facendo lo scan col radar,
     * per non andare contro il muro).
     * 
     * NOTA: il robot NON si muove, fa solo una simulazione di come varierebbe lo
     * stato se fosse in tale coordinata
     * 
     * ritorna un array. nella posizione 0 c'é la prima scelta; nella posizione 1
     * c'é la seconda scelta; nella posizione 2 c'é la terza scelta; nella posizione
     * 3 c'é* la quarta scelta.
     */

    public GridWorld.Direction[] compute(GridWorld.Coordinate myCoordinate) {

	GridWorld.Coordinate temp = build.new Coordinate(myCoordinate.row, myCoordinate.col);
	// simulate moving south
	priority[0].dir = GridWorld.Direction.SOUTH;
	temp.row++;
	priority[0].value = distanceOf(temp);
	temp.row--; // ripristina

	// simulate moving east
	priority[1].dir = GridWorld.Direction.EAST;
	temp.col++;
	priority[1].value = distanceOf(temp);
	temp.col--; // ripristina

	// simulate moving north
	priority[2].dir = GridWorld.Direction.NORTH;
	temp.row--; // simulate moving north
	priority[2].value = distanceOf(temp);
	temp.row++; // ripristina

	// simulate moving west
	priority[3].dir = GridWorld.Direction.WEST;
	temp.col--; // simulate moving north
	priority[3].value = distanceOf(temp);
	temp.col++; // ripristinaa

	// formatta il risultato
	Arrays.sort(priority);
	GridWorld.Direction[] result = new GridWorld.Direction[4];
	for (int i = 0; i < result.length; i++) {
	    result[i] = priority[i].dir;
	}
	return result;
    }

    /**
     * la logica dell' euristica proposta è la distanza in linea d'aria tra cella
     * attuale e cella goal. sqrt ((x1- x2)^2 + (y1-y2)^2).
     * 
     * Nota: cambiando euristica posso suggerire un diverso ordine di priorità
     * 
     * @param actualCoordinate
     * @return
     */
    private double distanceOf(GridWorld.Coordinate actualCoordinate) {
	double dy = actualCoordinate.row - this.goal.row;
	double dx = actualCoordinate.col - this.goal.col;
	return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }
}