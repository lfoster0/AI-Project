package neuralnetwork;

import ga.GAInterface;

public abstract class NeuralNetwork implements GAInterface{
	
	public abstract double[] feedForward(double[] input);
	public abstract void init();
}
