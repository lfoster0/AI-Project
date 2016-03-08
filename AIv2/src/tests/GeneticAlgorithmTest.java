package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import ga.CombinedRankComparator;
import ga.GeneticAlgorithm;
import ga.PopulationMember;
import ga.RawFitnessComparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import neuralnetwork.TrainingData;
import neuralnetwork.XORNeuralNetwork;

import org.jblas.DoubleMatrix;
import org.junit.Test;

public class GeneticAlgorithmTest {
	
	@Test
	public void testSetPSFR()
	{
		double[] fitness = {3.2, 34.34, 39, 12, 5.22222, 1.0, -1.0, 2, 4.3, 9.8};
		ArrayList<PopulationMember> pop = new ArrayList<PopulationMember>();
		for(int i = 0; i<fitness.length;i++)
		{
			pop.add(new PopulationMember());
		}
		for(int i = 0; i<fitness.length;i++)
		{
			pop.get(i).setRawFitness(fitness[i]);
		}
		Collections.sort(pop, new RawFitnessComparator());
		GeneticAlgorithm ga = new GeneticAlgorithm(pop,1,null,null);
		ga.calcPSFR();
		double sum = 0.0;
		for(int i = 0; i<pop.size();i++)
		{
			sum+=pop.get(i).getPSFR();
			if(i > 0)
				assertEquals("Calculate of PSFR wrong previous genome has lower PSFR", true, pop.get(i-1).getPSFR() > pop.get(i).getPSFR());
		}
		assertEquals("Does not sum to almost 1.0", true, sum >= .95);
	}
	
	@Test
	public void testSetPSCR()
	{
		double[] fitness = {3.2, 34.34, 39, 12, 5.22222, 1.0, -1.0, 2, 4.3, 9.8};
		double[] diversity = {2312.3, 12, 3, 53.1234, 41.3, 34.659, 23.2, 5.3, 0.001, 23.4};
		
		ArrayList<PopulationMember> pop = new ArrayList<PopulationMember>();
		for(int i = 0; i<fitness.length;i++)
		{
			pop.add(new PopulationMember());
		}
		for(int i = 0; i<fitness.length;i++)
		{
			pop.get(i).setRawFitness(fitness[i]);
			pop.get(i).setDiversityRank(diversity[i]);
			pop.get(i).setCombinedRank(fitness[i] + diversity[i]);
		}
		GeneticAlgorithm ga = new GeneticAlgorithm(pop,1,null,null);
		Collections.sort(pop, new CombinedRankComparator());
		ga.calcPSCR();
		double sum = 0.0;
		for(int i = 0; i<pop.size();i++)
		{
			sum+=pop.get(i).getPSCR();
			if(i > 0)
				assertEquals("Calculate of PSFR wrong previous genome has lower PSFR", true, pop.get(i-1).getPSCR() > pop.get(i).getPSCR());
		}
		assertEquals("Does not sum to almost 1.0", true, sum >= .95);
	}
	
	@Test
	public void testChooseFirstElite()
	{
		double[] genome = {1.0,2.0,3.0,4.0,5.0};
		ArrayList<PopulationMember> pop =new ArrayList<PopulationMember>();
		for(int i = 0; i<5;i++)
		{
			pop.add(new PopulationMember());
			pop.get(i).setRawFitness(genome[i]);
		}
		GeneticAlgorithm ga = new GeneticAlgorithm(pop,1,null,null);
		ga.calcPSFR();
		PopulationMember firstElite = ga.chooseFirstElite(0.5);
		assertEquals("Wrong First Elite Chosen", true, firstElite.getPSFR() == 0.667);
		firstElite = ga.chooseFirstElite(.886);
		assertEquals("Wrong First Elite Chosen", true, firstElite.getPSFR() < .01);
	}
	
	@Test 
	public void testChooseElite()
	{
		double[] input = {1.0,1.0};
		double[] output = {-1.0};
		TrainingData[] td = {new TrainingData(input, output)};
		ArrayList<PopulationMember> population = new ArrayList<PopulationMember>();
		for(int i = 0; i<100; i++)
		{
			XORNeuralNetwork nn = new XORNeuralNetwork();
			population.add(new PopulationMember(nn.encode()));
		}
		ArrayList<PopulationMember> elite = new ArrayList<PopulationMember>();
		XORNeuralNetwork nn = new XORNeuralNetwork();
		GeneticAlgorithm ga = new GeneticAlgorithm(population, 1, td, nn);
		ga.getElite(elite, 0);
		assertEquals("Incorrect number of elites", true, elite.size() == 20 );
	}
	@Test
	public void testVectorDistance()
	{
		double[] genome1 = {33.2,98.123};
		double[] genome2 = {2.0,2.0};
		PopulationMember pa1 = new PopulationMember(genome1);
		PopulationMember pa2 = new PopulationMember(genome2);
		
		DoubleMatrix m1 = new DoubleMatrix(pa1.getGenome());
		DoubleMatrix m2 = new DoubleMatrix(pa2.getGenome());
		double distance = m1.distance2(m2);
	}
	
	/* Tests the logic of the XOR MSE fitness calculation.
	 * Copy and pasted the logic here instead of using the method
	 * since it would be difficult to verify the correctness since it
	 * relies on feed forward of a NN.  Compared to values calculated online.
	 */
	@Test
	public void testCalcFitnessXOR()
	{
		DoubleMatrix expected1 = new DoubleMatrix(new double[]{3,4,-1.0});
		DoubleMatrix expected2 = new DoubleMatrix(new double[]{23,-4,1.0});
		double error = 0.0;
		DoubleMatrix actual1 = new DoubleMatrix(new double[]{3.2,4.3,-1.4});
		DoubleMatrix actual2 = new DoubleMatrix(new double[]{23.2,-4.234,.333});
		error += actual1.distance2(expected1);
		error += actual2.distance2(expected2);
		error = error / 2;
		assertEquals("MSE Incorrect", true, error == 0.6365609084657384 );
		
	}
	
	//Tests the logic at the beginning of evolve to ensure the population stays the same
	@Test
	public void testPopulationRounding()
	{
		double percentElite = .2;
		double percentMut = .7;
		double percentCrossover = .3;
		int population = 543;
		int numElite = (int)(population * .2);
		
		int remaining = population - numElite;
		int mutationCount = (int)(remaining * percentMut);
		int crossoverCount = (int) (remaining * percentCrossover);
		int difference = population - (numElite + mutationCount + crossoverCount);
		if(difference != 0)
			numElite += difference;
		assertEquals("New population size does not match original", true, population == (numElite + mutationCount + crossoverCount));
		
		Random r = new Random();
		population = r.nextInt();
		numElite = (int)(population * .2);
		
		remaining = population - numElite;
		mutationCount = (int)(remaining * percentMut);
		crossoverCount = (int) (remaining * percentCrossover);
		difference = population - (numElite + mutationCount + crossoverCount);
		if(difference != 0)
			numElite += difference;
		assertEquals("New population size does not match original", true, population == (numElite + mutationCount + crossoverCount));
	}
	
	@Test
	public void testMutation()
	{
		double[] input = {1.0,-1.0};
		double[] output = {1.0};
		TrainingData[] td = {new TrainingData(input,output)};
		XORNeuralNetwork nn = new XORNeuralNetwork();
		ArrayList<PopulationMember> population = new ArrayList<PopulationMember>();
		ArrayList<PopulationMember> elite = new ArrayList<PopulationMember>();
		for(int i = 0; i<5;i++)
		{
			nn.init();
			population.add(new PopulationMember(nn.encode()));
		}
		GeneticAlgorithm ga = new GeneticAlgorithm(population,1,td,nn);
		ga.getElite(elite, 0);
		ArrayList<PopulationMember> newPop = new ArrayList<PopulationMember>();
		ga.doMutation(elite, newPop, 4);
		for(int i = 0;i<newPop.size();i++)
		{
			assertEquals("Error mutation matches original", false,Arrays.equals(newPop.get(i).getGenome(), elite.get(0).getGenome()));
		}
		
	}
}

