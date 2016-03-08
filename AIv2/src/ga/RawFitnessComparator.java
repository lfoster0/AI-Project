package ga;

import java.util.Comparator;

public class RawFitnessComparator implements Comparator<PopulationMember>
{
		@Override
	public int compare(PopulationMember pm1, PopulationMember pm2) {
		return Double.compare(pm1.getRawFitness(), pm2.getRawFitness());
	}
}