package WordleSolver;

public class LetterLocationMap 
{
	private char[] letters;
	private int[] locations;
	private int size;
	
	public LetterLocationMap()
	{
		letters = new char[] {' ', ' ', ' ', ' '};
		locations = new int [] {-1, -1, -1, -1};
		size = 0;
	}

	public void addLetter(char letter, int location) 
	{
		for(int usedLocation: locations)
			if (location == usedLocation)
				return;
			
		letters[size] = letter;
		locations[size] = location;
		++size;
	}

	public boolean isPossibleWord(String word) 
	{
		for(int i = 0; i < size; ++i)
		{
			if (word.charAt(locations[i]) != letters[i])
			{
				return false;
			}
		}
		
		return true;
	}
}
