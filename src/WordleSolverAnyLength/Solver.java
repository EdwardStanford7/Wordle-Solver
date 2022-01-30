package WordleSolverAnyLength;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * This application will help the user solve the word game wordle.
 * 
 * @Author Edward Stanford
 * @Version January 30, 2022
 */
public class Solver
{
	public static void main(String args[]) throws FileNotFoundException
	{
		ArrayList<String> bestPossibleGuesses = new ArrayList<String>();
		Scanner input = new Scanner(System.in);
		int wordLength;
		
		System.out.print("What length word are you solving? ");	
		wordLength = getWordLength(input);
		
		RankedWords rankedWords = new RankedWords(wordLength);
		bestPossibleGuesses = rankedWords.getBestWords();
		
		System.out.print("What was your first guess? ");
		String guess = getGuess(input, bestPossibleGuesses);
		rankedWords.getInfo(input, guess);
		
		for(int i = 0; i < 5; ++i)
		{
			bestPossibleGuesses = rankedWords.getBestWords();
			
			System.out.println("These are all the words you can guess, ranked from best to worst.");
			System.out.println(bestPossibleGuesses.toString());
			System.out.print("Which word did you guess? ");
									
			guess = getGuess(input, bestPossibleGuesses);
			
			rankedWords.getInfo(input, guess);
		}
		
		input.close();
	}
	
	private static int getWordLength(Scanner input)
	{
		int wordLength;
		wordLength = input.nextInt();
		
		while(wordLength < 4 || wordLength > 11)
		{
			System.out.println("This program can not solve words of that size");
			wordLength = input.nextInt();
		}
		
		return wordLength;
	}
	
	private static String getGuess(Scanner input, ArrayList<String> bestPossibleGuesses)
	{	
		String guess;
		guess = input.next();
		
		while(! bestPossibleGuesses.contains(guess))
		{
			System.out.println("That word is not one of the possible words to guess.");
			guess = input.next();
		}
		
		return guess;
	}
}
