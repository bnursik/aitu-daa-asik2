package algorithms;

import metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MinHeapTest {

    @Test
    void basicOps() {
        MinHeap h = new MinHeap();
        h.insert(5); h.insert(2); h.insert(7); h.insert(1);
        assertEquals(1, h.peekMin());
        assertEquals(1, h.extractMin());
        assertEquals(2, h.peekMin());
         // after extract -> 3 elements, but size() counts current
        // Correct: after one extract size should be 3
        // Fix:
        assertEquals(3, h.size());
    }

    @Test
    void throwsOnPeekEmpty() {
        MinHeap h = new MinHeap();
        assertThrowsExactly(java.util.NoSuchElementException.class, h::peekMin);
    }

    @Test
    void throwsOnExtractEmpty() {
        MinHeap h = new MinHeap();
        assertThrowsExactly(java.util.NoSuchElementException.class, h::extractMin);
    }

    @Test
    void handlesDuplicates() {
        MinHeap h = new MinHeap();
        h.insert(3); h.insert(3); h.insert(1); h.insert(1); h.insert(2);
        assertEquals(1, h.extractMin());
        assertEquals(1, h.extractMin());
        assertEquals(2, h.extractMin());
        assertEquals(3, h.extractMin());
        assertEquals(3, h.extractMin());
        assertTrue(h.isEmpty());
    }

    @Test
    void decreaseKeyRejectsLargerValue() {
        MinHeap h = new MinHeap();
        h.insert(10); h.insert(20); h.insert(30);
        assertThrows(IllegalArgumentException.class, () -> h.decreaseKey(1, 25));
    }

    @Test
    void decreaseKeyIndexOutOfBounds() {
        MinHeap h = new MinHeap();
        h.insert(10);
        assertThrows(IndexOutOfBoundsException.class, () -> h.decreaseKey(5, 1));
    }

    @Test
    void mergePreservesHeapProperty() {
        MinHeap a = new MinHeap();
        a.insert(5); a.insert(7); a.insert(9);

        MinHeap b = new MinHeap();
        b.insert(1); b.insert(2); b.insert(3);

        MinHeap m = MinHeap.merge(a, b);
        int prev = Integer.MIN_VALUE;
        while (!m.isEmpty()) {
            int x = m.extractMin();
            assertTrue(x >= prev);
            prev = x;
        }
    }

    @Test
    void heapifyMatchesInsertionBuild() {
        int[] data = {5, 7, 1, 9, 2, 6, 3, 4, 8};
        PerformanceTracker tA = new PerformanceTracker();
        MinHeap hA = new MinHeap(tA);
        for (int v : data) hA.insert(v);

        PerformanceTracker tB = new PerformanceTracker();
        MinHeap hB = MinHeap.heapify(data, tB);

        while (!hA.isEmpty()) {
            assertEquals(hA.extractMin(), hB.extractMin());
        }
        assertTrue(hB.isEmpty());
    }
}
