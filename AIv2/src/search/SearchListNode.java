/*
 * Object that is searchable by BreadthFirstSearch.java
 * Has a generic type of move that is only used to provide output
 * to the user about the steps done to move between states.  This move
 * is not used in comparisons anywhere only for output.
 */
package search;

import java.util.ArrayList;

import puzzle.EightPuzzle;
import puzzle.Move;

public class SearchListNode<T> {

	private SearchListNode parent;
	private SearchableObject state;
	private double depthCost;
	private T move;
	
	public SearchListNode(SearchListNode parent, SearchableObject state,
			double depthCost, T move) {
		super();
		this.parent = parent;
		this.state = state;
		this.depthCost = depthCost;
		this.move = move;
	}

	public SearchListNode getParent() {
		return parent;
	}

	public SearchableObject getState() {
		return state;
	}

	public double getDepthCost() {
		return depthCost;
	}

	public T getMove() {
		return move;
	}

	/* Generates a list of child SearchListNodes by calling generateChildren() on the 
	 * SearchableObject that this SearchListNode contains.  Sets the move of the child
	 * SearchListNode to be the move required from the parent to get to the child.
	 */
	public ArrayList<SearchListNode> generateChildren() {
		ArrayList<SearchListNode> childrenNodes = new ArrayList<SearchListNode>();
		ArrayList<StateMovePair> childStateMoves = state.generateChildren();
		for(StateMovePair pair: childStateMoves){
			childrenNodes.add(new SearchListNode(this,pair.getState(),0,pair.getMove()));
		}
		
		return childrenNodes;
	}
	
}
