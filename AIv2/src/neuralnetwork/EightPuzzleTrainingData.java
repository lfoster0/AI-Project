/* Represents training tuple read in from a file.
 * Has the input values (first 36 numbers in the file) and 
 * the output values (last 4 numbers in the file)
 */
package neuralnetwork;

import java.util.Scanner;
import java.util.StringTokenizer;

public class EightPuzzleTrainingData {

	private double[] input;
	private double[] output;
	
	public EightPuzzleTrainingData(String data)
	{
		input = new double[36];
		output = new double[4];

		StringTokenizer st = new StringTokenizer(data, ",");
		for(int i = 0; i<input.length;i++)
		{
			input[i] = Double.parseDouble(st.nextToken());
		}
		
		for(int i = 0; i< output.length;i++)
		{
			output[i] = Double.parseDouble(st.nextToken());
		}
		
		
	}

	public double[] getInput() {
		return input;
	}

	public double[] getOutput() {
		return output;
	}

}
