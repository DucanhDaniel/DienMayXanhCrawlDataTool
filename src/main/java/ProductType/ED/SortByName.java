package ProductType.ED;

import java.util.Comparator;

public class SortByName implements Comparator<ElectronicDevice> {
    @Override
    public int compare(ElectronicDevice ed1, ElectronicDevice ed2) {
        return ed1.getProductName().compareTo(ed2.getProductName());
    }
}
