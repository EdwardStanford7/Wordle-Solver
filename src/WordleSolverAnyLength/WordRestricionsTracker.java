package WordleSolverAnyLength;

import java.util.ArrayList;

public class WordRestricionsTracker 
{
	// Fields
	private LetterRestrictionsTracker[] letterRestrictions;
	private ArrayList<Character> knownLettersInWord;
	private int wordLength;
	
	public WordRestricionsTracker(int wordLength)
	{
		letterRestrictions = new LetterRestrictionsTracker[wordLength];
		
		for(int i = 0; i < wordLength; ++i)
			letterRestrictions[i] = new LetterRestrictionsTracker();
		
		knownLettersInWord = new ArrayList<Character>();
		
		this.wordLength = wordLength;
	}
	
	public void addRestriction(char letter, int position, String color)
	{
		if(color.equals("grey"))
		{
			for(int i = 0 ; i < wordLength; ++i)
			{
				if (i != position)
				{
					letterRestrictions[i].addRestriction(letter, color);
				}
			}
		}
		else
		{
			knownLettersInWord.add(letter);
		}
		
		
		letterRestrictions[position].addRestriction(letter, color);
	}

	public boolean isValidWord(String word) 
	{
		if(word.length() != wordLength)
		{
			return false;
		}
		
		for(char letter: knownLettersInWord)
		{
			if(! word.contains(String.valueOf(letter)))
			{
				return false;
			}
		}
		
		for(int i = 0; i < wordLength; ++i)
			if(! letterRestrictions[i].isValidLetter(word.charAt(i)))
				return false;
		
		return true;
	}	
	
	public boolean isLetterKnown(int position)
	{
		return letterRestrictions[position].isLetterKnown();
	}
}
