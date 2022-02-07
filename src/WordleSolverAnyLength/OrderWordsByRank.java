package WordleSolverAnyLength;

/**
 * This comparator rankes words by their letter commonality score.
 * 
 * @author Edward Stanford
 * @version February 7, 2022
 */

import java.util.Comparator;
import java.util.HashMap;

public class OrderWordsByRank implements Comparator <String>
{
	// Fields
	private HashMap<String, Integer> rankedWords;
	
	/**
	 * Constructor, takes a HashMap of the words and their scores.
	 * @param rankedWords
	 */
	public OrderWordsByRank(HashMap<String, Integer> rankedWords)
	{
		this.rankedWords = rankedWords;
	}
	
	/**
	 * Compares two words based on their letter commonality scores.
	 */
	public int compare(String lhs, String rhs) 
	{
		return rankedWords.get(lhs) - rankedWords.get(rhs);
	}
}
