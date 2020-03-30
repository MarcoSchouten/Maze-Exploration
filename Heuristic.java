/**
 * questa interfaccia definisce un contratto che ogni euristica deve rispettare
 * 
 * @author Marco Schouten
 *
 */
public interface Heuristic {
    public GridWorld.Direction[] compute(GridWorld.Coordinate myCoordinate);
}
