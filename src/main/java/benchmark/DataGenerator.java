package benchmark;

import java.util.Random;

public class DataGenerator {
    // Generate a uniformly distributed random array
    public static Integer[] random(int n, Random rand) {
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++) {
            a[i] = rand.nextInt();
        }
        return a;
    }
    // Generate a sorted (ascending) array
    public static Integer[] sorted(int n) {
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++) {
            a[i] = i;
        }
        return a;
    }
    // Generate a fully reversed (descending) array
    public static Integer[] reversed(int n) {
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++) {
            a[i] = n - 1 - i;
        }
        return a;
    }
    // Generate a nearly sorted array (randomly swap 5% of pairs)
    public static Integer[] partiallySorted(int n, Random rand) {
        Integer[] a = sorted(n);
        int swaps = (int) (n * 0.05);
        for (int k = 0; k < swaps; k++) {
            int i = rand.nextInt(n);
            int j = rand.nextInt(n);
            int tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
        return a;
    }
    // Generate an array with many duplicate values
    public static Integer[] duplicates(int n, int numUnique, Random rand) {
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++) {
            a[i] = rand.nextInt(numUnique);
        }
        return a;
    }
}
