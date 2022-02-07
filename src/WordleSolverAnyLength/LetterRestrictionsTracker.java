package WordleSolverAnyLength;

/**
 * This class keeps track of the possible letters in a given position.
 * 
 * @author Edward Stanford
 * @version February 7, 2022
 */

import java.util.ArrayList;

public class LetterRestrictionsTracker 
{
	// Fields
	private ArrayList<Character> usableLetters;
	
	/**
	 * Constructor, make it so all 26 letters are possibilities.
	 */
	public LetterRestrictionsTracker()
	{
		usableLetters = new ArrayList<Character>();
		for(int i = 97; i <= 122; ++i)
		{
			usableLetters.add((char)(i));
		}
	}

	/**
	 * Check if a given letter is a possible letter for this position.
	 * @param letter, a char that is the letter to check.
	 * @return true if letter is a possible letter, false otherwise.
	 */
	public boolean isValidLetter(char letter) 
	{
		return usableLetters.contains(letter);
	}

	/**
	 * Remove a letter from the posssible letters.
	 * @param letter, a char that is the letter to remove.
	 */
	public void removeLetter(char letter) 
	{
		usableLetters.remove((Character) (letter));
	}
	
	/**
	 * Make it so there is only one possible letter in this position (known green letter.)
	 * @param letter, a char that is the letter to set as the only possible letter.
	 */
	public void setGreenLetter(char letter)
	{
		usableLetters.clear();
		usableLetters.add(letter);
	}
	
	/**
	 * Checks if the letter in this position is a known green letter.
	 * @return true if there is only one usable letter, false otherwise.
	 */
	public boolean isLetterKnown()
	{
		if(usableLetters.size() == 1)
		{
			return true;
		}
		
		return false;
	}
}
