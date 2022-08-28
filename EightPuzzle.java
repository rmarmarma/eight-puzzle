import java.util.*;

/**
 * Class that generates, initializes, and runs search algorithms to find a solution to the 8-puzzle game 
 * @author Robert Allen
 * @Version 1.0
 **/ 

public class EightPuzzle {

	/**
	 * Creates a random variation of the puzzle to use as the game's starting point
	 * @param rows  The number of rows the puzzle board will have
	 * @param columns  The number of columns the puzzle board will have
	 * @return The matrix containing the puzzle's initial state
	 */
	public static int[][] generateInitialPuzzle(int rows, int columns) {
		List<Integer> puzzleNums = new ArrayList<Integer>();
		for (int i = 0; i < rows * columns; i++) {
			puzzleNums.add(i);
		}
		Collections.shuffle(puzzleNums);
		int puzzle[][] = new int[rows][columns];
		int k = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				puzzle[i][j] = puzzleNums.get(k);
				k++;
			}
		}
		return puzzle;
	}

	/**
	 * Finds the number of inversions the puzzle's initial state has in order to choose the correct goal state
	 * @param puzzle  The matrix containing the puzzle board
	 * @return The number of inversions
	 */
	public static int getInversions(int[][] puzzle) {
		int inversions = 0;
		for (int i = 0; i < puzzle.length; i++) {
			for (int j = 0; j < puzzle[i].length; j++) {
				int num = puzzle[i][j];
				int k = i, l = j;
				while (k < puzzle.length) {
					if (l >= puzzle[k].length) {
						l = -1;
						k++;
					}
					else if (num > puzzle[k][l] && puzzle[k][l] != 0) {
						inversions++;
					}
					l++;
				}
			}
		}
		return inversions;
	}

	/**
	 * Chooses the correct goal state based on the parity of the puzzle's initial state
	 * @param parity  The parity of the puzzle's initial state
	 * @return The matrix containing the correct goal state
	 */
	public static int[][] initializeGoal(int parity) {
		if (parity == 0) {
			int[][] goal = {
				{1, 2, 3}, 
				{4, 5, 6}, 
				{7, 8, 0}
			};
			return goal;
		}
		int[][] goal = {
			{1, 2, 3},  
			{8, 0, 4}, 
			{7, 6, 5}
		};
		return goal;
	}

	/**
	 * Find the index of the blank space in the puzzle's initial state
	 * @param puzzle  The matrix containing the puzzle board
	 * @return The blank space's index
	 */
	public static int[] findBlank(int[][] puzzle) {
		int blankPosition[] = new int[2];
		boolean flag = false;
		for (int i = 0; i < puzzle.length; i++) {
			for (int j = 0; j < puzzle[i].length; j++) {
				if (puzzle[i][j] == 0) {
					blankPosition[0] = i;
					blankPosition[1] = j;
					flag = true;
					break;
				}
			}
			if (flag) {
				break;
			}
		}
		return blankPosition;
	}

	/**
	 * Prints the given state of the puzzle
	 * @param puzzle  The current state to be printed
	 */
	public static void printAsMatrix(int[][] puzzle) {
		for (int i = 0; i < puzzle.length; i++) {
			for (int j = 0; j < puzzle[i].length; j++) {
				System.out.print(puzzle[i][j] + " ");
			}
			System.out.println(" ");
		}
	}

	/**
	 * Evaluates whether the given state is equal to the goal state
	 * @param puzzle  The puzzle state in question
	 * @param goal  The goal state to be compared to
	 * @return True if the given state is equal to the goal state and false otherwise
	 */
	public static boolean isGoal(int[][] puzzle, int[][] goal) {
		for (int i = 0; i < puzzle.length; i++) {
			for (int j = 0; j < puzzle[i].length; j++) {
				if (puzzle[i][j] != goal[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Implementation of Breadth First Search used to solve the 8-puzzle
	 * @param goal  The goal state to be obtained
	 * @param root  The root of the search tree
	 * @return an array containing the number of moves it took to reach the goal state and the number of vertices that were explored while looking for the goal state
	 */
	public static int[] BFS(int[][] goal, Vertex root) {
		Queue<Vertex> q = new ArrayDeque<Vertex>();
		List<List<Integer>> nodesExplored = new ArrayList<List<Integer>>();
		int numMovesAndNodesExplored[] = {0, 0};
		q.add(root);
		while (!q.isEmpty()) {
			Vertex u = q.remove();
			nodesExplored.add(u.matrixToList());
			numMovesAndNodesExplored[1]++;
			if (isGoal(u.getState(), goal)) {
				numMovesAndNodesExplored[0] = u.getDepth();
				break;
			}
			for (Vertex v : u.getAdjacencyList()) {
				if (!q.contains(v) && !nodesExplored.contains(v.matrixToList())) {
					q.add(v);
				}
			}
		}
		return numMovesAndNodesExplored;
	}

	/**
	 * Implementation of A* Search used to solve the 8-puzzle
	 * @param goal  The goal state to be obtained
	 * @param root  The root of the search tree
	 * @param heuristic  The heurisitc to be used by the A* algorithm
	 * @return an array containing the number of moves it took to reach the goal state and the number of vertices that were explored while looking for the goal state
	 */
	public static int[] AStar(int[][] goal, Vertex root, int heuristic) {
		List<List<Integer>> nodesExplored = new ArrayList<List<Integer>>();
		int numMovesAndNodesExplored[] = {0, 0};
		Comparator<Vertex> comparator;
		if (heuristic == 1) {
			comparator = new OOPComparator(goal);
		}
		else {
			comparator = new ManhattanComparator(goal);
		}
		Queue<Vertex> pq = new PriorityQueue<Vertex>(comparator);
		pq.add(root);
		while (!pq.isEmpty()) {
			Vertex u = pq.poll();
			nodesExplored.add(u.matrixToList());
			numMovesAndNodesExplored[1]++;
			if (isGoal(u.getState(), goal)) {
				numMovesAndNodesExplored[0] = u.getDepth();
				break;
			}
			for (Vertex v : u.getAdjacencyList()) {
				if (!pq.contains(v) && !nodesExplored.contains(v.matrixToList())) {
					pq.add(v);
				}
			}
		}
		return numMovesAndNodesExplored;
	}

	/**
	 * Prints the results of the different search algorithms
	 * @param results  The results from searching to be printed
	 */
	public static void printResults(int[] results) {
		System.out.println("Total number of moves: " + results[0]);
		System.out.println("Total number of nodes explored: " + results[1]);
		System.out.println(" ");
	}

	//Main method
	public static void main(String[] args) {
		int puzzle[][] = generateInitialPuzzle(3, 3);
		int inversions = getInversions(puzzle);
		int parity = inversions % 2;
		int goal[][] = initializeGoal(parity);
		int blankPosition[] = findBlank(puzzle);
		Vertex root = new Vertex(puzzle, blankPosition[0], blankPosition[1], 0);
		System.out.println("INITIAL STATE:");
		printAsMatrix(puzzle);
		System.out.println("Number of inversions: " + inversions + "      ");
		System.out.println("Parity: " + parity);
		System.out.println("GOAL: ");
		printAsMatrix(goal);
		System.out.println(" ");
		System.out.println("*** Running Breadth First Search ***");
		int results[] = BFS(goal, root);
		printResults(results);
		System.out.println("*** Running A* Search with heuristic 1 (Tiles out of place) ***");
		int results2[] = AStar(goal, root, 1);
		printResults(results2);
		System.out.println("*** Running A* Search with heuristic 2 (Manhattan Distance) ***");
		int results3[] = AStar(goal, root, 2);
		printResults(results3);
	}
}