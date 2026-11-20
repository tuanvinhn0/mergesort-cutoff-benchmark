package sort;

import java.util.Arrays;
import java.util.Random;

// Correctness tests for InsertionSort and Mergesort
public class MergesortTest {

    public static void main(String[] args) {
        boolean allPassed = true;

        allPassed &= testInsertionSort();
        allPassed &= testMergesortWithVariousCutoffs();
        allPassed &= testSpecialCases();
        allPassed &= testLargeRandomArrays();

        if (allPassed) {
            System.out.println("\nAll tests passed.");
        } else {
            System.err.println("\nSome tests FAILED.");
        }
    }

    private static boolean testInsertionSort() {
        System.out.println("InsertionSort:");
        Integer[] arr = { 5, 2, 9, 1, 5, 6, 3, 8 };
        InsertionSort.sort(arr);
        boolean passed = Mergesort.isSorted(arr);
        printResult("Small array", passed);
        return passed;
    }

    private static boolean testMergesortWithVariousCutoffs() {
        System.out.println("\nMergesort with various CUTOFF values:");
        int[] cutoffs = { 0, 1, 7, 10, 16, 32 };
        boolean passed = true;

        for (int cutoff : cutoffs) {
            Integer[] arr = generateRandomArray(100, 42);
            Integer[] expected = arr.clone();
            Arrays.sort(expected);

            Mergesort.sort(arr, cutoff);

            boolean ok = Arrays.equals(arr, expected);
            printResult("Mergesort (Cutoff = " + cutoff + ")", ok);
            passed &= ok;
        }
        return passed;
    }

    private static boolean testSpecialCases() {
        System.out.println("\nSpecial cases:");
        boolean passed = true;

        // Empty array
        Integer[] empty = {};
        Mergesort.sort(empty, 10);
        boolean okEmpty = (empty.length == 0);
        printResult("Empty array", okEmpty);
        passed &= okEmpty;

        // Single element
        Integer[] single = { 42 };
        Mergesort.sort(single, 10);
        boolean okSingle = (single.length == 1 && single[0] == 42);
        printResult("Single element", okSingle);
        passed &= okSingle;

        // Already sorted
        Integer[] sorted = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        Mergesort.sort(sorted, 10);
        boolean okSorted = Mergesort.isSorted(sorted);
        printResult("Already sorted", okSorted);
        passed &= okSorted;

        // Reverse sorted
        Integer[] reversed = { 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };
        Mergesort.sort(reversed, 10);
        boolean okReversed = Mergesort.isSorted(reversed);
        printResult("Reverse sorted", okReversed);
        passed &= okReversed;

        // All duplicates
        Integer[] duplicates = { 7, 7, 7, 7, 7, 7, 7, 7 };
        Mergesort.sort(duplicates, 10);
        boolean okDup = Mergesort.isSorted(duplicates);
        printResult("All duplicates", okDup);
        passed &= okDup;

        return passed;
    }

    private static boolean testLargeRandomArrays() {
        System.out.println("\nLarge random array (N = 10,000):");
        Integer[] arr = generateRandomArray(10000, 12345);
        Integer[] expected = arr.clone();
        Arrays.sort(expected);

        Mergesort.sort(arr, 10);

        boolean passed = Arrays.equals(arr, expected);
        printResult("N = 10,000 with CUTOFF = 10", passed);
        return passed;
    }

    private static Integer[] generateRandomArray(int n, long seed) {
        Random rand = new Random(seed);
        Integer[] arr = new Integer[n];
        for (int i = 0; i < n; i++) {
            arr[i] = rand.nextInt(100000);
        }
        return arr;
    }

    private static void printResult(String testName, boolean passed) {
        if (passed) {
            System.out.printf("  [PASSED] %s\n", testName);
        } else {
            System.err.printf("  [FAILED] %s\n", testName);
        }
    }
}
