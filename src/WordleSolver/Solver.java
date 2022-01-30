package WordleSolver;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Solver 
{
	public static void main(String args[]) throws FileNotFoundException
	{
		RankedWords rankedWords = new RankedWords();
		
		boolean correctAnswer = false;
		int count = 0;
		Scanner input = new Scanner(System.in);
		String bestGuess = "";
		
		System.out.print("What word did you guess? ");
		bestGuess = input.next();
		
		while(bestGuess.length() != 5)
		{
			System.out.println("That word was not five letters long");
			System.out.print("What word did you guess? ");
			bestGuess = input.next();
		}
		
		while(! correctAnswer)
		{
			++count;
			if(count == 6)
				break;
			
			rankedWords.getInfo(input, bestGuess);
			
			bestGuess = rankedWords.giveNextGuess();
			System.out.println("The best word to guess is '" + bestGuess + "'");
			
			while(true)
			{
				System.out.print("Was that the right word? (y/n) ");
				String answer = input.next();
				
				if(answer.equals("y"))
				{
					System.out.println("Good Job!");
					correctAnswer = true;
					break;
				}
				else if(answer.equals("n"))
				{
					String printout = (count == 5 ? "Program couldn't solve your word. Sorry." :
																		 "It's OK, we'll get it next time.");
					System.out.println(printout);
					correctAnswer = false;
					rankedWords.removeWord(bestGuess);
					break;
				}
				else
				{
					System.out.println("That was not a valid answer.");
					continue;
				}
			}
		}
		
		input.close();
	}
}
