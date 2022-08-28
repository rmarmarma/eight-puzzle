# eight-puzzle

This project is a programming assignment from the Artificial Intelligence course taught at Trinity College in Hartford Connecticut

The purpose of the assignment is to implement three search algorithms — Breadth First Search, A* Search using the "tiles out of place"
heuristic, and A* Search using the manhattan distance heurisitc — in order to solve the 8-puzzle problem. Afterwards, we were to 
compare their performance, relative to one another, with regards to the number of search tree vertices explored and the length of the 
solution found.
		
After running the program ten times with ten randomly generated inputs, I found the following data.
On average:

Number of search tree vertices explored:
- BFS- 166,308
- A* (Tiles out of place)- 39,453
- A* (Manhattan)- 3,046

Length of solution found:
- BFS- 22
- A* (Tiles out of place)- 25
- A* (Manhattan)- 24
		
Breadth First Search was by far the slowest of the three algorithms, and though, as an uninformed searching strategy, that was 
expected, I was still surprised by the massive difference between BFS and the two A* searching strategies. There were times where
I terminated the program forcefully and ran it again with a different input because the BFS search was taking too long to run and
terminate naturally; in contrast, this never happened with either of the A* algorithms. 

A* Search using the "tiles out of place" heuristic perfomed significantly faster than Breadth First Search, but was also much 
slower than its A* counterpart, and this is reflected in the amount of vertices visited by algorithm in comparision to the 
others. Meanwhile, A* Search using the Manhattan Distance heuristic had by far the best performance, taking the least amount of time to 
run and finding the solution with the least amount of vertices explored. 
		
One things that I found interesting was that, despite exploring significantly more vertices during its search for the goal state,
Breadth First Search always found the shortest path to the solution (at least of the three). Although these differences are pretty
marginal and there were instances where the other two tied BFS in path length, there wasn't a single time where BFS had a longer
solution path to the goal than any of the informed searches. This makes sense as BFS always expands the entirety of a level before
moving on to the next level of the tree, and so, it will always find the solution with the shortest distance from the root.
