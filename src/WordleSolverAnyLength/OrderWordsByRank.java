package WordleSolverAnyLength;

import java.util.Comparator;
import java.util.HashMap;

public class OrderWordsByRank implements Comparator <String>
{
	private HashMap<String, Integer> rankedWords;
	
	public OrderWordsByRank(HashMap<String, Integer> rankedWords)
	{
		this.rankedWords = rankedWords;
	}
	
	public int compare(String lhs, String rhs) 
	{
		return rankedWords.get(lhs) - rankedWords.get(rhs);
	}
}
