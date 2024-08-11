package ProductType.ED;

import java.util.Comparator;

public class SortByTotalRating implements Comparator<ElectronicDevice> {
    @Override
    public int compare(ElectronicDevice o1, ElectronicDevice o2) {
        return Double.compare(o2.getTotalRating(), o1.getTotalRating());
    }
}
