import java.util.Comparator;

/**
 * Comparator that compares vertices based on their manhattan distance 
 * @author Robert Allen
 * @Version 1.0
 **/ 

public class ManhattanComparator implements Comparator<Vertex> {
	private int goal[][];

	/**
	 * Constructor that initializes the goal state
	 * @param goal  The goal state necessary for comparison
	 */
	public ManhattanComparator(int[][] goal) {
		this.goal = goal;
	}

	//@Override
	public int compare(Vertex u, Vertex v) {
		if (u.getDepth() + u.getManhattanDistance(goal) > v.getDepth() + v.getManhattanDistance(goal)) {
			return 1;
		}
		if (u.getDepth() + u.getManhattanDistance(goal) < v.getDepth() + v.getManhattanDistance(goal)) {
			return -1;
		}
		return 0;
	}
}