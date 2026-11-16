package sort;

import java.util.Arrays;
import java.util.Random;

/**
 * Lớp kiểm thử tính đúng đắn của InsertionSort và Mergesort.
 * Bạn có thể chạy trực tiếp file này (Right click -> Run trong IntelliJ) để kiểm tra implementation của mình.
 */
public class MergesortTest {

    public static void main(String[] args) {
        boolean allPassed = true;

        allPassed &= testInsertionSort();
        allPassed &= testMergesortWithVariousCutoffs();
        allPassed &= testSpecialCases();
        allPassed &= testLargeRandomArrays();

        if (allPassed) {
            System.out.println("All passed");
        } else {
            System.err.println("Failed");
        }
    }

    private static boolean testInsertionSort() {
        System.out.println("InsertionSort:");
        Integer[] arr = { 5, 2, 9, 1, 5, 6, 3, 8 };
        InsertionSort.sort(arr);
        boolean passed = Mergesort.isSorted(arr);
        printResult("InsertionSort trên mảng nhỏ", passed);
        return passed;
    }

    private static boolean testMergesortWithVariousCutoffs() {
        System.out.println("\nKiểm tra Mergesort với các giá trị CUTOFF khác nhau:");
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
        System.out.println("\nKiểm tra các trường hợp đặc biệt:");
        boolean passed = true;

        // 1. Mảng rỗng
        Integer[] empty = {};
        Mergesort.sort(empty, 10);
        boolean okEmpty = (empty.length == 0);
        printResult("Mảng rỗng", okEmpty);
        passed &= okEmpty;

        // 2. Mảng 1 phần tử
        Integer[] single = { 42 };
        Mergesort.sort(single, 10);
        boolean okSingle = (single.length == 1 && single[0] == 42);
        printResult("Mảng 1 phần tử", okSingle);
        passed &= okSingle;

        // 3. Mảng đã sắp xếp sẵn
        Integer[] sorted = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        Mergesort.sort(sorted, 10);
        boolean okSorted = Mergesort.isSorted(sorted);
        printResult("Mảng đã sắp xếp sẵn", okSorted);
        passed &= okSorted;

        // 4. Mảng đảo ngược hoàn toàn
        Integer[] reversed = { 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };
        Mergesort.sort(reversed, 10);
        boolean okReversed = Mergesort.isSorted(reversed);
        printResult("Mảng ngược", okReversed);
        passed &= okReversed;

        // 5. Mảng toàn phần tử giống nhau (all duplicates)
        Integer[] duplicates = { 7, 7, 7, 7, 7, 7, 7, 7 };
        Mergesort.sort(duplicates, 10);
        boolean okDup = Mergesort.isSorted(duplicates);
        printResult("Mảng nhiều phần tử trùng lặp", okDup);
        passed &= okDup;

        return passed;
    }

    private static boolean testLargeRandomArrays() {
        System.out.println("\nKiểm tra mảng ngẫu nhiên kích thước lớn (N = 10,000):");
        Integer[] arr = generateRandomArray(10000, 12345);
        Integer[] expected = arr.clone();
        Arrays.sort(expected);

        Mergesort.sort(arr, 10);

        boolean passed = Arrays.equals(arr, expected);
        printResult("Mảng N = 10,000 với CUTOFF = 10", passed);
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
