/* Run when the user presses the SOLVE button on the Training Data tab.
 * Solves a given number of puzzles through Breadth First Search and
 * puts them in memory.  
 */
package worker;

import java.util.List;

import javax.swing.SwingWorker;

import puzzle.EightPuzzle;
import puzzle.Move;
import search.BreadthFirstSearch;
import search.SearchListNode;
import main.Main;
import main.SharedMem;

public class BFSPuzzleSolverWorker extends SwingWorker<Void, String>{

	@Override
	protected Void doInBackground() throws Exception {
		int num = Integer.parseInt(Main.window.textField_PuzzlesToSolve.getText());
		BreadthFirstSearch bfs;
		for(int i = 1; i<=num;i++)
		{
			EightPuzzle puzzle = new EightPuzzle();
			do{
				puzzle.shuffle();
			}
			while(SharedMem.getSolvedPuzzles().get(puzzle) != null);
			
			SearchListNode initialNode = new SearchListNode(null,puzzle,0,Move.START);
			bfs = new BreadthFirstSearch(initialNode, SharedMem.getGoalState());
			SearchListNode solvedNode = bfs.search();
			SharedMem.getSolvedPuzzles().put(initialNode.getState(), solvedNode);
			publish("Puzzle " + i + " Solved");
			
		}
		return null;
	}
	
	@Override
	protected void process(List<String> chunks)
	{
		Main.window.output(chunks.get(0));
		Main.window.setNumSolvedPuzzlesLabel(SharedMem.getSolvedPuzzles().size() + "");
	}
}
