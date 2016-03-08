/* XOR Neural Network
 * Used to ensure changes to the network work properlly
 */
package neuralnetwork;

import ga.GAInterface;

import java.util.HashMap;
import java.util.Random;

import org.apache.commons.lang3.builder.EqualsBuilder;

import puzzle.EightPuzzle;

public class EightPuzzleNeuralNetwork extends NeuralNetwork implements GAInterface{
	private static final double A = 1.716;
	private static final double B = 0.667;
	
	private int inputSize = 2;
	private int outputSize = 1;
	private int hiddenSize = 2;
	
	private static final int numLayers = 3;
	private static final int inputLayer = 0;
	private static final int hiddenLayer = 1;
	private static final int outputLayer = 2;
	
	private static final double biasValue = 1.0;
	
	private double learningRate;
	
	//nodes[layer][rightside][leftside]
	private double[][][] edges;
	//bias[layer][node];
	private double [][] biasEdges;
	
	private Random r;
	
	public EightPuzzleNeuralNetwork(int hiddenSize)
	{
		this.inputSize = 36;
		this.hiddenSize = hiddenSize;
		this.outputSize = 4;
		learningRate = 0.1;
		
		r = new Random();
		edges = new double[numLayers-1][][];
		biasEdges = new double[2][];
		//Initialize node array
		edges[inputLayer] = new double[inputSize][hiddenSize];
		edges[hiddenLayer] = new double[hiddenSize][outputSize];
		
		//Initialize bias array
		biasEdges[0] = new double[hiddenSize];
		biasEdges[1] = new double[outputSize];
		
		this.init();		
	}

	//Resets all the edges
	public void init()
	{
		//Initialize Input to Hidden Edges
		for(int inputNode = 0;inputNode<edges[inputLayer].length;inputNode++)
		{
			for(int hiddenNode = 0;hiddenNode<edges[hiddenLayer].length;hiddenNode++)
			{
				double val = r.nextDouble();
				if(r.nextBoolean())
					val = -val;
				edges[inputLayer][inputNode][hiddenNode] = val;
			}
		}
		
		//Initialize Hidden to Output Edges
		for(int hiddenNode = 0;hiddenNode<edges[hiddenLayer].length;hiddenNode++)
		{
			for(int outputNode = 0;outputNode<outputSize;outputNode++)
			{
				double val = r.nextDouble();
				if(r.nextBoolean())
					val = -val;
				edges[hiddenLayer][hiddenNode][outputNode] = val;
			}
		}
		
		//Initialize Bias to Hidden Edges
		for(int hiddenNode = 0; hiddenNode<biasEdges[0].length;hiddenNode++)
		{
			double val = r.nextDouble();
			if(r.nextBoolean())
				val = -val;
			biasEdges[0][hiddenNode] = val;
		}
		
		//Initialize Bias to Output Edges
		for(int outputNode = 0; outputNode<outputSize;outputNode++)
		{
			double val = r.nextDouble();
			if(r.nextBoolean())
				val = -val;
			biasEdges[1][outputNode] = val;
		}

	}
	
	//The values at a node are just a calculation of their net through the transfer function.
	public double[] feedForward(double[] input)
	{
		double[] hiddenValues = new double[hiddenSize];
		double[] outputValues = new double[outputSize];
		
		//Calculate the value of each hidden node
		for(int i = 0; i<edges[hiddenLayer].length;i++)
		{
			double netVal = calcNetHidden(i, input);
			hiddenValues[i] = transferFunction(netVal);
		}
		
		//Set each output neuron to its net through the transfer function
		for(int i = 0; i<outputSize;i++)
		{
			double netVal = calcNetOutput(i, hiddenValues);
			outputValues[i] = transferFunction(netVal);
		}
		
		return outputValues;
	}
	
	//Calculates the net value of the given hidden node
	private double calcNetHidden(int hiddenNode, double[] inputVals)
	{
		double val = biasEdges[0][hiddenNode];
		for(int i = 0; i<inputVals.length;i++)
		{
			val += inputVals[i] * edges[inputLayer][i][hiddenNode];
		}
		return val;
	}
	
	//Calculates the net value of an output node n
	private double calcNetOutput(int outputNode, double[] hiddenVals)
	{
		double val = biasEdges[1][outputNode];
		for(int i = 0; i<hiddenVals.length;i++)
		{
			val += hiddenVals[i] * edges[hiddenLayer][i][outputNode];
		}
		return val;
	}
	
	public void stochasticBackPropagation(double[] input, double[] expected)
	{
		double[] actual  = feedForward(input);
		double[] hiddenValues = new double[hiddenSize];
		double sigmaK[] = new double[outputSize];
		double sigmaJ[] = new double[hiddenSize];
		double[][] deltaOutputHidden = new double[outputSize][hiddenSize];
		double[] deltaBiasOutput = new double[outputSize];
		double[][] deltaHiddenInput = new double[hiddenSize][outputSize];
		double[] deltaBiasHidden = new double[hiddenSize];
		
		//Calculate the value of each hidden node
		for(int i = 0; i<edges[hiddenLayer].length;i++)
		{
			double netVal = calcNetHidden(i, input);
			hiddenValues[i] = transferFunction(netVal);
		}
		
		//Calc sigmaK's
		for(int i = 0; i<sigmaK.length;i++)
		{
			sigmaK[i] = (actual[i] - expected[i]) * transferFunctionPrime(calcNetOutput(i, hiddenValues));
		}	
		//calc edge weight deltas between output and hidden
		for(int i = 0; i<outputSize;i++)
		{
			for(int j = 0; j<hiddenSize;j++)
			{
				deltaOutputHidden[i][j] = learningRate * sigmaK[i] * transferFunction(calcNetHidden(j, input));
			}
		}
		//calc edge weight deltas between output and bias
		for(int i = 0; i<outputSize;i++)
		{
			deltaBiasOutput[i] = learningRate * sigmaK[i] * biasEdges[1][i];
		}
		//calc sigmaJ
		for(int i = 0; i<hiddenSize;i++)
		{
			for(int j = 0; j<outputSize;j++)
			{
				
			}
		}
		//calc edge weight deltas between hidden and input
		//calc edge weight deltas between hidden and bias
		//apply updates
		//do mse or other error function
	}
	
	public double transferFunction(double d)
	{
		return A * Math.tanh(d * B);
	}
	
	//Derivative of the transferFunction
	public double transferFunctionPrime(double d)
	{
		return 1-Math.tanh(d)*Math.tanh(d);
	}


	public double getLearningRate() {
		return learningRate;
	}

	public void setLearningRate(double learningRate) {
		this.learningRate = learningRate;
	}

	@Override
	public double[] encode() {
		double[] encoded = new double[inputSize*hiddenSize+hiddenSize*outputSize+hiddenSize+outputSize];
		int k = 0;
		//Get input-hidden layers
		for(int i = 0; i<inputSize;i++)
		{
			for(int j = 0; j<hiddenSize;j++)
			{
				encoded[k++] = edges[inputLayer][i][j];
			}
		}
		//Get hidden-output layers
		for(int i = 0; i<hiddenSize;i++)
		{
			for(int j = 0; j<outputSize;j++)
			{
				encoded[k++] = edges[hiddenLayer][i][j];
			}
		}
		//Bias-hidden and bias-output
		for(int i = 0;i< biasEdges.length;i++)
		{
			for(int j = 0; j<biasEdges[i].length;j++)
			{
				encoded[k++] = biasEdges[i][j];
			}
		}
		return encoded;
	}

	@Override
	public void decode(double[] toDecode) {
		int k = 0;
		//Get input-hidden layers
		for(int i = 0; i<inputSize;i++)
		{
			for(int j = 0; j<hiddenSize;j++)
			{
				edges[inputLayer][i][j] = toDecode[k++];
			}
		}
		//Get hidden-output layers
		for(int i = 0; i<hiddenSize;i++)
		{
			for(int j = 0; j<outputSize;j++)
			{
				edges[hiddenLayer][i][j] = toDecode[k++];
			}
		}
		//Bias-hidden and bias-output
		for(int i = 0;i< biasEdges.length;i++)
		{
			for(int j = 0; j<biasEdges[i].length;j++)
			{
				biasEdges[i][j] = toDecode[k++];
			}
		}
	}
	
	public void setBiasEdges(double[][] newEdges)
	{
		biasEdges = new double[newEdges.length][];
		for(int i = 0; i <newEdges.length;i++)
		{
			biasEdges[i] = new double[newEdges[i].length];
			for(int j = 0; j<newEdges[i].length;j++)
			{
				biasEdges[i][j] = newEdges[i][j];
			}
		}
	}
	
	public double[][] getBiasEdges()
	{
		return biasEdges;
	}
	
	public void setEdges(double[][][] newEdges)
	{
		edges = new double[newEdges.length][][];
		for(int i = 0; i <newEdges.length;i++)
		{
			edges[i] = new double[newEdges[i].length][];
			for(int j = 0; j<newEdges[i].length;j++)
			{
				edges[i][j] = new double[newEdges[i][j].length];
				for(int k = 0; k<newEdges[i][j].length;k++)
				{
					edges[i][j][k] = newEdges[i][j][k];
				}
			}
		}
	}
	
	public double[][][] getEdges()
	{
		return this.edges;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(obj == this)
			return true;
		if(!(obj instanceof XORNeuralNetwork)){
			return false;
		}
		XORNeuralNetwork compareTo = (XORNeuralNetwork) obj;
		double[][][] compareToEdges = compareTo.getEdges();
		try
		{
			for(int i = 0; i <compareToEdges.length;i++)
			{
				for(int j = 0; j<compareToEdges[i].length;j++)
				{
					for(int k = 0; k<compareToEdges[i][j].length;k++)
					{
						if(this.edges[i][j][k] != compareToEdges[i][j][k])
							return false;
					}
				}
			}
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	
	public static void main(String[] args)
	{
		Random r = new Random();
		double[][] trainingdata = new double[4][3];
		double[][] expectedOutput = new double[4][1];
		//Init Input
		trainingdata[0] = new double[]{-1.0, -1.0};
		trainingdata[1] = new double[]{1.0, -1.0};
		trainingdata[2] = new double[]{-1.0, 1.0};
		trainingdata[3] = new double[]{1.0, 1.0};
		//Init Output
		expectedOutput[0] = new double[]{-1.0};
		expectedOutput[1] = new double[]{1.0};
		expectedOutput[2] = new double[]{1.0};
		expectedOutput[3] = new double[]{-1.0};
				
		XORNeuralNetwork nn = new XORNeuralNetwork();
		for(int i = 0; i<100; i++)
		{
			int random = r.nextInt(trainingdata.length);
			double[] answer = nn.feedForward(trainingdata[random]);
			//nn.stochasticBackPropagation(expectedOutput[random]);
			System.out.println(trainingdata[random][0] + " " + 
			                   trainingdata[random][1] + " " + 
			                   expectedOutput[random][0] + " | " + answer[0]);
		} 
		double[] encoded = nn.encode();
		for(int i = 0; i<encoded.length;i++)
		{
			System.out.print(encoded[i] + " ");
		}
	}

}
