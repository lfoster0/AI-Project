package tests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import puzzle.EightPuzzle;
import puzzle.Move;
import search.SearchListNode;
import search.SearchableObject;
import search.StateMovePair;

public class EightPuzzleTest {

	@Test
	public void testEightPuzzle() {
		EightPuzzle puzzle1 = new EightPuzzle();
		EightPuzzle puzzle2 = new EightPuzzle(new int[]{6,7,EightPuzzle.EMPTY_SPACE,5,4,3,2,1,8});
		assertArrayEquals("Not initialized to goal state", EightPuzzle.GOAL_STATE,puzzle1.getPuzzle());
		assertEquals("Space Index initialized incorrectly", 8 ,puzzle1.getSpaceIndex());
		assertEquals("Space Index initialized incorrectly", 2 ,puzzle2.getSpaceIndex());
	}

	@Test
	public void testCanMoveUp() {
		int[] board1 = {EightPuzzle.EMPTY_SPACE,7,6,5,4,3,2,1,8};
		int[] board2 = {7,EightPuzzle.EMPTY_SPACE,6,5,4,3,2,1,8};
		int[] board3 = {6,7,EightPuzzle.EMPTY_SPACE,5,4,3,2,1,8};
		EightPuzzle puzzle1 = new EightPuzzle(board1);
		EightPuzzle puzzle2 = new EightPuzzle(board2);
		EightPuzzle puzzle3 = new EightPuzzle(board3);
		assertEquals("Invalid Move Made On Top Row", false, puzzle1.canMoveUp());
		assertEquals("Invalid Move Made On Top Row", false, puzzle2.canMoveUp());
		assertEquals("Invalid Move Made On Top Row", false, puzzle3.canMoveUp());
		
		int[] board4 = { EightPuzzle.EMPTY_SPACE, 3, 8, 5, 7, 2, 6, 1, 4};
		EightPuzzle puzzle4 = new EightPuzzle(board4);
		assertEquals("Should not be able to move up", false, puzzle4.canMoveUp());
	}

	@Test
	public void testMoveDown() {
		int[] board1 = {8,7,6,5,4,3,2,1,EightPuzzle.EMPTY_SPACE};
		int[] board2 = {7,1,6,5,4,3,2,EightPuzzle.EMPTY_SPACE,8};
		int[] board3 = {6,7,2,5,4,3,EightPuzzle.EMPTY_SPACE,1,8};
		EightPuzzle puzzle1 = new EightPuzzle(board1);
		EightPuzzle puzzle2 = new EightPuzzle(board2);
		EightPuzzle puzzle3 = new EightPuzzle(board3);
		assertEquals("Invalid Move Made On Bottom Row", false, puzzle1.canMoveDown());
		assertEquals("Invalid Move Made On Bottom Row", false, puzzle2.canMoveDown());
		assertEquals("Invalid Move Made On Bottom Row", false, puzzle3.canMoveDown());
	}

	@Test
	public void testMoveLeft() {
		int[] board1 = {EightPuzzle.EMPTY_SPACE,7,6,5,4,3,2,1,8};
		int[] board2 = {8,7,6,EightPuzzle.EMPTY_SPACE,4,3,2,1,5};
		int[] board3 = {8,7,6,5,4,3,EightPuzzle.EMPTY_SPACE,1,2};
		EightPuzzle puzzle1 = new EightPuzzle(board1);
		EightPuzzle puzzle2 = new EightPuzzle(board2);
		EightPuzzle puzzle3 = new EightPuzzle(board3);
		assertEquals("Invalid Move Made On Left Side", false, puzzle1.canMoveLeft());
		assertEquals("Invalid Move Made On Left Side", false, puzzle2.canMoveLeft());
		assertEquals("Invalid Move Made On Left Side", false, puzzle3.canMoveLeft());
		
		int[] testboard = {8,7,6,5,4,EightPuzzle.EMPTY_SPACE,2,1,3};
		int[] correctboard = {8,7,6,5,EightPuzzle.EMPTY_SPACE,4,2,1,3};
		
		EightPuzzle testPuzzle = new EightPuzzle(testboard);
		testPuzzle.moveLeft();
		assertArrayEquals(testPuzzle.getPuzzle(), correctboard);
	}

	@Test
	public void testMoveRight() {
		int[] board1 = {8,7,6,5,4,3,2,1,EightPuzzle.EMPTY_SPACE};
		int[] board2 = {8,7,EightPuzzle.EMPTY_SPACE,5,4,3,2,1,6};
		int[] board3 = {8,7,6,5,4,EightPuzzle.EMPTY_SPACE,2,1,3};
		EightPuzzle puzzle1 = new EightPuzzle(board1);
		EightPuzzle puzzle2 = new EightPuzzle(board2);
		EightPuzzle puzzle3 = new EightPuzzle(board3);
		assertEquals("Invalid Move Made On Right Side", false, puzzle1.canMoveRight());
		assertEquals("Invalid Move Made On Right Side", false, puzzle2.canMoveRight());
		assertEquals("Invalid Move Made On Right Side", false, puzzle3.canMoveRight());
	}

	@Test
	public void testGetSpaceIndex() {
		EightPuzzle puzzle = new EightPuzzle();
		assertEquals("Space is in the wrong position", 8, puzzle.getSpaceIndex());
		puzzle.moveUp();
		puzzle.moveLeft();
		puzzle.moveLeft();
		puzzle.moveLeft();
		puzzle.moveRight();
		puzzle.moveUp();
		puzzle.moveDown();
		puzzle.moveDown();
		assertEquals("Space is in the wrong position", 7, puzzle.getSpaceIndex());
	}

	@Test
	public void testGetPuzzle() {
		EightPuzzle puzzle = new EightPuzzle();
		int[] expectedPuzzle = {1,2,3,4,8,5,7,EightPuzzle.EMPTY_SPACE,6};
		puzzle.moveUp();
		puzzle.moveLeft();
		puzzle.moveLeft();
		puzzle.moveLeft();
		puzzle.moveRight();
		puzzle.moveUp();
		puzzle.moveDown();
		puzzle.moveDown();
		assertArrayEquals("Puzzle is in the wrong state",expectedPuzzle, puzzle.getPuzzle());
	}
	
	@Test
	public void testShuffle(){
		EightPuzzle puzzle = new EightPuzzle();
		puzzle.shuffle();
		assertNotSame("Same as Goal State", EightPuzzle.GOAL_STATE, puzzle.getPuzzle());
	}
	
	@Test
	public void testIsValidState(){
		EightPuzzle puzzle = new EightPuzzle(new int[]{3,7,1,2,6,5,4,EightPuzzle.EMPTY_SPACE,8});
		assertEquals("State is valid since inversion number is even", true, puzzle.isValidState());
		puzzle = new EightPuzzle(new int[]{4,8,7,2,EightPuzzle.EMPTY_SPACE,1,3,5,6});
		assertEquals("State is not valid since inversion number is odd", false, puzzle.isValidState());
	}
	
	@Test
	public void testIsSolved(){
		int[] board1 = {1,2,3,4,5,6,7,8,EightPuzzle.EMPTY_SPACE};
		int[] board2 = {6,7,8,3,1,4,2,5,EightPuzzle.EMPTY_SPACE};
		EightPuzzle puzzle1 = new EightPuzzle(board1);
		EightPuzzle puzzle2 = new EightPuzzle(board2);
		EightPuzzle puzzle3 = new EightPuzzle();
		
		assertEquals("Puzzle1 is a solved puzzle",true,puzzle1.isSolved());
		assertEquals("Puzzle3 is a solved puzzle",true,puzzle3.isSolved());
		assertEquals("Puzzle2 should not be in solved state",false,puzzle2.isSolved());
	}
	
	@Test
	public void testGenerateChildren(){
		EightPuzzle puzzle = new EightPuzzle(new int[]{ EightPuzzle.EMPTY_SPACE, 3, 8, 5, 7, 2, 6, 1, 4});
		EightPuzzle correctDownPuzzle = new EightPuzzle(new int[]{ 5,3,8,EightPuzzle.EMPTY_SPACE,7,2,6,1,4});
		EightPuzzle correctRightPuzzle = new EightPuzzle(new int[]{ 3, EightPuzzle.EMPTY_SPACE,8,5,7,2,6,1,4});
		EightPuzzle goalState = new EightPuzzle();
		ArrayList<StateMovePair> children = puzzle.generateChildren();
		
		assertEquals("Too man children",2,children.size());
		//System.out.println(children.get(0).getState());
		//System.out.println(children.get(1).getState());
		assertEquals("Incorrect Child State",true,children.get(0).getState().equals(correctDownPuzzle));
		assertEquals("Incorrect Child State",true,children.get(1).getState().equals(correctRightPuzzle));
	}
	
	@Test
	public void testSearchableObject(){
		EightPuzzle puzzle1 = new EightPuzzle();
		EightPuzzle puzzle2 = new EightPuzzle(new int[]{8, 7,6,5,4,EightPuzzle.EMPTY_SPACE,2,1,3});
		SearchableObject puzzle3 = new EightPuzzle();
		
		assertEquals("Puzzle1 should not equal puzzle2",false, puzzle1.equals(puzzle2));
		assertEquals("Puzzle1 should equal puzzle3",true, puzzle1.equals(puzzle3));
	}
	
	@Test
	public void testKeyHashMap(){
		HashMap<Object, SearchListNode> visitedNodes = new HashMap<Object, SearchListNode>();
		SearchListNode node1 = new SearchListNode(null, new EightPuzzle(), 0, Move.START);
		SearchListNode node2 = new SearchListNode(null, new EightPuzzle(), 0, Move.START);
		
		visitedNodes.put(node1.getState(), node1);
		assertEquals("Node1 is in the map",true,visitedNodes.get(node1.getState()) != null);
		assertEquals("Node2's key is in the map",true,visitedNodes.get(node2.getState()) != null);
	}
}
