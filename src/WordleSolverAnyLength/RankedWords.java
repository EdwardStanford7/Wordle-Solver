package WordleSolverAnyLength;

/**
 * This class reads a dictionary file of all words and then
 * ranks them based on information given by the user.
 * 
 * @author Edward Stanford
 * @version February 7, 2022
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class RankedWords
{
	// Fields
	private char[][] mostCommonLetters;
	private ArrayList<String> allWords = new ArrayList<String>();
	private HashMap<String, Integer> rankedWords;
	private WordRestricionsTracker wordRestrictions;
	private int wordLength;
	private int numGreenLetters;
	
	/**
	 * Constructor. Reads dictionary file,
	 * finds the most common letters for a given word length,
	 * eliminates all words that are not the given word length,
	 * initializes the object that keeps track of the word restrictions,
	 * and ranks all the words.
	 * @param wordLength, the length of word the user is solving.
	 * @throws FileNotFoundException if dictionary file does not exist.
	 */
	public RankedWords(int wordLength) throws FileNotFoundException
	{
		mostCommonLetters = findMostCommonLetters(wordLength);
		rankedWords = scoreWords(allWords, mostCommonLetters);
		wordRestrictions = new WordRestricionsTracker(wordLength);
		this.wordLength = wordLength;
		numGreenLetters = 0;
	}
	
	/**
	 * Removes a word from all the possible words that can be guessed.
	 * @param word, a String that is the word to be removed.
	 */
	public void removeWord(String word) 
	{
		rankedWords.remove(word);
	}
	
	/**
	 * Gets information from the user based on what word they guessed,
	 * and what colors the letters were.
	 * @param input, the scanner object to get input from the user.
	 * @param guess, a String that is the word the user guessed.
	 */
	public void getInfo(Scanner input, String guess)
	{
		// Information has to be recorded in a specific order:
		// all green letters, then all yellow letters, then all grey letters.
		// Create HashMaps to temporarily store the info.
		HashMap<Character, Integer> greenLetters = new HashMap<Character, Integer>();
		HashMap<Character, Integer> yellowLetters = new HashMap<Character, Integer>();
		HashMap<Character, Integer> greyLetters = new HashMap<Character, Integer>();
		
		for(int i = 0; i < wordLength; ++i)
		{
			// If we know the letter is green,
			// we know we didn't guess something that doesn't have the letter in this position.
			if(wordRestrictions.isLetterKnown(i))
			{
				continue;
			}
			
			// Infinite loop in case user gives faulty input.
			// Will break out of loop once confirmed user entered correct input.
			while(true)
			{
				// Get what color this letter is from the user.
				System.out.print("What color was " + guess.charAt(i) + ": ");
				String color = input.next();
				
				// Check the three possible colors the letter could be.
				
				if(color.equals("green"))
				{
					++numGreenLetters;
					// If all letters are green, we know the letter. End program.
					if(numGreenLetters == wordLength)
					{
						System.out.println("You found the correct word. Good Job!");
						System.exit(0);
					}
					
					// Store info.
					greenLetters.put(guess.charAt(i), i);
					break;
				}
				
				if(color.equals("yellow"))
				{
					// Store info.
					yellowLetters.put(guess.charAt(i), i);
					break;
				}
				
				if(color.equals("grey"))
				{
					// Store info.
					greyLetters.put(guess.charAt(i), i);
					break;
				}
				
				// If still in the loop, user did not enter a valid response.
				// Continue to next iteration and try again.
				System.out.println("That is not a valid response.");
				continue;
			}
		}
		
		// Record the information gotten from the user.
		
		for(char letter: greenLetters.keySet())
		{
			wordRestrictions.addRestriction(letter, greenLetters.get(letter), "green");
		}
		
		for(char letter: yellowLetters.keySet())
		{
			wordRestrictions.addRestriction(letter, yellowLetters.get(letter), "yellow");
		}
		
		for(char letter: greyLetters.keySet())
		{
			wordRestrictions.addRestriction(letter, greyLetters.get(letter), "grey");
		}
	}
	
	/**
	 * Gets all possible words that can be guessed currently.
	 * @return, an ArrayList with all current possible guesses.
	 */
	public ArrayList<String> getBestWords()
	{
		ArrayList<String> possibleGuesses = new ArrayList<String>();
		
		// Loop through all words
		for(String word: rankedWords.keySet())
		{			
			// If it's currently a valid word, add it to ArrayList to be returned.
			if(wordRestrictions.isValidWord(word))
			{
				possibleGuesses.add(word);
			}
		}
		
		// Sort the possible words from best to worst.
		Collections.sort(possibleGuesses, new OrderWordsByRank(rankedWords));
		
		// Return the ArrayList of possible words.
		return possibleGuesses;
	}
	
	/**
	 * Helper method for the scoreWords method. 
	 * Gets how common a specific letter is in a specific position.
	 * @param c, a char that is the letter to be ranked.
	 * @param positionInWord, an int representing the position of the letter in the word.
	 * @param mostCommonLetters, a two dimensional array
	 * representing how common each letter is in each position.
	 * @return
	 */
	private int getCharValue(char c, int positionInWord, char[][] mostCommonLetters)
	{
		for(int i = 0; i < 26; ++i)
		{
			if (mostCommonLetters[positionInWord][i] == c)
			{
				return i;
			}
		}
		
		// Exectution should never get here. Debugging purposes for programmer.
		System.out.print("ERROR ERROR ERROR");
		
		return 0;
	}
	
	/**
	 * Private helper method for the constructor.
	 * Scores all words based on the releative frequency of the letters in it by position.
	 * @param words, an ArrayList that is the words to rank.
	 * @param mostCommonLetters, a two dimensional array
	 * representing how common each letter is in each position.
	 * @return a HashMap of words associated with their scores.
	 */
	private HashMap<String, Integer> scoreWords(ArrayList<String> words, char[][] mostCommonLetters)
	{
		HashMap<String, Integer> rankedWords = new HashMap<String, Integer>();
		
		for(String word: words)
		{
			rankedWords.put(word, 0);
			
			for(int i = 0; i < wordLength; ++i)
			{
				char letter = word.charAt(i);
				
				rankedWords.put(word, rankedWords.get(word) + getCharValue(letter, i, mostCommonLetters));
			}
		}
		
		return rankedWords;
	}
	
	/**
	 * Private helper method for the constructor.
	 * @param wordLength, the length of word to be ranking.
	 * @return a two dimensional array
	 * representing how common each letter is in each position for words of the given length.
	 * @throws FileNotFoundException if the dictionary file does not exist.
	 */
	private char[][] findMostCommonLetters(int wordLength) throws FileNotFoundException 
	{			
		int[][] numCharUses = new int[wordLength][26];
		
		Scanner s = new Scanner(new File("src/WordleSolverAnyLength/words_alpha.txt"));
		
		while (s.hasNext())
		{
			String word = s.next();
			
			if(word.length() != wordLength)
			{
				continue;
			}
			
			if(! allWords.contains(word))
			{
				allWords.add(word);
			}
			
			for(int i = 0; i < wordLength; ++i)
			{
				numCharUses[i][(((int) word.charAt(i)) - 97)] += 1;
			}
		}
				
		char[][] mostCommonChar = new char[wordLength][26];
		
		for(int i = 0; i < wordLength; ++i)
		{
			for(int j = 0; j < 26; ++j)
			{
				int largestNum = 0;
				int positionOfLargestNum = -1;
				
				for(int k = 0; k < 26; ++k)
				{
					if (numCharUses[i][k] == -1)
						continue;

					if (numCharUses[i][k] >= largestNum)
					{
						largestNum = numCharUses[i][k];
						positionOfLargestNum = k;
					}
				}
				
				mostCommonChar[i][j] = (char) (positionOfLargestNum + 97);
				numCharUses[i][positionOfLargestNum] = -1;
			}
		}
				
		return mostCommonChar;
	}
}
