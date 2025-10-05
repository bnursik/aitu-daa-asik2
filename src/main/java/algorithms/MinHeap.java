package algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class MinHeap {
    private final List<Integer> heap = new ArrayList<>();

    public int size() { return heap.size(); }
    public boolean isEmpty() { return heap.isEmpty(); }

    public void insert(int x) {
        heap.add(x);
        siftUp(heap.size() - 1);
    }

    public int peekMin() {
        if (heap.isEmpty()) throw new NoSuchElementException("empty heap");
        return heap.get(0);
    }

    public int extractMin() {
        if (heap.isEmpty()) throw new NoSuchElementException("empty heap");
        int min = heap.get(0);
        int last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            siftDown(0);
        }
        return min;
    }

    // Student A required ops:
    public void decreaseKey(int index, int newValue) {
        if (index < 0 || index >= heap.size()) throw new IndexOutOfBoundsException();
        if (newValue > heap.get(index)) throw new IllegalArgumentException("newValue must be <= current");
        heap.set(index, newValue);
        siftUp(index);
    }

    public static MinHeap merge(MinHeap a, MinHeap b) {
        MinHeap m = new MinHeap();
        m.heap.addAll(a.heap);
        m.heap.addAll(b.heap);
        // build-heap in O(n)
        for (int i = m.heap.size()/2 - 1; i >= 0; i--) m.siftDown(i);
        return m;
    }

    private void siftUp(int i) {
        while (i > 0) {
            int p = (i - 1) / 2;
            if (heap.get(p) <= heap.get(i)) break;
            swap(i, p);
            i = p;
        }
    }

    private void siftDown(int i) {
        int n = heap.size();
        while (true) {
            int l = 2*i + 1, r = 2*i + 2, smallest = i;
            if (l < n && heap.get(l) < heap.get(smallest)) smallest = l;
            if (r < n && heap.get(r) < heap.get(smallest)) smallest = r;
            if (smallest == i) break;
            swap(i, smallest);
            i = smallest;
        }
    }

    private void swap(int i, int j) {
        int tmp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, tmp);
    }
}
