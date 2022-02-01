package WordleSolverAnyLength;

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
	
	
	public RankedWords(int wordLength) throws FileNotFoundException
	{
		mostCommonLetters = findMostCommonLetters(wordLength);
		rankedWords = scoreWords(allWords, mostCommonLetters);
		wordRestrictions = new WordRestricionsTracker(wordLength);
		this.wordLength = wordLength;
		numGreenLetters = 0;
	}
	
	public void removeWord(String word) 
	{
		rankedWords.remove(word);
	}
	
	public void getInfo(Scanner input, String guess)
	{
		HashMap<Character, Integer> greenLetters = new HashMap<Character, Integer>();
		HashMap<Character, Integer> yellowLetters = new HashMap<Character, Integer>();
		HashMap<Character, Integer> greyLetters = new HashMap<Character, Integer>();
		
		for(int i = 0; i < wordLength; ++i)
		{
			if(wordRestrictions.isLetterKnown(i))
			{
				continue;
			}
			
			while(true)
			{
				System.out.print("What color was " + guess.charAt(i) + ": ");
				String color = input.next();
				
				if(color.equals("green"))
				{
					++numGreenLetters;
					if(numGreenLetters == wordLength)
					{
						System.out.println("You found the correct word. Good Job!");
						System.exit(0);
					}
					
					greenLetters.put(guess.charAt(i), i);
					break;
				}
				
				if(color.equals("yellow"))
				{
					yellowLetters.put(guess.charAt(i), i);
					break;
				}
				
				if(color.equals("grey"))
				{
					greyLetters.put(guess.charAt(i), i);
					break;
				}
				
				System.out.println("That is not a valid response.");
				continue;
			}
		}
		
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
	
	public ArrayList<String> getBestWords()
	{
		ArrayList<String> possibleGuesses = new ArrayList<String>();
		
		for(String word: rankedWords.keySet())
		{			
			if(wordRestrictions.isValidWord(word))
			{
				possibleGuesses.add(word);
			}
		}
		
		Collections.sort(possibleGuesses, new OrderWordsByRank(rankedWords));
		
		return possibleGuesses;
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
			
			for(int i = 0; i < wordLength; ++i)
			{
				char letter = word.charAt(i);
				
				rankedWords.put(word, rankedWords.get(word) + getCharValue(letter, i, mostCommonLetters));
			}
		}
		
		return rankedWords;
	}
	
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
