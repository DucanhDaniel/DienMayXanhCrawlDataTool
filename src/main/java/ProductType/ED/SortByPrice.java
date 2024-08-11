package ProductType.ED;

import java.util.Comparator;

public class SortByPrice implements Comparator<ElectronicDevice> {
    @Override
    public int compare(ElectronicDevice o1, ElectronicDevice o2) {
        double price1 = Double.parseDouble(o1.getPrice().replaceAll("[^0-9]", ""));
        double price2 = Double.parseDouble(o2.getPrice().replaceAll("[^0-9]", ""));
        return Double.compare(price1, price2);
    }
}
