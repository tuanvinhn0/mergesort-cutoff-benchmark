package benchmark;

import sort.Mergesort;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class BenchmarkRunner {

    private static final int[] SIZES = { 10_000, 50_000, 100_000, 500_000, 1_000_000 };
    private static final int[] CUTOFFS = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 18, 20, 24, 28, 32 };

    // Run 25 rounds total, discard first 5 (JVM warm-up), keep last 20 for measurement
    private static final int TOTAL_ROUNDS = 25;
    private static final int DISCARD = 5;
    private static final int MEASURED_ROUNDS = TOTAL_ROUNDS - DISCARD;

    private static final String CSV_PATH = "results/benchmark_cutoff.csv";

    public static void main(String[] args) {
        System.out.println("========== MERGESORT CUTOFF BENCHMARK ==========");
        System.out.printf("Config: %d total rounds, discard first %d, measure last %d\n\n",
                TOTAL_ROUNDS, DISCARD, MEASURED_ROUNDS);

        File resultsDir = new File("results");
        if (!resultsDir.exists()) resultsDir.mkdirs();

        try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_PATH))) {
            writer.println("N,Cutoff,Distribution,MeanTimeMs,StdDevMs,MinTimeMs,MaxTimeMs,Trials");

            for (int n : SIZES) {
                System.out.printf(">>> Running benchmark for N = %,d ...\n", n);
                runBenchmarkForSize(n, "Random", writer);
            }

            System.out.println("\nBenchmark complete! Results saved to: " + CSV_PATH);
        } catch (IOException e) {
            System.err.println("Error writing CSV: " + e.getMessage());
        }
    }

    private static void runBenchmarkForSize(int n, String distribution, PrintWriter writer) {
        long baseSeed = 10007L;

        for (int cutoff : CUTOFFS) {
            double[] times = new double[MEASURED_ROUNDS];
            int recorded = 0;

            for (int trial = 0; trial < TOTAL_ROUNDS; trial++) {
                // Same seed per trial ensures all cutoff values are tested on the same data
                Random rand = new Random(baseSeed + trial);
                Integer[] a = DataGenerator.random(n, rand);

                long start = System.nanoTime();
                Mergesort.sort(a, cutoff);
                long elapsed = System.nanoTime() - start;

                // Skip first DISCARD rounds (let JVM JIT optimize), only record the rest
                if (trial >= DISCARD) {
                    times[recorded++] = elapsed / 1_000_000.0;
                }
            }

            double mean = calculateMean(times);
            double stdDev = calculateStdDev(times, mean);
            double min = calculateMin(times);
            double max = calculateMax(times);

            writer.printf("%d,%d,%s,%.4f,%.4f,%.4f,%.4f,%d\n",
                    n, cutoff, distribution, mean, stdDev, min, max, MEASURED_ROUNDS);

            System.out.printf("  N = %-8s | Cutoff = %-2d | Mean: %8.3f ms | StdDev: %6.3f ms\n",
                    String.format("%,d", n), cutoff, mean, stdDev);
        }
        writer.flush();
    }

    private static double calculateMean(double[] data) {
        double sum = 0;
        for (double v : data) sum += v;
        return sum / data.length;
    }

    private static double calculateStdDev(double[] data, double mean) {
        double sumSq = 0;
        for (double v : data) sumSq += Math.pow(v - mean, 2);
        return Math.sqrt(sumSq / data.length);
    }

    private static double calculateMin(double[] data) {
        double min = data[0];
        for (double v : data) if (v < min) min = v;
        return min;
    }

    private static double calculateMax(double[] data) {
        double max = data[0];
        for (double v : data) if (v > max) max = v;
        return max;
    }
}
