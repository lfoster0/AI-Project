package tests;

import static org.junit.Assert.assertEquals;
import ga.DiversityRankComparator;
import ga.PopulationMember;

import java.util.Arrays;

import org.junit.Test;

public class PopulationMemberTest {
	
	@Test
	public void testDiversityRankSort()
	{
		double[] diversityRank = {3.2, 34.34, 39, 12, 5.22222, 1.0, -1.0, 2, 4.3, 9.8};
		double[] sortedDiversity = {-1.0,1.0,2,3.2,4.3,5.22222,9.8,12,34.34,39};
		PopulationMember[] pop = new PopulationMember[10];
		for(int i = 0; i<pop.length;i++)
		{
			pop[i] = new PopulationMember();
			pop[i].setDiversityRank(diversityRank[i]);
		}
		Arrays.sort(pop, new DiversityRankComparator());
		for(int i =0;i<pop.length;i++)
		{
			assertEquals("Bad Sort", true, pop[i].getDiversityRank() == sortedDiversity[i]);
		}
	}
	
	@Test
	public void testFitnessSort()
	{
		double[] fitness = {3.2, 34.34, 39, 12, 5.22222, 1.0, -1.0, 2, 4.3, 9.8};
		double[] sortedFitness = {-1.0,1.0,2,3.2,4.3,5.22222,9.8,12,34.34,39};
		PopulationMember[] pop = new PopulationMember[10];
		for(int i = 0; i<pop.length;i++)
		{
			pop[i] = new PopulationMember();
			pop[i].setRawFitness(fitness[i]);
		}
		Arrays.sort(pop);
		for(int i =0;i<pop.length;i++)
		{
			assertEquals("Bad Sort", true, pop[i].getRawFitness() == sortedFitness[i]);
		}
	}
	
	@Test
	public void testEquals()
	{
		double[] genome1 = {3.2, 34.34, 39, 12, 5.22222, 1.0, -1.0, 2, 4.3, 9.8};
		double[] genome2 = {3.2, 34.34, 39, 12, 5.22222, 1.0, -1.0, 2, 4.3};
		PopulationMember m1 = new PopulationMember(genome1);
		m1.setDiversityRank(3.21);
		m1.setPSFR(43.222222);
		m1.setDiversityVal(1.0);
		m1.setRawFitness(3.222);
		PopulationMember m2 = new PopulationMember(genome1);
		m2.setDiversityRank(3.21);
		m2.setPSFR(43.222222);
		m2.setDiversityVal(1.0);
		m2.setRawFitness(3.222);
		PopulationMember m3 = new PopulationMember(genome2);
		m3.setDiversityRank(3.21);
		m3.setPSFR(43.222222);
		m3.setDiversityVal(1.0);
		m3.setRawFitness(3.222);
		assertEquals("Does Not Equal Self", true, m1.equals(m1));
		assertEquals("Does Not Equal Equivalent Member", true, m1.equals(m2));
		assertEquals("Equals non matching genome", false, m1.equals(m3));
	}
	
}

