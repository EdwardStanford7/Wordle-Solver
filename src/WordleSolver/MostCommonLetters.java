package WordleSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MostCommonLetters 
{
	//Fields
	
	public static ArrayList<String> wordleWords = new ArrayList<String>();
	
	public static char[][] findMostCommonLetters() throws FileNotFoundException 
	{	
		int[][] numCharUses = new int[5][26];
		
		Scanner s = new Scanner(new File("src/WordleSolver/wordle_words.txt"));
		//Scanner s = new Scanner(new File("src/all_five_letter_words.txt")); // For hellowordl
		
		while (s.hasNext())
		{
			String word = s.next();
			if(! wordleWords.contains(word))
			{
				wordleWords.add(word);
			}
			
			for(int i = 0; i < 5; ++i)
			{
				numCharUses[i][(((int) word.charAt(i)) - 97)] += 1;
			}
		}
				
		char[][] mostCommonChar = new char[5][26];
		
		for(int i = 0; i < 5; ++i)
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
