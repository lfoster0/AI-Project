/* Searches a given starting state until it finds the given goal state.
 * Uses hashmap to store visted nodes to increase the speed of the search.
 * 
 */
package search;

import java.util.ArrayList;
import java.util.HashMap;

import puzzle.Move;

public class BreadthFirstSearch {
	
	private ArrayList<SearchListNode> openList;
	private ArrayList<SearchListNode> closedList;
	private HashMap<Object, SearchListNode> visitedNodes;
	private SearchListNode goalState;
	
	public BreadthFirstSearch(SearchListNode startingState, SearchListNode goalState){
		openList = new ArrayList<SearchListNode>();
		closedList = new ArrayList<SearchListNode>();
		visitedNodes = new HashMap<Object,SearchListNode>();
		this.openList.add(startingState);
		this.goalState = goalState;		
	}
	
	/* Does a Breadth First Search until the starting state
	 * matches the goal state.
	 */
	public SearchListNode search(){
		SearchListNode currentNode;
		ArrayList<SearchListNode> childStateList;
		while(!openList.isEmpty() && !openList.get(0).getState().equals(goalState.getState())){
			currentNode = openList.remove(0);
			childStateList = currentNode.generateChildren();
			closedList.add(currentNode);
			visitedNodes.put(currentNode.getState(), currentNode);
			
			for(SearchListNode<Move> childNode : childStateList)
			{
				if(!visitedNodes.containsKey(childNode.getState()))
				{
					openList.add(childNode);
					visitedNodes.put(childNode.getState(), childNode);
				}
			}
		}
		return openList.get(0);
	}
}
