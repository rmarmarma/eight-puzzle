import java.util.Comparator;

/**
 * Comparator that compares vertices based on their number of out of place tiles
 * @author Robert Allen
 * @Version 1.0
 **/ 

public class OOPComparator implements Comparator<Vertex> {
	private int goal[][];

	/**
	 * Constructor that initializes the goal state
	 * @param goal  The goal state necessary for comparison
	 */
	public OOPComparator(int[][] goal) {
		this.goal = goal;
	}

	//@Override
	public int compare(Vertex u, Vertex v) {
		if (u.getDepth() + u.getTilesOutOfPlace(goal) > v.getDepth() + v.getTilesOutOfPlace(goal)) {
			return 1;
		}
		if (u.getDepth() + u.getTilesOutOfPlace(goal) < v.getDepth() + v.getTilesOutOfPlace(goal)) {
			return -1;
		}
		return 0;
	}
}