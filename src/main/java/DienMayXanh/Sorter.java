package DienMayXanh;

import ProductType.ED.*;

import java.util.ArrayList;
import java.util.Collections;

public class Sorter {
    public static ArrayList<ElectronicDevice> electronicDevices;
    public static void sortByCommand(String command) throws IllegalArgumentException {
        if (command.equals("SortByName")) electronicDevices.sort(new SortByName());
        else if (command.equals("SortByPrice")) electronicDevices.sort(new SortByPrice());
        else if (command.equals("SortByStar")) electronicDevices.sort(new SortByStar());
        else if (command.equals("SortByPoints")) electronicDevices.sort(new SortByPoints());
        else if (command.equals("SortByTotalRating")) electronicDevices.sort(new SortByTotalRating());
        else {
            throw new IllegalArgumentException("Invalid command");
        }
        System.out.println("Electronic devices list after sorting:");
        int counter = 0;
        for (ElectronicDevice ed : electronicDevices) {
            counter++;
            System.out.println("ProductID: " + counter);
            System.out.println(ed);
            System.out.println("-------------------------------");
        }
    }
}
