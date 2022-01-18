package WordleSolver;

import java.util.ArrayList;

public class LetterRestrictionsTracker 
{
	// Fields
	private char knownLetter;
	private ArrayList<Character> unusableLetters;
	
	public LetterRestrictionsTracker()
	{
		knownLetter = ' ';
		unusableLetters = new ArrayList<Character>();
	}

	public boolean isValidLetter(char letter) 
	{
		if(knownLetter == ' ')
		{
			if(unusableLetters.contains(letter))
			{
				return false;
			}
		}
		else if(knownLetter != letter)
		{
			return false;
		}
		
		return true;
	}

	public void addRestriction(char letter, String color) 
	{
		if(color.equals("green"))
		{
			knownLetter = letter;
		}
		else
		{
			unusableLetters.add(letter);
		}
	}
}
