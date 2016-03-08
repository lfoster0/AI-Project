/* Program is started from this class
 * 
 */
package main;
/**
 * Main Class used to run program
 */
import gui.GUI;

import java.awt.EventQueue;
import java.io.File;
import java.util.Scanner;

import javax.swing.UIManager;

public class Main {
	
	private static File trainingData;
	
	private static Scanner s;
	public static GUI window;

	/*public static void main(String[] args) throws IOException
	{
		Scanner s = new Scanner(System.in);
		System.out.println("File Name:");
		File file = new File(s.nextLine());
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		
		TrainingData[] td = new TrainingData[4];
		td[0] = new TrainingData(new double[]{-1,-1} , new double[]{-1});
		td[1] = new TrainingData(new double[]{-1,1} , new double[]{1});
		td[2] = new TrainingData(new double[]{1,-1} , new double[]{1});
		td[3] = new TrainingData(new double[]{1,1} , new double[]{-1});
		

		System.out.println("Number of NN");
		int popSize = s.nextInt();
		XORNeuralNetwork nn = new XORNeuralNetwork();
		ArrayList<PopulationMember> population = new ArrayList<PopulationMember>();
		for(int i = 0; i<popSize; i++)
		{
			nn.init();
			population.add(new PopulationMember(nn.encode()));
		}
		System.out.println("Generations: ");
		int generations = s.nextInt();
		System.out.println("Genomes to Mutate 0.0-1.0: ");
		double genomesToMutate = s.nextDouble();
		System.out.println("Mutation amount 0.0-1.0: ");
		double mutationAmount = s.nextDouble();
		GeneticAlgorithm ga = new GeneticAlgorithm(population, generations,td, nn);
		ga.setNumberGenesToMutate((int)(36 * genomesToMutate));
		ga.setMutationPercent(mutationAmount);
		System.out.println("Start");
		double[][] data = ga.evolve(1.0, 0.0);
		System.out.println("End");
		for(int i = 0; i<data[0].length;i++)
		{
			System.out.println("**********");
			System.out.println("Best Fitness: " + data[0][i]);
			System.out.println("Worst Fitness: " + data[1][i]);
			System.out.println("Average Fitness: " + data[2][i]);
			bw.write(data[0][i]+",");
			bw.write(data[1][i]+",");
			bw.write(""+data[2][i]);
			bw.write(System.lineSeparator());
			bw.flush();
		}
		ArrayList<PopulationMember> goodPop = ga.getPopulation();
		Collections.sort(goodPop, new RawFitnessComparator());
		nn.decode(goodPop.get(0).getGenome());
		
		double[] output = nn.feedForward(td[0].getInput());
		System.out.println("(-1 -1 | -1) = " + output[0]);
		
		output = nn.feedForward(td[1].getInput());
		System.out.println("(-1 1 | 1) = " + output[0]);
		
		output = nn.feedForward(td[1].getInput());
		System.out.println("(1 -1 | 1) = " + output[0]);
		
		output = nn.feedForward(td[1].getInput());
		System.out.println("(1 1 | -1) = " + output[0]);
	}
	
	public static void main(String[] args) throws IOException
	{
		Scanner s = new Scanner(System.in);
		System.out.println("File Name:");
		File file = new File(s.nextLine());
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		SearchListNode<Move> goalState = new SearchListNode<Move>(null, new EightPuzzle(), 0.0, Move.START);
		EightPuzzle puzzle = new EightPuzzle();
		puzzle.shuffle();
		SearchListNode initialNode = new SearchListNode(null,puzzle,0,Move.START);
		BreadthFirstSearch bfs = new BreadthFirstSearch(initialNode,goalState);
		SearchListNode solvedNode = bfs.search();
		EightPuzzleBinaryEncoder encoder = new EightPuzzleBinaryEncoder();
		ArrayList<TrainingData> td = new ArrayList<TrainingData>();
		while(solvedNode.getParent() != null){
			td.add(new TrainingData(encoder.encodePuzzle((EightPuzzle) solvedNode.getParent().getState()),
					encoder.encodeMove((Move)solvedNode.getMove())));
			solvedNode = solvedNode.getParent();
		}
		TrainingData[] td2 = new TrainingData[td.size()];
		for(int i = 0; i<td2.length;i++)
		{
			td2[i] = td.get(i);
		}
		System.out.println("Number of NN");
		int popSize = s.nextInt();
		EightPuzzleNeuralNetwork nn = new EightPuzzleNeuralNetwork(10);
		ArrayList<PopulationMember> population = new ArrayList<PopulationMember>();
		for(int i = 0; i<popSize; i++)
		{
			nn.init();
			population.add(new PopulationMember(nn.encode()));
		}
		System.out.println("Generations: ");
		int generations = s.nextInt();
		System.out.println("Genomes to Mutate 0.0-1.0: ");
		double genomesToMutate = s.nextDouble();
		System.out.println("Mutation amount 0.0-1.0: ");
		double mutationAmount = s.nextDouble();
		GeneticAlgorithm ga = new GeneticAlgorithm(population, generations,td2, nn);
		ga.setNumberGenesToMutate((int)(36 * genomesToMutate));
		ga.setMutationPercent(mutationAmount);
		System.out.println("Start");
		double[][] data = ga.evolve(1.0, 0.0);
		System.out.println("End");
		for(int i = 0; i<data[0].length;i++)
		{
			System.out.println("**********");
			System.out.println("Best Fitness: " + data[0][i]);
			System.out.println("Worst Fitness: " + data[1][i]);
			System.out.println("Average Fitness: " + data[2][i]);
			bw.write(data[0][i]+",");
			bw.write(data[1][i]+",");
			bw.write(""+data[2][i]);
			bw.write(System.lineSeparator());
			bw.flush();
		}
	} */
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
			        UIManager.setLookAndFeel(
			                UIManager.getSystemLookAndFeelClassName());
					window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}); 
	} 
	
}
  