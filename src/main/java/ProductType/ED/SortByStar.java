package ProductType.ED;

import java.util.Comparator;

public class SortByStar implements Comparator<ElectronicDevice> {
    @Override
    //Sort by stars in descending order
    public int compare(ElectronicDevice ed1, ElectronicDevice ed2) {
        return Double.compare(ed2.getStars(), ed1.getStars());
    }
}
