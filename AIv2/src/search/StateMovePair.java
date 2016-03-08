/* Used in generateChildren() of SearchableObject.
 *	Stores the state of the child and the move required to get to that state
 *  from the previous ones.
 */
package search;

public class StateMovePair<T> {
	private SearchableObject state;
	private T move;
	
	public StateMovePair(SearchableObject state, T move){
		this.state = state;
		this.move = move;
	}
	
	public SearchableObject getState() {
		return state;
	}

	public T getMove() {
		return move;
	}

}
