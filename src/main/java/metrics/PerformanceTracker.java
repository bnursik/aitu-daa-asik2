package metrics;

public class PerformanceTracker {
    public long comparisons;
    public long swaps;
    public long arrayAccesses;
    public long allocationsN;

    public void reset() {
        comparisons = swaps = arrayAccesses = allocationsN = 0;
    }
}
