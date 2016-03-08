package tests;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

import puzzle.EightPuzzle;
import puzzle.Move;

import encoder.EightPuzzleBinaryEncoder;

public class EightPuzzleBinaryEncoderTest {

	@Test
	public void testEncode()
	{
		double[] correctEncoding = {-1,1,-1,1,-1,-1,1,1,-1,-1,1,-1,-1,-1,-1,1,-1,-1,-1,-1,1,-1,-1,-1,-1,1,-1,-1,-1,1,1,1,-1,1,1,-1,-1,-1,-1,1};
		EightPuzzle puzzle = new EightPuzzle(new int[]{5,3,2,1,0,8,4,7,6});
		EightPuzzleBinaryEncoder encoder = new EightPuzzleBinaryEncoder();
		double[] answer = encoder.encode(puzzle, Move.UP);
		assertArrayEquals(correctEncoding,answer,0.0);
	}
}
