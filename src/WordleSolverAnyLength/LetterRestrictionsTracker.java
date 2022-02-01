package WordleSolverAnyLength;

import java.util.ArrayList;

public class LetterRestrictionsTracker 
{
	// Fields
	private ArrayList<Character> usableLetters;
	
	public LetterRestrictionsTracker()
	{
		usableLetters = new ArrayList<Character>();
		for(int i = 97; i <= 122; ++i)
		{
			usableLetters.add((char)(i));
		}
	}

	public boolean isValidLetter(char letter) 
	{
		return usableLetters.contains(letter);
	}

	public void removeLetter(char letter) 
	{
		usableLetters.remove((Character) (letter));
	}
	
	public void setGreenLetter(char letter)
	{
		ArrayList<Character> lettersToRemove = new ArrayList<Character>();
		
		for(char possibleLetter: usableLetters)
		{
			if(possibleLetter != letter)
			{
				lettersToRemove.add(possibleLetter);
			}
		}
		
		for(char ltr: lettersToRemove)
		{
			usableLetters.remove((Character) (ltr));
		}
	}
	
	public boolean isLetterKnown()
	{
		if(usableLetters.size() == 1)
		{
			return true;
		}
		
		return false;
	}
}
