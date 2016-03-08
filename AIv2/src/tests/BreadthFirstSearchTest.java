package tests;

import java.util.ArrayList;

import org.junit.Test;

import puzzle.EightPuzzle;
import puzzle.Move;
import search.BreadthFirstSearch;
import search.SearchListNode;

public class BreadthFirstSearchTest {

	@Test
	public void testSearch() {
		SearchListNode<Move> goalstate = new SearchListNode(null,new EightPuzzle(),0,Move.START);
		//EightPuzzle puzzle = new EightPuzzle(new int[]{1,2,3,4,EightPuzzle.EMPTY_SPACE,6,7,5,8});
		
	    EightPuzzle puzzle = new EightPuzzle();
		puzzle.shuffle();
	    System.out.println(puzzle);
		SearchListNode startNode = new SearchListNode<Move>(null, puzzle, 0, Move.START);
		
		
		BreadthFirstSearch bfs = new BreadthFirstSearch(startNode, goalstate);
		SearchListNode result = bfs.search();
		System.out.println(result.getMove());
		
		/*while((result = result.getParent()) != null){
			System.out.println(result.getMove());
		} */
	}

}
