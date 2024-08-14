package JFX;

import ProductType.ED.BlackListWord;
import ProductType.ED.Sorter;
import ProductType.ED.ElectronicDevice;
import font.TextFont;

import java.io.FileWriter;
import java.io.IOException;


import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static Scanner scanner;
    public static ArrayList<ElectronicDevice> result = new ArrayList<>();

    //Return true if user want to continue after displaying products
    //Return false if user want to exit
    public static boolean displayProduct() {
        int counter = 0;
        System.out.println("----------------------------------");
        for (ElectronicDevice electronicDevice : result) {
            counter++;
            System.out.println("ProductID: " + counter);
            System.out.println(electronicDevice);
            System.out.println("----------------------------------");
        }
        exportDataListToTxt();
        while (true) {
            System.out.println(ANSI_CYAN + "Continue? (y/n): " + ANSI_RESET);
            char x = scanner.next().charAt(0);
            if (x == 'y') return true;
            if (x == 'n') return false;
            System.out.println(ANSI_RED + "Invalid option! Please try again." + ANSI_RESET);
        }
    }

    //Return true if user want to continue after sorting products
    //Return false if user want to exit
    public static boolean sortProductList() {
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
        exportDataListToTxt();

        while (true) {
            System.out.println(ANSI_CYAN + "Continue? (y/n): " + ANSI_RESET);
            char x = scanner.next().charAt(0);
            if (x == 'y') return true;
            if (x == 'n') return false;
            System.out.println(ANSI_RED + "Invalid option! Try again." + ANSI_RESET);
        }
    }

    //Return true if user want to continue after getting product information
    //Return false if user want to exit or getting an exception
    public static boolean getProductInfo() {
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
                ElectronicDevice electronicDevice = result.get(id - 1);
                int dataSource = 0;
                if (electronicDevice.getProductURL().contains("dienmayxanh")) {
                    DienMayXanh.GetProductInformation.get(electronicDevice);
                    dataSource = 1;
                }
                else if (electronicDevice.getProductURL().contains("meta")) {
                    meta.GetProductInformation.get(electronicDevice);
                    dataSource = 2;
                }
                else if (electronicDevice.getProductURL().contains("mediamart")) {
                    mediamart.GetProductInformation.get(electronicDevice);
                    dataSource = 3;
                }
                System.out.println(ANSI_GREEN + "Getting product information completed!" + ANSI_RESET);
                System.out.println("----------------------------------");
                exportProductInformationToTxt(dataSource);
            } catch (IOException e) {
                System.out.println(ANSI_RED + "Error while getting product information!" + ANSI_RESET);
                return false;
            }

            System.out.println(ANSI_CYAN + "Continue? (y/n): " + ANSI_RESET);
            char x = scanner.next().charAt(0);
            if (x == 'y') return true;
            if (x == 'n') return false;
            System.out.println(ANSI_RED + "Invalid option! Try again." + ANSI_RESET);
        }
    }

    //Export data to a file .txt, need absolute path of the file
    public static void exportDataListToTxt() {
        System.out.println("Do you want to export these data to a file? (y/n): ");
        if (scanner.next().charAt(0) == 'y') {
            System.out.println("Enter the file .txt path: ");
            String filePath = scanner.next();
            try (FileWriter writer = new FileWriter(filePath)) {
                for (ElectronicDevice electronicDevice : result) {
                    writer.write(electronicDevice.toString() + "\n\n");
                }
                System.out.println(ANSI_GREEN + "Exporting data to file completed!" + ANSI_RESET);
            } catch (IOException e) {
                System.out.println(ANSI_RED + "Error while exporting data to file!" + ANSI_RESET);
            }
        }
    }

    //Export product information to a file.txt, need absolute path of the file
    public static void exportProductInformationToTxt(int dataSource) {
        System.out.println("Do you want to export product information to a file? (y/n): ");
        if (scanner.next().charAt(0) == 'y') {
            System.out.println("Enter the file .txt path: ");
            String filePath = scanner.next();
            String textFileContent = "";
            try (FileWriter writer = new FileWriter(filePath)) {
                if (dataSource == 1)
                    textFileContent = DienMayXanh.GetProductInformation.allText.toString();
                else if (dataSource == 2)
                    textFileContent = meta.GetProductInformation.allText.toString();
                else if (dataSource == 3)
                    textFileContent = mediamart.GetProductInformation.allText.toString();
                else System.out.println("Invalid data source!");

                textFileContent = TextFont.resetFont(textFileContent);
                writer.write(textFileContent);

                System.out.println(ANSI_GREEN + "Exporting product information to file completed!" + ANSI_RESET);
            } catch (IOException e) {
                System.out.println(ANSI_RED + "Error while exporting product information to file!" + ANSI_RESET);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println(ANSI_GREEN + "This program will get products data from dienmayxanh.com, meta.vn, mediamart.vn and display product details. It also provides some sorting options." + ANSI_RESET);
        System.out.println(ANSI_GREEN + "Insert product name, example : may-giat." + ANSI_RESET);
        String product_name;
        scanner = new Scanner(System.in);
        product_name = scanner.nextLine();

        //Getting data from websites and add them into an ArrayList
        System.out.println(ANSI_GREEN + "Please wait while getting data..." + ANSI_RESET);
        BlackListWord.initBlackListWords();


        result.addAll(DienMayXanh.GetProductsData.get(product_name));
        result.addAll(meta.GetProductsData.get(product_name));
        result.addAll(mediamart.GetProductsData.get(product_name));

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
                if (!displayProduct()) return;

            }
            else if (choice == 2) {
                if (!sortProductList()) return;
            }
            else if (choice == 3) {
                if (!getProductInfo()) return;
            }
            else if (choice == 4) return;
            else System.out.println(ANSI_RED + "Invalid option! Please try again." + ANSI_RESET);

        }
    }
}