import java.util.*;

/**
 * Models a vertex in the 8-puzzle search tree
 * @author Robert Allen
 * @Version 1.0
 **/ 

public class Vertex {
	private int state[][];
	private int blankRow;
	private int blankColumn;
	private int depth;
	private List<Vertex> adjacenyList = new ArrayList<Vertex>();


	/**
	 * Constructor that initializes all instance variables
	 * @param state  holds this vertex's board configuration
	 * @param blankRow  The row the blank is in
	 * @param blankColumn  The column the blank is in
	 * @param depth  The depth of this vertex within the search tree
	 */
	public Vertex(int[][] state, int blankRow, int blankColumn, int depth) {
		this.state = state;
		this.blankRow = blankRow;
		this.blankColumn = blankColumn;
		this.depth = depth;
	}

	/**
	 * returns the vertex's state
	 * @return The matrix containing the vertex's state
	 */
	public int[][] getState() {
		return state;
	}

	/**
	 * returns the row the blank is in
	 * @return Blank's row
	 */
	public int getBlankRow() {
		return blankRow;
	}

	/**
	 * returns the column the blank is in
	 * @return Blank's column
	 */
	public int getBlankColumn() {
		return blankColumn;
	}

	/**
	 * returns the dpeth the vertex within the search tree
	 * @return The vertex's depth
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * returns a list of vertices adjacent to this vertex
	 * @return The vertex's neighbors
	 */
	public List<Vertex> getAdjacencyList() {
		return getAdjacentVertices();
	}

	/**
	 * Creates and lists the vertices adjacent to this vertex
	 * @return The list containing the vertex's neigbors
	 */
	private List<Vertex> getAdjacentVertices() {
		if (isIndexValid(blankRow, blankColumn - 1)) {
			Vertex adjacent = new Vertex(moveBlank(state, blankRow, blankColumn, "left"), blankRow, blankColumn - 1, depth + 1);
			adjacenyList.add(adjacent);
		}
		if (isIndexValid(blankRow, blankColumn + 1)) {
			Vertex adjacent = new Vertex(moveBlank(state, blankRow, blankColumn, "right"), blankRow, blankColumn + 1, depth + 1);
			adjacenyList.add(adjacent);
		}
		if (isIndexValid(blankRow - 1, blankColumn)) {
			Vertex adjacent = new Vertex(moveBlank(state, blankRow, blankColumn, "up"), blankRow - 1, blankColumn, depth + 1);
			adjacenyList.add(adjacent);
		}
		if (isIndexValid(blankRow + 1, blankColumn)) {
			Vertex adjacent = new Vertex(moveBlank(state, blankRow, blankColumn, "down"), blankRow + 1, blankColumn, depth + 1);
			adjacenyList.add(adjacent);
		}
		return adjacenyList;
	}

	/**
	 * Checks if the given index falls within the boundries of the matrix it belongs to
	 * @param i  the row index
	 * @param j  the column index
	 * @return True if the index is valid and false otherwise
	 */
	private boolean isIndexValid(int i, int j) {
		if (i < 0 || i > 2 || j < 0 || j > 2) {
			return false;
		}
		return true;
	}

	/**
	 * Moves the blank one space to the left, right, up, or down
	 * @param puzzle  The matrix containing the puzzle board
	 * @param i  The row index of the blank
	 * @param j  The column index of the blank
	 * @param direction  The direction to move the blank
	 * @return The new board configuration after moving the blank
	 */
	private int[][] moveBlank(int[][] puzzle, int i, int j, String direction) {
		int tempPuzzle[][] = new int[puzzle.length][puzzle[0].length];
		for (int k = 0; k < puzzle.length; k++) {
			for (int l = 0; l < puzzle[0].length; l++) {
				tempPuzzle[k][l] = puzzle[k][l];
			}
		}
		int temp = tempPuzzle[i][j];
		if (direction == "left") {
			tempPuzzle[i][j] = puzzle[i][j - 1];
			tempPuzzle[i][j - 1] = temp;
		}
		else if (direction == "right") {
			tempPuzzle[i][j] = puzzle[i][j + 1];
			tempPuzzle[i][j + 1] = temp;
		}
		else if (direction == "up") {
			tempPuzzle[i][j] = puzzle[i - 1][j];
			tempPuzzle[i - 1][j] = temp;
		}
		else if (direction == "down") {
			tempPuzzle[i][j] = puzzle[i + 1][j];
			tempPuzzle[i + 1][j] = temp;
		}
		return tempPuzzle;
	}

	/**
	 * Counts the number of tiles om the board that are not in the correct positions
	 * @param goal  The goal state used to determine what the correct positions are
	 * @return The number of out of place tiles
	 */
	public int getTilesOutOfPlace(int[][] goal) {		//heuristic 1 for A* search
		int outOfPlace = 0;
		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[i].length; j++) {
				if (state[i][j] != goal[i][j] && state[i][j] != 0) {
					outOfPlace++;
				}
			}
		}
		return outOfPlace;
	}

	/**
	 * Calculates the manhattan distance of the vertex's state
	 * @param goal  The goal state used in finding the manhattan distance
	 * @return The manhattan distance
	 */
	public int getManhattanDistance(int[][] goal) {		//heuristic 2 for A* search
		int sumDistanceFromGoal = 0;
		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[i].length; j++) {
				int num = state[i][j];
				int k = 0, l = 0;
				while (k < state.length) {
					if (l >= state[k].length) {
						l = -1;
						k++;
					}
					else if (num == goal[k][l]) {
						sumDistanceFromGoal += Math.abs(i - k) + Math.abs(j - l);
					}
					l++;
				}
			}
		}
		return sumDistanceFromGoal;
	}

	/**
	 * Converts the matrix into a list
	 * @return A list containing the elements of the matrix in row major order
	 */
	public List<Integer> matrixToList() {
		List<Integer> l = new ArrayList<Integer>();
		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[i].length; j++) {
				l.add(state[i][j]);
			}
		}
		return l;
	}
}