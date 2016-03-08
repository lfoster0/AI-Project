package ga;

import java.util.Comparator;

import org.apache.commons.lang3.builder.EqualsBuilder;

import puzzle.EightPuzzle;

public class PopulationMember{
	private double rawFitness;
	private double diversityVal;
	private double PSFR;
	private double PSCR;
	private double diversityRank;
	private double[] genome;
	private double combinedRank;
	private int fitnessRank;
	
	public PopulationMember()
	{
		
	}
	
	public PopulationMember(PopulationMember copyMe)
	{
		this.genome = new double[copyMe.getGenome().length];
		for(int i = 0; i<genome.length;i++)
		{
			genome[i] = copyMe.getGenome()[i];
		}
	}
	
	public PopulationMember(double[] genome) {
		this.genome = genome;
	}
	
	public double getRawFitness() {
		return rawFitness;
	}

	public void setRawFitness(double rawFitness) {
		this.rawFitness = rawFitness;
	}

	public double getDiversityVal() {
		return diversityVal;
	}

	public void setDiversityVal(double diversityVal) {
		this.diversityVal = diversityVal;
	}

	public double getPSFR() {
		return PSFR;
	}

	public void setPSFR(double PSFR) {
		this.PSFR = PSFR;
	}

	public double getDiversityRank() {
		return diversityRank;
	}

	public void setDiversityRank(double diversityRank) {
		this.diversityRank = diversityRank;
	}	
	
	public double[] getGenome()
	{
		return genome;
	}
	
	
	public double getCombinedRank() {
		return combinedRank;
	}

	public void setCombinedRank(double combinedRank) {
		this.combinedRank = combinedRank;
	}

	public void setGenome(int index, double value)
	{
		this.genome[index] = value;
	}
	
	public void setGenome(double[] genome) {
		this.genome = genome;
	}

	public int getFitnessRank() {
		return fitnessRank;
	}

	public void setFitnessRank(int fitnessRank) {
		this.fitnessRank = fitnessRank;
	}

	public double getPSCR() {
		return PSCR;
	}

	public void setPSCR(double pSCR) {
		PSCR = pSCR;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(obj == this)
			return true;
		if(!(obj instanceof PopulationMember)){
			return false;
		}
		PopulationMember compareTo = (PopulationMember) obj;
		return new EqualsBuilder().append(this.diversityRank, compareTo.diversityRank)
								  .append(this.diversityVal, compareTo.diversityVal)
								  .append(this.genome, compareTo.genome)
								  .append(this.PSFR, compareTo.PSFR)
								  .append(this.rawFitness, compareTo.rawFitness).isEquals();
	}
}


