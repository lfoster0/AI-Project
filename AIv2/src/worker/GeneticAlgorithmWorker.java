package worker;

import ga.GeneticAlgorithm;
import ga.PopulationMember;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.SwingWorker;

import main.Main;
import main.SharedMem;
import neuralnetwork.EightPuzzleNeuralNetwork;
import neuralnetwork.EightPuzzleTrainingData;
import neuralnetwork.NeuralNetwork;
import neuralnetwork.TrainingData;
import neuralnetwork.XORNeuralNetwork;

public class GeneticAlgorithmWorker extends SwingWorker<Void, Void>{

	@Override
	protected Void doInBackground() throws Exception {
		Main.window.btnTrain.setEnabled(false);	
		int populationSize = Integer.parseInt(Main.window.PopulationTextField.getText());
		int generations = Integer.parseInt(Main.window.generationsTextField.getText());
		double mutationPercent = 1.0;
		double percentGenesToMutate = ((double)Main.window.PercentToMutate.getValue() / 100.0);
		double percentToMutateBy = ((double)Main.window.PercentToMutateBy.getValue() / 100.0);
		double elitePercent = ((double)Main.window.PercentEliteSlider.getValue() / 100.0);
		
		if(SharedMem.getTrainingData().size() == 0 && Main.window.EightPuzzleRadioButton.isSelected())
		{
			Main.window.output("No Training Data Loaded");
			return null;
		}
		Main.window.output("Starting Experiment");
		NeuralNetwork nn = null;
		if(Main.window.XORRadioButton.isSelected())
			nn = new XORNeuralNetwork();
		else
			nn = new EightPuzzleNeuralNetwork(10);
		
		ArrayList<PopulationMember> pop = new ArrayList<PopulationMember>();
		for(int i = 0; i<populationSize;i++)
		{
			nn.init();
			pop.add(new PopulationMember(nn.encode()));
		}
		TrainingData[] td = null;
		//If XOR then initialize the training data to have the 4 XOR training data
		if(Main.window.XORRadioButton.isSelected())
		{
			td = new TrainingData[4];
			td[0] = new TrainingData(new double[]{-1,-1} , new double[]{-1});
			td[1] = new TrainingData(new double[]{-1,1} , new double[]{1});
			td[2] = new TrainingData(new double[]{1,-1} , new double[]{1});
			td[3] = new TrainingData(new double[]{1,1} , new double[]{-1});
		}
		else
		{
			td = getTrainingData();
		}
		
		GeneticAlgorithm ga = new GeneticAlgorithm(pop,generations,td,nn);
		ga.setElitePercent(elitePercent);
		ga.setMutationPercent(percentToMutateBy);
		if(Main.window.XORRadioButton.isSelected())
			ga.setNumberGenesToMutate((int)(9 * percentGenesToMutate));
		else
			ga.setNumberGenesToMutate((int)(36 * percentGenesToMutate));
		
		double[][] data = ga.evolve(1.0, 0.0);
		try{
			saveData(data);
			Main.window.output("File Saved Successfully");
		}
		catch(Exception e)
		{
			Main.window.output("Error Saving File");
		}
		return null;
	}
	
	private void saveData(double[][] data) throws Exception {
		final JFileChooser fc = new JFileChooser();
		fc.showSaveDialog(null);
		File f = fc.getSelectedFile();
		if(f != null)
		{
			FileWriter fw = new FileWriter(f);
			BufferedWriter bw = new BufferedWriter(fw);
			for(int i = 0; i<data[0].length;i++)
			{
				bw.write(data[0][i]+",");
				bw.write(data[1][i]+",");
				bw.write(""+data[2][i]);
				bw.write(System.lineSeparator());
				bw.flush();
			}
			bw.close();
		}
	}

	private TrainingData[] getTrainingData() {
		ArrayList<EightPuzzleTrainingData> eightPuzzleTD = SharedMem.getTrainingData();
		TrainingData[] td = new TrainingData[eightPuzzleTD.size()];
		for(int i = 0; i<td.length;i++)
		{
			double[] input = eightPuzzleTD.get(i).getInput();
			double[] output = eightPuzzleTD.get(i).getOutput();
			td[i] = new TrainingData(input, output);
		}
		return td;
	}

	@Override
	protected void done() {
		Main.window.btnTrain.setEnabled(true);	
		Main.window.output("Finished Genetic Algorithm Trials");
	}
}