package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

import neuralnetwork.XORNeuralNetwork;

public class XORNeuralNetworkTest {
	
	@Test
	public void testNetwork()
	{
		XORNeuralNetwork network = new XORNeuralNetwork();
		network.setLearningRate(.01);
		double[] expected0 = {-1.0};
		double[] expected1 = {1.0};

		double[] in0 = {-1.0, -1.0};
		double[] in1 = {1.0, 1.0};
		double[] in2 = {-1.0, 1.0};
		double[] in3 = {1.0, -1.0};

		for(int i = 0; i< 1; i++)
		{
			//0 0
			double[] recieved = network.feedForward(in0);
			System.out.println("0 0 " + recieved[0]);
			//network.stochasticBackPropagation(expected0);
			
			//1 1
			recieved = network.feedForward(in1);
			System.out.println("1 1 " + recieved[0]);
			//network.stochasticBackPropagation(expected0);
			
			//0 1
			recieved = network.feedForward(in2);
			System.out.println("0 1 " + recieved[0]);
			//network.stochasticBackPropagation(expected1);
			
			//1 0
			recieved = network.feedForward(in3);
			System.out.println("1 0 " + recieved[0]);
			//network.stochasticBackPropagation(expected1);
		}
	}
	
	@Test
	public void testEncode()
	{
		double[][] bias = {{7.0,3.0},{14.7}};
		double[][] in = {{3.2,4.1},{3.9,16.8}};
		double[][] hidden = {{2.0},{7.2}};
		double[][][] newEdges = {in,hidden};
		double[] expected = {3.2,4.1,3.9,16.8,2.0,7.2,7.0,3.0,14.7};
		XORNeuralNetwork nn = new XORNeuralNetwork();
		nn.setEdges(newEdges);
		nn.setBiasEdges(bias);
		double[] encoded = nn.encode();
		assertEquals("Bad Encoding Size: " + encoded.length, true,encoded.length == 9);
		assertEquals("Encoded does not match expected", true, Arrays.equals(expected, encoded));		
	}
	
	@Test
	public void testDecode()
	{
		double[][] bias = {{7.0,3.0},{14.7}};
		double[][] in = {{3.2,4.1},{3.9,16.8}};
		double[][] hidden = {{2.0},{7.2}};
		double[][][] expected = {in,hidden,{}};
		double[] newEdges = {3.2,4.1,3.9,16.8,2.0,7.2,7.0,3.0,14.7};
		XORNeuralNetwork nn = new XORNeuralNetwork();
		nn.decode(newEdges);
		double[][][]recieved = nn.getEdges();
		for(int i = 0; i <recieved.length;i++)
		{
			for(int j = 0; j<recieved[i].length;j++)
			{
				for(int k = 0; k<recieved[i][j].length;k++)
				{
					assertEquals("Error Decoding Edges", true,recieved[i][j][k] == expected[i][j][k]);
				}
			}
		}
	}
	@Test
	public void testEquals()
	{
		XORNeuralNetwork nn = new XORNeuralNetwork();
		assertEquals("Problem With Equals", true, nn.equals(nn));
		
	}
	@Test
	public void testSetEdges()
	{
		double[][] a1 = {{1,2,3},{5,6}};
		double[][] a2 = {{7,8,9},{10,11,12,13}};
		double[][][] newEdges = {a1,a2};
		XORNeuralNetwork nn = new XORNeuralNetwork();
		nn.setEdges(newEdges);
		double[][][] compareTo = nn.getEdges();
		for(int i = 0; i <compareTo.length;i++)
		{
			for(int j = 0; j<compareTo[i].length;j++)
			{
				for(int k = 0; k<compareTo[i][j].length;k++)
				{
					assertEquals("Error Setting Edges", true,newEdges[i][j][k] == compareTo[i][j][k]);
				}
			}
		}
		
	}
	
}

