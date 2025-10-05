package algorithms;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MinHeapTest {
    @Test
    void basicOps() {
        MinHeap h = new MinHeap();
        h.insert(5); h.insert(2); h.insert(7); h.insert(1);
        assertEquals(1, h.peekMin());
        assertEquals(1, h.extractMin());
        assertEquals(2, h.peekMin());
    }
}
