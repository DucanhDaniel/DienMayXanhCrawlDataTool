package JFX;

import ProductType.ED.BlackListWord;
import ProductType.ED.Sorter;
import ProductType.ED.ElectronicDevice;
import java.io.IOException;


import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_CYAN = "\u001B[36m";


    public static void main(String[] args) throws IOException {
        System.out.println(ANSI_GREEN + "This program will get products data from dienmayxanh.com and meta.vn and display product details. It also provides some sorting options." + ANSI_RESET);
        System.out.println(ANSI_GREEN + "Insert product name, example : may-giat." + ANSI_RESET);
        String product_name;
        Scanner scanner = new Scanner(System.in);
        product_name = scanner.nextLine();

        //Getting data from websites and add them into an ArrayList
        System.out.println(ANSI_GREEN + "Please wait while getting data..." + ANSI_RESET);
        BlackListWord.initBlackListWords();
        ArrayList<ElectronicDevice> result = new ArrayList<>();

        result.addAll(DienMayXanh.GetProductsData.get(product_name));
        result.addAll(meta.GetProductsData.get(product_name));

        if (result == null) return;
        Sorter.electronicDevices = result;
        System.out.println(ANSI_GREEN + result.size() + " products found!" + ANSI_RESET);

        while (true) {
            System.out.println("----------------------------------");
            System.out.println(ANSI_CYAN + """
                    Choose an option:\s
                     \
                    1. Display products list\s
                     \
                    2. Sorting products\s
                     \
                    3. Get product information\s
                     \
                    4. Exit""" + ANSI_RESET
            );
            System.out.print("Enter your choice (number): ");
            int choice = scanner.nextInt();
            if (choice == 1) {
                int counter = 0;
                System.out.println("----------------------------------");
                for (ElectronicDevice electronicDevice : result) {
                    counter++;
                    System.out.println("ProductID: " + counter);
                    System.out.println(electronicDevice);
                    System.out.println("----------------------------------");
                }

                while (true) {
                    System.out.println(ANSI_CYAN + "Continue? (y/n): " + ANSI_RESET);
                    char x = scanner.next().charAt(0);
                    if (x == 'y') break;
                    if (x == 'n') return;
                    System.out.println(ANSI_RED + "Invalid option!" + ANSI_RESET);
                    break;
                }
            }
            else if (choice == 2) {
                System.out.println(ANSI_CYAN + """
                        Choose a command:\s
                         \
                        SortByPoints\s
                         \
                        SortByPrice\s
                         \
                        SortByStar\s
                         \
                        SortByTotalRating""" + ANSI_RESET);
                String command = scanner.next();

                Sorter.sortByCommand(command);
                result = Sorter.electronicDevices;

                while (true) {
                    System.out.println(ANSI_CYAN + "Continue? (y/n): " + ANSI_RESET);
                    char x = scanner.next().charAt(0);
                    if (x == 'y') break;
                    if (x == 'n') return;
                    System.out.println(ANSI_RED + "Invalid option! Try again." + ANSI_RESET);
                }
            }
            else if (choice == 3) {
                while (true) {
                    System.out.println(ANSI_GREEN + "Insert ProductID. Example: 1" + ANSI_RESET);
                    int id;
                    try {
                        id = scanner.nextInt();
                    } catch (Exception e) {
                        System.out.println(ANSI_RED + "Invalid input! Try again." + ANSI_RESET);
                        continue;
                    }
                    if (id > result.size() || id < 1) {
                        System.out.println(ANSI_RED + "ProductID must be <= " + result.size() + " and >= 1!" + ANSI_RESET);
                        continue;
                    }
                    try {
                        System.out.println("Please wait while getting product information...");
                        if (result.get(id - 1).getProductURL().contains("dienmayxanh")) {
                            DienMayXanh.GetProductInformation.get(result.get(id - 1));
                        }
                        else if (result.get(id - 1).getProductURL().contains("meta")) {
                            meta.GetProductInformation.get(result.get(id - 1));
                        }
                        System.out.println(ANSI_GREEN + "Getting product information completed!" + ANSI_RESET);
                        System.out.println("----------------------------------");
                    } catch (IOException e) {
                        System.out.println(ANSI_RED + "Error while getting product information!" + ANSI_RESET);
                        return;
                    }

                    System.out.println(ANSI_CYAN + "Continue? (y/n): " + ANSI_RESET);
                    char x = scanner.next().charAt(0);
                    if (x == 'y') break;
                    if (x == 'n') return;
                    System.out.println(ANSI_RED + "Invalid option! Try again." + ANSI_RESET);
                }
            }
            else if (choice == 4) return;
            else System.out.println(ANSI_RED + "Invalid option! Try again." + ANSI_RESET);

        }
    }
}