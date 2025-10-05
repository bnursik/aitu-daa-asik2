package algorithms;

import metrics.PerformanceTracker;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Binary min-heap (0-based index, parent=(i-1)/2, children=2i+1,2i+2).
 * Invariant: heap[0] is the minimum; for every i>0, heap[parent(i)] <= heap[i].
 * <p>
 * Metrics:
 *  - comparisons: value comparisons
 *  - swaps: exchanges of two positions
 *  - arrayAccesses: get/set on backing list
 *  - allocationsN: elements added/copied during construction/merge
 */
public class MinHeap {
    private final List<Integer> heap = new ArrayList<>();
    private final PerformanceTracker tracker;

    /** Construct with an external tracker (shared across runs if desired). */
    public MinHeap(PerformanceTracker tracker) {
        this.tracker = (tracker == null) ? new PerformanceTracker() : tracker;
    }

    /** Construct with a fresh tracker. */
    public MinHeap() {
        this(new PerformanceTracker());
    }

    /** O(n) heapify from an int array (bulk build). */
    public static MinHeap heapify(int[] data, PerformanceTracker tracker) {
        MinHeap h = new MinHeap(tracker);
        if (data != null && data.length > 0) {
            // Allocate and copy
            for (int v : data) {
                h.heap.add(v);
                if (h.tracker != null) h.tracker.allocationsN++;
                // Count array access for reading v from input array:
                if (h.tracker != null) h.tracker.arrayAccesses++;
            }
            // Bottom-up heapify
            for (int i = h.heap.size() / 2 - 1; i >= 0; i--) {
                h.siftDown(i);
            }
        }
        return h;
    }

    public PerformanceTracker metrics() {
        return tracker;
    }

    public int size() { return heap.size(); }
    public boolean isEmpty() { return heap.isEmpty(); }

    public void insert(int x) {
        add(x);               // counts allocation + access
        siftUp(size() - 1);
    }

    public int peekMin() {
        if (heap.isEmpty()) throw new NoSuchElementException("min-heap is empty");
        return get(0);
    }

    public int extractMin() {
        if (heap.isEmpty()) throw new NoSuchElementException("min-heap is empty");
        int min = get(0);
        int last = removeLast(); // counts access
        if (!heap.isEmpty()) {
            set(0, last);
            siftDown(0);
        }
        return min;
    }

    /**
     * Decrease key at index i to newValue (must be <= current).
     */
    public void decreaseKey(int index, int newValue) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("index out of bounds: " + index);
        }
        int current = get(index);
        if (tracker != null) tracker.comparisons++;
        if (newValue > current) {
            throw new IllegalArgumentException("newValue must be <= current value");
        }
        set(index, newValue);
        siftUp(index);
    }

    /**
     * Merge two heaps by concatenation then heapify in O(n).
     */
    public static MinHeap merge(MinHeap a, MinHeap b) {
        PerformanceTracker t = new PerformanceTracker();
        MinHeap m = new MinHeap(t);
        if (a != null) {
            for (int i = 0; i < a.heap.size(); i++) {
                int v = a.getNoCount(i); // no double-count from a
                m.heap.add(v);
                m.tracker.allocationsN++;
                m.tracker.arrayAccesses++; // write into new arraylist
            }
        }
        if (b != null) {
            for (int i = 0; i < b.heap.size(); i++) {
                int v = b.getNoCount(i);
                m.heap.add(v);
                m.tracker.allocationsN++;
                m.tracker.arrayAccesses++;
            }
        }
        for (int i = m.heap.size() / 2 - 1; i >= 0; i--) m.siftDown(i);
        return m;
    }

    /* ================== internals with metrics ================== */

    private void siftUp(int i) {
        while (i > 0) {
            int p = (i - 1) / 2;
            // compare heap[p] <= heap[i] ?
            if (tracker != null) tracker.comparisons++;
            if (get(p) <= get(i)) break;
            swap(i, p);
            i = p;
        }
    }

    private void siftDown(int i) {
        int n = size();
        while (true) {
            int l = 2 * i + 1, r = 2 * i + 2, smallest = i;
            if (l < n) {
                if (tracker != null) tracker.comparisons++;
                if (get(l) < get(smallest)) smallest = l;
            }
            if (r < n) {
                if (tracker != null) tracker.comparisons++;
                if (get(r) < get(smallest)) smallest = r;
            }
            if (smallest == i) break;
            swap(i, smallest);
            i = smallest;
        }
    }

    private void swap(int i, int j) {
        int a = get(i);
        int b = get(j);
        set(i, b);
        set(j, a);
        if (tracker != null) tracker.swaps++;
        // Note: arrayAccesses already counted in get/set
    }

    /* ---- wrappers that count array accesses/allocations ---- */

    private int get(int idx) {
        if (tracker != null) tracker.arrayAccesses++;
        return heap.get(idx);
    }
    private int getNoCount(int idx) {
        return heap.get(idx);
    }
    private void set(int idx, int value) {
        if (tracker != null) tracker.arrayAccesses++;
        heap.set(idx, value);
    }
    private void add(int value) {
        heap.add(value);
        if (tracker != null) {
            tracker.arrayAccesses++; // write
            tracker.allocationsN++;  // logical growth
        }
    }
    private int removeLast() {
        // one read for last element
        if (tracker != null) tracker.arrayAccesses++;
        return heap.remove(heap.size() - 1);
    }
}
