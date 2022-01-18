package WordleSolver;

import java.util.ArrayList;

public class WordRestricionsTracker 
{
	// Fields
	LetterRestrictionsTracker[] letterRestrictions;
	ArrayList<Character> knownLettersInWord;
	
	public WordRestricionsTracker()
	{
		letterRestrictions = new LetterRestrictionsTracker[5];
		
		for(int i = 0; i < 5; ++i)
			letterRestrictions[i] = new LetterRestrictionsTracker();
		
		knownLettersInWord = new ArrayList<Character>();
	}
	
	public void addRestriction(char letter, int position, String color)
	{
		if(color.equals("grey"))
		{
			for(int i = 0 ; i < 5; ++i)
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
		for(char letter: knownLettersInWord)
		{
			if(! word.contains(String.valueOf(letter)))
			{
				return false;
			}
		}
		
		for(int i = 0; i < 5; ++i)
			if(! letterRestrictions[i].isValidLetter(word.charAt(i)))
				return false;
		
		return true;
	}	
}
