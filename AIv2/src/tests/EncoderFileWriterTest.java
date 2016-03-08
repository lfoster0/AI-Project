package tests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.junit.Test;

import puzzle.EightPuzzle;
import puzzle.Move;
import encoder.EightPuzzleBinaryEncoder;
import encoder.EncoderFileWriter;

public class EncoderFileWriterTest {
	
	@Test 
	public void testFileWrite() throws FileNotFoundException
	{
		File f = new File("file1.txt");
		EncoderFileWriter filewriter = new EncoderFileWriter(f);
		EightPuzzle puzzle = new EightPuzzle(new int[]{5,3,2,1,0,8,4,7,6});
		EightPuzzleBinaryEncoder encoder = new EightPuzzleBinaryEncoder();
		double[] encodedPuzzle = encoder.encode(puzzle, Move.UP);
		
		//Check if any lines are getting written to the file
		filewriter.append(encodedPuzzle);
		Scanner s = new Scanner(f);
		int lineCount = 0;
		String lineText = "";
		try{
			while((lineText = s.nextLine()) != null)
			lineCount++;
		}
		catch(NoSuchElementException e){}

		assertEquals("No Lines In File", true, lineCount > 0);
		s.close();
		
		f.delete();
	}
	
	@Test
	public void testDuplicateRemoval() throws FileNotFoundException
	{
		File f = new File("file2.txt");
		EncoderFileWriter filewriter = new EncoderFileWriter(f);
		EightPuzzle puzzle = new EightPuzzle(new int[]{5,3,2,1,0,8,4,7,6});
		EightPuzzleBinaryEncoder encoder = new EightPuzzleBinaryEncoder();
		double[] encodedPuzzle = encoder.encode(puzzle, Move.UP);
		
		//Check if any lines are getting written to the file
		filewriter.append(encodedPuzzle);
		Scanner s = new Scanner(f);
		int lineCount = 0;
		String lineText = "";
		try{
			while((lineText = s.nextLine()) != null)
					lineCount++;
		}
		catch(NoSuchElementException e){}
		s.close();
		
		filewriter.append(encodedPuzzle);
		s = new Scanner(f);
		int lineCount2 = 0;
		String lineText2 = "";
		try{
			while((lineText2 = s.nextLine()) != null)
				lineCount2++;
		}
		catch(NoSuchElementException e){}
		assertEquals("Duplicates in File", true, lineCount == lineCount2);
		
		f.delete();
		s.close();
	}
	
	@Test
	public void testFileAppending() throws FileNotFoundException
	{
		File f = new File("file3.txt");
		EncoderFileWriter filewriter = new EncoderFileWriter(f);
		EightPuzzle puzzle1 = new EightPuzzle(new int[]{5,3,2,1,0,8,4,7,6});
	EightPuzzle puzzle2 = new EightPuzzle(new int[]{7,1,2,6,8,3,4,5,0});
		EightPuzzleBinaryEncoder encoder = new EightPuzzleBinaryEncoder();
		double[] encodedPuzzle1 = encoder.encode(puzzle1, Move.UP);
		double[] encodedPuzzle2 = encoder.encode(puzzle2, Move.DOWN);
		
		//Check if any lines are getting written to the file
		filewriter.append(encodedPuzzle1);
		Scanner s = new Scanner(f);
		int lineCount = 0;
		String lineText = "";
		try{
			while((lineText = s.nextLine()) != null)
				lineCount++;
		}
		catch(NoSuchElementException e){}
		s.close();
		
		filewriter.append(encodedPuzzle2);
		s = new Scanner(f);
		int lineCount2 = 0;
		String lineText2 = "";
		try{
			while((lineText2 = s.nextLine()) != null)
				lineCount2++;
		}
		catch(NoSuchElementException e){}
		assertEquals("Error Appending to File", true, lineCount < lineCount2);
		
		f.delete();
	}
	
	
}
