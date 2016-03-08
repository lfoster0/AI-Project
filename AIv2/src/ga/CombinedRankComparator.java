package ga;

import java.util.Comparator;

public class CombinedRankComparator implements Comparator<PopulationMember>
{
	@Override
	public int compare(PopulationMember pm1, PopulationMember pm2) {
		return Double.compare(pm1.getCombinedRank(), pm2.getCombinedRank());
	}
}