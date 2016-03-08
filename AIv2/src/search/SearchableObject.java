/*
 * Interface that allows an object to be put into a search list node
 * A class that impliments this should also override equals and hashcode
 * to work correctly with comparing to the goal state as well as checking
 * for duplicates in the hash map
 */
package search;

import java.util.ArrayList;

public interface SearchableObject {
	public ArrayList<StateMovePair> generateChildren();
}
