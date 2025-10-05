package cli;

import algorithms.MinHeap;
import metrics.PerformanceTracker;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Simple CLI benchmark that prints CSV rows:
 * columns: n,mode,build_ms,extract_ms,total_ms,comparisons,swaps,arrayAccesses,allocationsN
 *
 * Usage:
 *   mvn -q -DskipTests exec:java -Dexec.mainClass="cli.BenchmarkRunner" -Dexec.args="--n=100,1000,10000"
 *
 * Modes:
 *  - insert: build heap by inserting each element
 *  - heapify: build heap via O(n) bottom-up heapify
 */
public class BenchmarkRunner {

    public static void main(String[] args) {
        List<Integer> sizes = List.of(100, 1000, 10000);
        long seed = 42L;

        for (String a : args) {
            if (a.startsWith("--n=")) {
                String list = a.substring(4).trim();
                sizes = Arrays.stream(list.split(","))
                        .map(String::trim).filter(s -> !s.isEmpty())
                        .map(Integer::parseInt).collect(Collectors.toList());
            } else if (a.startsWith("--seed=")) {
                seed = Long.parseLong(a.substring(7).trim());
            }
        }

        System.out.println("n,mode,build_ms,extract_ms,total_ms,comparisons,swaps,arrayAccesses,allocationsN");

        for (int n : sizes) {
            int[] data = randomArray(n, seed);

            // ----- Mode: insert -----
            PerformanceTracker t1 = new PerformanceTracker();
            long t0 = System.nanoTime();
            MinHeap h1 = new MinHeap(t1);
            for (int v : data) h1.insert(v);
            long tBuild1 = System.nanoTime();

            // extract all to exercise structure
            while (!h1.isEmpty()) {
                h1.extractMin();
            }
            long tEnd1 = System.nanoTime();
            printCsv(n, "insert", t0, tBuild1, tEnd1, t1);

            // ----- Mode: heapify -----
            PerformanceTracker t2 = new PerformanceTracker();
            long t2a = System.nanoTime();
            MinHeap h2 = MinHeap.heapify(data, t2);
            long t2b = System.nanoTime();
            while (!h2.isEmpty()) {
                h2.extractMin();
            }
            long t2c = System.nanoTime();
            printCsv(n, "heapify", t2a, t2b, t2c, t2);
        }
    }

    private static int[] randomArray(int n, long seed) {
        Random r = new Random(seed + n * 1315423911L);
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = r.nextInt();
        return a;
    }

    private static void printCsv(int n, String mode, long tStart, long tBuildDone, long tEnd, PerformanceTracker t) {
        long buildMs = (tBuildDone - tStart) / 1_000_000;
        long extractMs = (tEnd - tBuildDone) / 1_000_000;
        long totalMs = (tEnd - tStart) / 1_000_000;
        System.out.printf(Locale.ROOT,
                "%d,%s,%d,%d,%d,%d,%d,%d,%d%n",
                n, mode, buildMs, extractMs, totalMs,
                t.comparisons, t.swaps, t.arrayAccesses, t.allocationsN);
    }
}
