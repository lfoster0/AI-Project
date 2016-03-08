package ga;

import java.util.Comparator;

public class DiversityRankComparator implements Comparator<PopulationMember>
{
		@Override
	public int compare(PopulationMember pm1, PopulationMember pm2) {
		return Double.compare(pm1.getDiversityRank(), pm2.getDiversityRank());
	}
}