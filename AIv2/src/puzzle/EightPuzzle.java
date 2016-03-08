/* Class representing an EightPuzzle.
 * Impliments SearchableObject so that it can be solved
 * using breadth first search.
 * 
 */
package puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import search.SearchableObject;
import search.StateMovePair;

public class EightPuzzle implements SearchableObject {
	public static final int EMPTY_SPACE = 0;
	public static final int[] GOAL_STATE = { 1, 2, 3, 4, 5, 6, 7, 8,
			EMPTY_SPACE };

	private int[] puzzle;
	private int spaceIndex;
	private int[] initialState;

	//Copy Constructor
	public EightPuzzle(EightPuzzle clone) {
		this.puzzle = new int[clone.getPuzzle().length];
		for(int i = 0; i<puzzle.length;i++){
			this.puzzle[i] = clone.getPuzzle()[i];
		}
		this.spaceIndex = clone.getSpaceIndex();
		this.initialState = clone.getPuzzle();
	}

	public EightPuzzle() {
		this.puzzle = new int[9];
		for (int i = 0; i < puzzle.length; i++) {
			this.puzzle[i] = GOAL_STATE[i];
		}
		this.initialState = this.puzzle;
		spaceIndex = puzzle.length - 1;
	}

	// TODO validate array given
	public EightPuzzle(int[] puzzle) {
		this.puzzle = puzzle;
		this.initialState = puzzle;
		for (int i = 0; i < this.puzzle.length; i++) {
			if (this.puzzle[i] == EMPTY_SPACE)
				spaceIndex = i;
		}
	}

	public boolean canMoveUp() {
		if (spaceIndex - 3 < 0)
			return false;
		return true;
	}

	/**
	 * Move the space up. Checks if the move is valid before making the move.
	 */
	public void moveUp() {
		if (canMoveUp()) {
			puzzle[spaceIndex] = puzzle[spaceIndex - 3];
			puzzle[spaceIndex - 3] = EMPTY_SPACE;
			spaceIndex -= 3;
		}

	}

	public boolean canMoveDown() {
		if (spaceIndex + 3 >= puzzle.length)
			return false;
		return true;
	}

	/**
	 * Move the space down. Checks if the move is valid before making the move.
	 */
	public void moveDown() {
		if (canMoveDown()) {
			puzzle[spaceIndex] = puzzle[spaceIndex + 3];
			puzzle[spaceIndex + 3] = EMPTY_SPACE;
			spaceIndex += 3;
		}
	}

	public boolean canMoveLeft() {
		if (spaceIndex % 3 == 0)
			return false;
		return true;
	}

	/**
	 * Move the space left. Checks if the move is valid before making the move.
	 */
	public void moveLeft() {
		if (canMoveLeft()) {
			puzzle[spaceIndex] = puzzle[spaceIndex - 1];
			puzzle[spaceIndex - 1] = EMPTY_SPACE;
			spaceIndex -= 1;
		}
	}
	
	public boolean canMoveRight(){
		if (spaceIndex % 3 == 2)
			return false;
		return true;
	}
	
	/**
	 * Move the space right. Checks if the move is valid before making the move.
	 */
	public void moveRight() {

		if(canMoveRight()) {
			puzzle[spaceIndex] = puzzle[spaceIndex + 1];
			puzzle[spaceIndex + 1] = EMPTY_SPACE;
			spaceIndex += 1;
		}
	}

	public int getSpaceIndex() {
		return spaceIndex;
	}

	public int[] getPuzzle() {
		return this.puzzle;
	}

	public int[] getInitialState() {
		return this.initialState;
	}

	public boolean isSolved() {
		for (int i = 0; i < GOAL_STATE.length; i++) {
			if (GOAL_STATE[i] != puzzle[i])
				return false;
		}

		return true;
	}

	public String toString() {
		String s = " " ;
		for (int i = 0; i < puzzle.length; i++) {
			s += puzzle[i] + " ";
		}
		return s;
	}

	/**
	 * Shuffles the puzzle multiple times until it finds a valid state and
	 * resets the initial state of the puzzle
	 */
	public void shuffle() {
		Random r = new Random();
		do {
			for (int i = 0; i < puzzle.length; i++) {
				int k = r.nextInt(puzzle.length);
				int tmp = puzzle[i];
				puzzle[i] = puzzle[k];
				puzzle[k] = tmp;
			}
			this.initialState = puzzle;
		} while (!isValidState());
		for(int i = 0; i < puzzle.length; i++){
			if(puzzle[i] == EightPuzzle.EMPTY_SPACE)
				spaceIndex = i;
		}

	}

	private int countInversions(int startingIndex) {
		int inversionCount = 0;
		for (int j = startingIndex + 1; j < puzzle.length; j++) {
			if (puzzle[startingIndex] > puzzle[j] && puzzle[j] != EMPTY_SPACE)
				inversionCount++;
		}
		return inversionCount;
	}

	/**
	 * Counts the number of inversions in the puzzle and based off criteria
	 * discussed in class.
	 * 
	 * @return true if the puzzle state is valid
	 */
	public boolean isValidState() {
		int inversionCount = 0;
		for (int i = 0; i < puzzle.length; i++) {
			inversionCount += countInversions(i);
		}
		if (inversionCount % 2 == 0)
			return true;

		return false;
	}


	public int hashCode(){
		return new HashCodeBuilder(17,37).append(puzzle).toHashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(obj == this)
			return true;
		if(!(obj instanceof EightPuzzle)){
			return false;
		}
		EightPuzzle compareTo = (EightPuzzle) obj;
		return new EqualsBuilder().append(this.puzzle, compareTo.puzzle).isEquals();
	}

	//TODO Clean this up
	@Override
	public ArrayList<StateMovePair> generateChildren() {
		ArrayList<StateMovePair> children = new ArrayList<StateMovePair>();
		if(canMoveDown()){
			EightPuzzle puzzle = new EightPuzzle(this);
			puzzle.moveDown();
			children.add(new StateMovePair<Move>(puzzle, Move.DOWN));
		}
		if (canMoveUp()) {
			EightPuzzle puzzle = new EightPuzzle(this);
			puzzle.moveUp();
			children.add(new StateMovePair<Move>(puzzle, Move.UP));
		}
		if (canMoveRight()) {
			EightPuzzle puzzle = new EightPuzzle(this);
			puzzle.moveRight();
			children.add(new StateMovePair<Move>(puzzle, Move.RIGHT));
		}
		if (canMoveLeft()) {
			EightPuzzle puzzle = new EightPuzzle(this);
			puzzle.moveLeft();
			children.add(new StateMovePair<Move>(puzzle, Move.LEFT));
		}
		return children;
	}
	
	public void draw(){
		System.out.println(puzzle[0] + " " + puzzle[1] + " " + puzzle[2]);
		System.out.println(puzzle[3] + " " + puzzle[4] + " " + puzzle[5]);
		System.out.println(puzzle[6] + " " + puzzle[7] + " " + puzzle[8]);
	}
}
