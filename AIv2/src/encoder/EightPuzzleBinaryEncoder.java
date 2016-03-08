/**
 * Encodes a given 8puzzle and move to binary form with -1 instead of 0
 */
package encoder;

import java.util.ArrayList;

import org.apache.commons.lang3.ArrayUtils;

import puzzle.EightPuzzle;
import puzzle.Move;

public class EightPuzzleBinaryEncoder {

	//Takes a given Eight puzzle and move and converts them into an array of Doubles
	//to be used for training data.  The move is encoded after the puzzle so the first
	//36 numbers are the puzzle, the last 4 the move
	public double[] encode(EightPuzzle puzzle, Move move){
		int[] puzzleArray = puzzle.getPuzzle();
		ArrayList<Double> encodedValues = new ArrayList<Double>();
		
		for(int i = 0; i<puzzleArray.length;i++){
			switch(puzzleArray[i]){
			case EightPuzzle.EMPTY_SPACE:
				encodedValues.add(-1.0);
				encodedValues.add(-1.0);
				encodedValues.add(-1.0);
				encodedValues.add(-1.0);
				break;
			case 1:
				encodedValues.add(-1.0);
				encodedValues.add(-1.0);
				encodedValues.add(-1.0);
				encodedValues.add(1.0);
				break;
			
			case 2:
				encodedValues.add(-1.0);
				encodedValues.add(-1.0);
				encodedValues.add(1.0);
				encodedValues.add(-1.0);
				break;
			case 3:
				encodedValues.add(-1.0);
				encodedValues.add(-1.0);
				encodedValues.add(1.0);
				encodedValues.add(1.0);
				break;
			case 4:
				encodedValues.add(-1.0);
				encodedValues.add(1.0);
				encodedValues.add(-1.0);
				encodedValues.add(-1.0);
				break;
			case 5:
				encodedValues.add(-1.0);
				encodedValues.add(1.0);
				encodedValues.add(-1.0);
				encodedValues.add(1.0);
				break;
			case 6:
				encodedValues.add(-1.0);
				encodedValues.add(1.0);
				encodedValues.add(1.0);
				encodedValues.add(-1.0);
				break;
			case 7:
				encodedValues.add(-1.0);
				encodedValues.add(1.0);
				encodedValues.add(1.0);
				encodedValues.add(1.0);
				break;
			case 8:
				encodedValues.add(1.0);
				encodedValues.add(-1.0);
				encodedValues.add(-1.0);
				encodedValues.add(-1.0);
				break;
			default:
				encodedValues.add(0.0);
				encodedValues.add(0.0);
				encodedValues.add(0.0);
				encodedValues.add(0.0);
				break;
			}	
		}
		
		switch(move){
		case UP: 
			encodedValues.add(-1.0);
			encodedValues.add(-1.0);
			encodedValues.add(-1.0);
			encodedValues.add(1.0);
			break;
		case DOWN:
			encodedValues.add(-1.0);
			encodedValues.add(-1.0);
			encodedValues.add(1.0);
			encodedValues.add(-1.0);
			break;
		case LEFT:
			encodedValues.add(-1.0);
			encodedValues.add(1.0);
			encodedValues.add(-1.0);
			encodedValues.add(-1.0);
			break;
		case RIGHT:
			encodedValues.add(1.0);
			encodedValues.add(-1.0);
			encodedValues.add(-1.0);
			encodedValues.add(-1.0);
			break;
		}
		
		return ArrayUtils.toPrimitive(encodedValues.toArray(new Double[0]));
	}

	public double[] encodeMove(Move move)
	{
		ArrayList<Double> encodedValues = new ArrayList<Double>();
		
		switch(move){
		case UP: 
			encodedValues.add(-1.0);
			encodedValues.add(-1.0);
			encodedValues.add(-1.0);
			encodedValues.add(1.0);
			break;
		case DOWN:
			encodedValues.add(-1.0);
			encodedValues.add(-1.0);
			encodedValues.add(1.0);
			encodedValues.add(-1.0);
			break;
		case LEFT:
			encodedValues.add(-1.0);
			encodedValues.add(1.0);
			encodedValues.add(-1.0);
			encodedValues.add(-1.0);
			break;
		case RIGHT:
			encodedValues.add(1.0);
			encodedValues.add(-1.0);
			encodedValues.add(-1.0);
			encodedValues.add(-1.0);
			break;
		}
		
		return ArrayUtils.toPrimitive(encodedValues.toArray(new Double[0]));
	}
	
	public double[] encodePuzzle(EightPuzzle puzzle)
	{
		int[] puzzleArray = puzzle.getPuzzle();
		ArrayList<Double> encodedValues = new ArrayList<Double>();
		
		for(int i = 0; i<puzzleArray.length;i++){
			switch(puzzleArray[i]){
			case EightPuzzle.EMPTY_SPACE:
				encodedValues.add(-1.0);
				encodedValues.add(-1.0);
				encodedValues.add(-1.0);
				encodedValues.add(-1.0);
				break;
			case 1:
				encodedValues.add(-1.0);
				encodedValues.add(-1.0);
				encodedValues.add(-1.0);
				encodedValues.add(1.0);
				break;
			
			case 2:
				encodedValues.add(-1.0);
				encodedValues.add(-1.0);
				encodedValues.add(1.0);
				encodedValues.add(-1.0);
				break;
			case 3:
				encodedValues.add(-1.0);
				encodedValues.add(-1.0);
				encodedValues.add(1.0);
				encodedValues.add(1.0);
				break;
			case 4:
				encodedValues.add(-1.0);
				encodedValues.add(1.0);
				encodedValues.add(-1.0);
				encodedValues.add(-1.0);
				break;
			case 5:
				encodedValues.add(-1.0);
				encodedValues.add(1.0);
				encodedValues.add(-1.0);
				encodedValues.add(1.0);
				break;
			case 6:
				encodedValues.add(-1.0);
				encodedValues.add(1.0);
				encodedValues.add(1.0);
				encodedValues.add(-1.0);
				break;
			case 7:
				encodedValues.add(-1.0);
				encodedValues.add(1.0);
				encodedValues.add(1.0);
				encodedValues.add(1.0);
				break;
			case 8:
				encodedValues.add(1.0);
				encodedValues.add(-1.0);
				encodedValues.add(-1.0);
				encodedValues.add(-1.0);
				break;
			default:
				encodedValues.add(0.0);
				encodedValues.add(0.0);
				encodedValues.add(0.0);
				encodedValues.add(0.0);
				break;
			}	
		}
		return ArrayUtils.toPrimitive(encodedValues.toArray(new Double[0]));
	}
	
	public static Move decodeMove(double[] move)
	{
		if(move[3] == 1.0)
			return Move.UP;
		if(move[2] == 1.0)
			return Move.DOWN;
		if(move[1] == 1.0)
			return Move.LEFT;
		if(move[0] == 1.0)
			return Move.RIGHT;
		
		return Move.START;
	}
}
