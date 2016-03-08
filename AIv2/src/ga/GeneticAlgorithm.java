package ga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import neuralnetwork.NeuralNetwork;
import neuralnetwork.TrainingData;
import neuralnetwork.XORNeuralNetwork;

import org.jblas.DoubleMatrix;

import puzzle.Move;
import encoder.EightPuzzleBinaryEncoder;

public class GeneticAlgorithm {

	private double elitePercent;
	private int numberGenesToMutate;
	private double mutationPercent;
	
	private int generations;
	private ArrayList<PopulationMember> population;
	private TrainingData[] trainingData;
	private int numElite;
	private Random r;
	private DiversityRankComparator dRComparator;
	private CombinedRankComparator cRComparator;
	private RawFitnessComparator rFComparator;
	private NeuralNetwork nn;
	
	private double[] bestFitness;
	private double[] averageFitness;
	private double[] worstFitness;
	
	public GeneticAlgorithm(ArrayList<PopulationMember> population, int generations, TrainingData[] trainingData, NeuralNetwork nn)
	{
		this.generations = generations;
		this.population = population;
		this.trainingData = trainingData;
		this.nn = nn;
		r = new Random();
		dRComparator = new DiversityRankComparator();
		cRComparator = new CombinedRankComparator();
		rFComparator = new RawFitnessComparator();
		if(population != null)
			numElite = (int) (population.size() * elitePercent);
		numberGenesToMutate = 2;
		mutationPercent = .1;
		elitePercent = .20;
	}	
	
	/* Evolve the population through mutation and crossover
	 * mutation and crossover values given are the percent
	 * of mutation and crossover to do on the population.
	 * 
	 * Returns the information about the different fitnesses
	 * Index 0: Best Fitness
	 * Index 1: Worst Fitness
	 * Index 2: Average Fitness
	 */
	public double[][] evolve(double mutationPercent, double  crossoverPercent)
	{
		bestFitness = new double[generations];
		worstFitness = new double[generations];
		averageFitness = new double[generations];
		//Calculate the number of genomes from crossover and mutation.
		//Round up the number of elite so that the population size stays the same
		int remaining = population.size() - numElite;
		int mutationCount = (int)(remaining * mutationPercent);
		int crossoverCount = (int) (remaining * crossoverPercent);
		int difference = population.size() - (numElite + mutationCount + crossoverCount);
		if(difference != 0)
			numElite += difference;
		
		ArrayList<PopulationMember> elite = new ArrayList<PopulationMember>();
		ArrayList<PopulationMember> newPop = new ArrayList<PopulationMember>();
		for(int i = 0; i<generations;i++)
		{			
			getElite(elite, i);
			doMutation(elite, newPop, mutationCount);
			doCrossover(elite, newPop, crossoverCount);
			population = newPop;
			population.addAll(elite);
		}
		
		return new double[][]{bestFitness,worstFitness,averageFitness};
	}

	private void doCrossover(ArrayList<PopulationMember> elite, ArrayList<PopulationMember> newPop,int crossoverCount) {
		// TODO Auto-generated method stub
	}

	public void doMutation(ArrayList<PopulationMember> elite,ArrayList<PopulationMember> newPop,int mutationCount) {
		
		for(int i = 0; i<mutationCount;i++)
		{
			int eliteToMutate = r.nextInt(elite.size());
			PopulationMember newMem = new PopulationMember(elite.get(eliteToMutate));
			//Set mutatedGenome
			for(int j = 0; j<numberGenesToMutate;j++)
			{
				int geneToMutate = r.nextInt(newMem.getGenome().length);
				double mutationAmount = newMem.getGenome()[geneToMutate] * mutationPercent;
				if(r.nextBoolean())
					mutationAmount *= -1.0;
				mutationAmount = newMem.getGenome()[geneToMutate] + mutationAmount;
				newMem.setGenome(geneToMutate, mutationAmount);				
			}
			newPop.add(newMem);
		}
	}

	public void getElite(ArrayList<PopulationMember> elite, int generationNum) {
		//Set Raw Fitness
		double lowestFitness = Double.MAX_VALUE;
		double highestFitness = Double.MIN_VALUE;
		double avgFitness = 0.0;
		for(int i = 0; i<population.size();i++)
		{
			nn.decode(population.get(i).getGenome());
			double rawFitness = 0.0;
			if(nn instanceof XORNeuralNetwork)
				rawFitness = calcFitnessXOR();
			else
				rawFitness = calcFitnessEightPuzzle();
			
			if(rawFitness < lowestFitness)
				lowestFitness = rawFitness;
			if(rawFitness > highestFitness)
				highestFitness = rawFitness;
			avgFitness += rawFitness;
			
			population.get(i).setRawFitness(rawFitness);
		}
		averageFitness[generationNum] = avgFitness/population.size();
		bestFitness[generationNum] = lowestFitness;
		worstFitness[generationNum] = highestFitness;
		//Sort by raw fitness
		Collections.sort(population, rFComparator);
		
		//Set Fitness Rank, used in calculating combined rank
		for(int i = 0; i<population.size();i++)
		{
			population.get(i).setFitnessRank(i);
		}
		//Calc PSFR for each pop member
		calcPSFR();
		//Chose first elite based of PSFR pick num from 0.0-1.0
		elite.add(chooseFirstElite(r.nextDouble()));
		DoubleMatrix firstElite = new DoubleMatrix(elite.get(0).getGenome());
		//Calculate PSFR after removing the first elite
		calcPSFR();
		
		//Choose remaining elites
		for(int j = 0; j<numElite-1;j++)
		{
			//Calc Diversity value - vector distance between first and current
			for(int k = 1; k<population.size();k++)
			{
				DoubleMatrix current = new DoubleMatrix(population.get(k).getGenome());
				population.get(k).setDiversityRank(firstElite.distance2(current));
			}
			Collections.sort(population, new DiversityRankComparator());
			//Calc Combined Rank
			for(int k = 1; k<population.size();k++)
			{
				population.get(k).setCombinedRank(population.get(k).getDiversityRank() + population.get(k).getFitnessRank());
			}
			Collections.sort(population, new CombinedRankComparator());
			//Calc PSCR
			calcPSCR();
			elite.add(chooseElite(r.nextDouble()));
		}
	}
	
	/* Uses the max function to calculate the fitness of the genome.
	 * Takes the highest value at a node as the decision the NN wants to make.
	 * Max at output node 0 is UP, 1 is DOWN, 2 is LEFT, 3 is RIGHT
	 */
	public double calcFitnessEightPuzzle() {
		int badMoves = 0;
		for(int i = 0; i<trainingData.length;i++)
		{
			double[] output = nn.feedForward(trainingData[i].getInput());
			Move actualMove = EightPuzzleBinaryEncoder.decodeMove(trainingData[i].getOutput());
			double max = Double.MIN_VALUE;
			int index = 0;
			for(int j = 0; j<output.length;j++)
			{
				if(output[j] > max)
				{
					max = output[j];
					index = j;
				}
			}
			if(actualMove == Move.UP && index != 0)
				badMoves++;
			else if(actualMove == Move.DOWN && index != 1)
				badMoves++;
			else if(actualMove == Move.LEFT && index != 2)
				badMoves++;
			else if(actualMove == Move.RIGHT && index != 3)
				badMoves++;
		}
		return (double)badMoves/trainingData.length;
	}

	/* Calculates the fitness value for an XOR nn using
	 * Mean Squared Error
	 */
	private double calcFitnessXOR() {
		double error = 0.0;
		for(int i = 0; i<trainingData.length;i++)
		{
			DoubleMatrix actual = new DoubleMatrix(nn.feedForward(trainingData[i].getInput()));
			DoubleMatrix expected = new DoubleMatrix(trainingData[i].getOutput());
			error += expected.distance2(actual);
		}
		return error/trainingData.length;
	}

	public PopulationMember chooseElite(double selection)
	{
		double sum = 0.0;
		for(int i = 0; i<population.size();i++)
		{
			if(selection <= population.get(i).getPSFR() + sum)
				return population.remove(i);
			sum +=population.get(i).getPSCR();
		}
		return population.remove(population.size()-1);
	}
	
	public PopulationMember chooseFirstElite(double selection) {
		double sum = 0.0;
		for(int i = 0; i<population.size();i++)
		{
			if(selection <= population.get(i).getPSFR() + sum)
				return population.remove(i);
			sum +=population.remove(i).getPSFR();
		}
		return population.remove(population.size() - 1);
	}
	public void calcPSCR()
	{
		for(int j = 0; j<population.size();j++)
		{
			double sum = 0.0;
			for(int k = 0; k<j;k++)
			{
				sum+=population.get(k).getPSCR();
			}
			population.get(j).setPSCR(0.667 * (1-sum));
		}
	}
	public void calcPSFR() {
		for(int j = 0; j<population.size();j++)
		{
			double sum = 0.0;
			for(int k = 0; k<j;k++)
			{
				sum+=population.get(k).getPSFR();
			}
			population.get(j).setPSFR(0.667 * (1-sum));
		}
	}

	public ArrayList<PopulationMember> getPopulation()
	{
		return this.population;
	}
	
	public double getElitePercent() {
		return elitePercent;
	}

	public void setElitePercent(double elitePercent) {
		this.elitePercent = elitePercent;
	}

	public int getNumberGenesToMutate() {
		return numberGenesToMutate;
	}

	public void setNumberGenesToMutate(int numberGenesToMutate) {
		this.numberGenesToMutate = numberGenesToMutate;
	}

	public double getMutationPercent() {
		return mutationPercent;
	}

	public void setMutationPercent(double mutationPercent) {
		this.mutationPercent = mutationPercent;
	}

	public static void main(String[] args)
	{
		XORNeuralNetwork nn = new XORNeuralNetwork();
		double[] in = {-1.0,1.0};
		double[] out = {1.0};
		System.out.println("Feed Forward Before: " + nn.feedForward(in)[0]);
		TrainingData[] td = {new TrainingData(in, out)};
		
		ArrayList<PopulationMember> population = new ArrayList<PopulationMember>();
		for(int i = 0; i < 100;i++)
		{
			nn.init();
			population.add(new PopulationMember(nn.encode()));
		}
		GeneticAlgorithm ga = new GeneticAlgorithm(population, 50, td,nn);
		ga.evolve(1.0, 0);
		ArrayList<PopulationMember> bestPop = ga.getPopulation();
		Collections.sort(bestPop, new RawFitnessComparator());
		System.out.println("***********************************");
		System.out.println("Lowest Fitness Value: " + bestPop.get(0).getRawFitness());
		nn.decode(bestPop.get(0).getGenome());
		System.out.println("Feed Forward On Best NN: " + nn.feedForward(in)[0]);
		System.out.println("Expected: " + out[0]);
		System.exit(0);
	}
}
