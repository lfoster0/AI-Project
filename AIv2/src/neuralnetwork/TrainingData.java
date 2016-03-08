package neuralnetwork;

public class TrainingData {
	private double[] input;
	private double[] output;
	
	public TrainingData(double[] input, double[] output)
	{
		this.input = input;
		this.output = output;
	}

	public double[] getInput() {
		return input;
	}

	public double[] getOutput() {
		return output;
	}
	
}
