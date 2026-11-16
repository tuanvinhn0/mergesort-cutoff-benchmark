package sort;

// Mergesort
public class Mergesort {
    public static final int DEFAULT_CUTOFF = 10;

    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > hi) {
                a[k] = aux[i++];
            } else if (less(aux[j], aux[i])) {
                a[k] = aux[j++];
            } else {
                a[k] = aux[i++];
            }
        }
    }

    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi, int cutoff) {
        if (cutoff <= 0) {
            if (hi <= lo) return;
        } else {
            if (hi <= lo + cutoff - 1) {
                InsertionSort.sort(a, lo, hi);
                return;
            }
        }

        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid, cutoff);
        sort(a, aux, mid + 1, hi, cutoff);
        merge(a, aux, lo, mid, hi);
    }

    public static void sort(Comparable[] a, int cutoff) {
        if (a == null || a.length <= 1) return;
        Comparable[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length - 1, cutoff);
    }

    public static void sort(Comparable[] a) {
        sort(a, DEFAULT_CUTOFF);
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    public static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1])) return false;
        }
        return true;
    }
}
