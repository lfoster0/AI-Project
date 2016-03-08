package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import puzzle.EightPuzzle;
import puzzle.Move;
import search.SearchListNode;
import search.StateMovePair;

public class SearchListNodeTest {

	@Test
	public void testGenerateChildren() {
		EightPuzzle puzzle = new EightPuzzle(new int[]{8,7,6,5,4,EightPuzzle.EMPTY_SPACE,2,1,3});
		SearchListNode testnode = new SearchListNode<Move>(null,puzzle,0,Move.START);

		 ArrayList<SearchListNode> searchListNodeChildren = testnode.generateChildren();
		 ArrayList<StateMovePair> eightPuzzleChildren = puzzle.generateChildren();
		 
		 assertEquals("Incorrect number of children generated",true,searchListNodeChildren.size() == eightPuzzleChildren.size());
		 
		 for(int i = 0; i< searchListNodeChildren.size(); i++)
		 {
			 assertEquals("Incorrect Parent", true ,searchListNodeChildren.get(i).getParent().equals(testnode));
			 
			 assertEquals("Children State Differ from EightPuzzle and SearchListNode", true, searchListNodeChildren.get(i).getState().equals(eightPuzzleChildren.get(i).getState()));
			 assertEquals("Children Move Differ from EightPuzzle and SearchListNode", true, searchListNodeChildren.get(i).getMove().equals(eightPuzzleChildren.get(i).getMove()));
		 }
	}

}
