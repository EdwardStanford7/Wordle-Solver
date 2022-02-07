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
	
	/**
	 * Constructor. Sets up the objects that will track each specific position in the word.
	 * Initializes ArrayLists that track all letter/color information.
	 * Initializes wordLength
	 * @param wordLength, an int that is the length of word the user is solving.
	 */
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
	
	/**
	 * Records the data given by the user for a specific letter and specific position.
	 * @param letter, a char that is the letter to record info about.
	 * @param position, an int that is the position in the word the letter is.
	 * @param color, a String that is the info to record.
	 */
	public void addRestriction(char letter, int position, String color)
	{
		// Record info.
		
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

	/**
	 * Checks if a given word is valid with the information we know.
	 * @param word, a String that is the word to check.
	 * @return true if word is valid, false otherwise.
	 */
	public boolean isValidWord(String word) 
	{		
		// If the word is not the right length, it is not valid. Return false.
		if(word.length() != wordLength)
		{
			return false;
		}
		
		// If the word does not have all the green letters in it, it is not valid. Return false.
		for(char letter: greenLetters)
		{
			if(! word.contains(String.valueOf(letter)))
			{				
				return false;
			}
		}
		
		// If the word does not have all the yellow letters in it, it is not valid. Return false.
		for(char letter: yellowLetters)
		{
			if(! word.contains(String.valueOf(letter)))
			{
				return false;
			}
		}
		
		// If any of the letters in the word are not valid for their specific position,
		// It is not valid. Return false.
		for(int i = 0; i < word.length(); ++i)
		{
			if(! letterRestrictions[i].isValidLetter(word.charAt(i)))
			{
				return false;
			}
		}
			
		// If reached here word must be valid. Return true.
		return true;
	}	
	
	/**
	 * Checks if a the letter in a given position is known.
	 * @param position, an int that is the position in the word to check.
	 * @return true if the letter in the specific position in known, false otherwise.
	 */
	public boolean isLetterKnown(int position)
	{
		return letterRestrictions[position].isLetterKnown();
	}
}
