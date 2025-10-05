package metrics;

public class PerformanceTracker {
    public long comparisons;     // e.g., value comparisons in heap ops
    public long swaps;           // number of element swaps
    public long arrayAccesses;   // get/set operations touching the backing store
    public long allocationsN;    // elements allocated or copied into structures

    public void reset() {
        comparisons = 0;
        swaps = 0;
        arrayAccesses = 0;
        allocationsN = 0;
    }

    @Override
    public String toString() {
        return "comparisons=" + comparisons +
               ", swaps=" + swaps +
               ", arrayAccesses=" + arrayAccesses +
               ", allocationsN=" + allocationsN;
    }
}
