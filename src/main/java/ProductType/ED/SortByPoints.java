package ProductType.ED;

import java.util.Comparator;

public class SortByPoints implements Comparator<ElectronicDevice> {
    @Override
    public int compare(ElectronicDevice o1, ElectronicDevice o2) {
        return Double.compare(o2.getPoints(), o1.getPoints());
    }
}
