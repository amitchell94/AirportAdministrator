package helper;

import java.util.Optional;
import java.util.Scanner;

public final class ScannerHelper {
    private static Scanner scanner = new Scanner(System.in);  // Create a Scanner object

    public static String readLine() {
        return scanner.nextLine();
    }

    public static void closeScanner() {
        scanner.close();
    }
}
