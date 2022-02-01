package WordleSolverAnyLength;

import java.util.ArrayList;

public class WordRestricionsTracker 
{
	// Fields
	private LetterRestrictionsTracker[] letterRestrictions;
	private ArrayList<Character> yellowLetters;
	private ArrayList<Character> greenLetters;
	private ArrayList<Character> greyLetters;
	private int wordLength;
	
	public WordRestricionsTracker(int wordLength)
	{
		letterRestrictions = new LetterRestrictionsTracker[wordLength];
		
		for(int i = 0; i < wordLength; ++i)
			letterRestrictions[i] = new LetterRestrictionsTracker();
		
		yellowLetters = new ArrayList<Character>();
		greenLetters = new ArrayList<Character>();
		greyLetters = new ArrayList<Character>();
		
		this.wordLength = wordLength;
	}
	
	public void addRestriction(char letter, int position, String color)
	{
		if(color.equals("green"))
		{
			
			greenLetters.add(letter);
			letterRestrictions[position].setGreenLetter(letter);
		}
		
		if(color.equals("yellow"))
		{
			yellowLetters.add(letter);
			letterRestrictions[position].removeLetter(letter);
		}
		
		if(color.equals("grey"))
		{	
			if(! greenLetters.contains(letter))
			{
				if(! yellowLetters.contains(letter))
				{
					greyLetters.add(letter);
					
					for(LetterRestrictionsTracker tracker: letterRestrictions)
					{
						tracker.removeLetter(letter);
					}
				}
			}
			
			letterRestrictions[position].removeLetter(letter);
		}		
	}

	public boolean isValidWord(String word) 
	{		
		if(word.length() != wordLength)
		{
			return false;
		}
		
		for(char letter: greenLetters)
		{
			if(! word.contains(String.valueOf(letter)))
			{				
				return false;
			}
		}
		
		for(char letter: yellowLetters)
		{
			if(! word.contains(String.valueOf(letter)))
			{
				return false;
			}
		}
		
		for(int i = 0; i < word.length(); ++i)
		{
			if(! letterRestrictions[i].isValidLetter(word.charAt(i)))
			{
				return false;
			}
		}
				
		return true;
	}	
	
	public boolean isLetterKnown(int position)
	{
		return letterRestrictions[position].isLetterKnown();
	}
}
