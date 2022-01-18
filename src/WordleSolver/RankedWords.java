package WordleSolver;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class RankedWords
{
	// Fields
	private char[][] mostCommonLetters;
	private HashMap<String, Integer> rankedWordleWords;
	private WordRestricionsTracker wordRestrictions;
	
	
	public RankedWords() throws FileNotFoundException
	{
		mostCommonLetters = MostCommonLetters.findMostCommonLetters();
		rankedWordleWords = scoreWords(MostCommonLetters.wordleWords, mostCommonLetters);
		wordRestrictions = new WordRestricionsTracker();
	}
	
	public void removeWord(String word) 
	{
		rankedWordleWords.remove(word);
	}
	
	public void getInfo(Scanner input, String guess)
	{		
		for(int i = 0; i < 5; ++i)
		{
			while(true)
			{
				System.out.print("What color was " + guess.charAt(i) + ": ");
				String color = input.next();
				
				if(color.equals("grey"))
				{
					wordRestrictions.addRestriction(guess.charAt(i), i, color);
					break;
				}
				
				else if(color.equals("yellow"))
				{
					wordRestrictions.addRestriction(guess.charAt(i), i, color);
					break;
				}

				else if(color.equals("green"))
				{
					wordRestrictions.addRestriction(guess.charAt(i), i, color);
					break;
				}
				else
				{
					System.out.println("That is not a valid response.");
					continue;
				}
			}
		}
	}
	
	public String giveNextGuess() 
	{
		ArrayList<String> wordsToRemove = new ArrayList<String>();
		
		for(String word: rankedWordleWords.keySet())
		{
			if(! wordRestrictions.isValidWord(word))
			{
				wordsToRemove.add(word);
			}
		}
		
		for(String word: wordsToRemove)
		{
			rankedWordleWords.remove(word);
		}
		
		String bestWord = null;
		int bestScore = 10000;
		
		for(String word: rankedWordleWords.keySet())
		{
			if(rankedWordleWords.get(word) < bestScore)
			{
				bestWord = word;
				bestScore = rankedWordleWords.get(word);
			}
		}
		
		return bestWord;
	}
	
	private int getCharValue(char c, int positionInWord, char[][] mostCommonLetters)
	{
		for(int i = 0; i < 26; ++i)
		{
			if (mostCommonLetters[positionInWord][i] == c)
				return i;
		}
		
		System.out.print("ERROR ERROR ERROR");
		
		return 0;
	}
	
	private HashMap<String, Integer> scoreWords(ArrayList<String> words, char[][] mostCommonLetters)
	{
		HashMap<String, Integer> rankedWords = new HashMap<String, Integer>();
		
		for(String word: words)
		{
			rankedWords.put(word, 0);
			
			for(int i = 0; i < 5; ++i)
			{
				char letter = word.charAt(i);
				
				rankedWords.put(word, rankedWords.get(word) + getCharValue(letter, i, mostCommonLetters));
			}
		}
		
		return rankedWords;
	}
}
