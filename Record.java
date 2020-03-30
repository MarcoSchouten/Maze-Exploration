
/**
 * questa classe è di ausilio alla classe AirLineHeuristic. definisce un record
 * per un array di priorità. ogni record dev'essere comparable
 * 
 * @author Marco Schouten
 *
 */

public class Record implements Comparable<Record> {

    public GridWorld.Direction dir;
    public double value;

    Record() {
	this.dir = null;
	this.value = 0.0;
    }

    public void setDirection(String str) {
	dir = GridWorld.Direction.valueOf(str);
    }

    public void setValue(Double d) {
	value = d;
    }

    public int compareTo(Record other) {
	return Double.compare(this.value, other.value);
    }
}
