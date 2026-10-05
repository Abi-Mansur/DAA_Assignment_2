import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DataStructuresTest {
    private Metrics metrics;

    @BeforeEach
    void setUp() {
        metrics = new Metrics();
    }

    @Test
    void testDynamicArrayBoundaryAndExceptions() {
        DynamicArray array = new DynamicArray(metrics);
        assertThrows(IndexOutOfBoundsException.class, () -> array.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> array.remove(0));

        array.add(10);
        array.add(20);
        assertEquals(10, array.get(0));
        assertEquals(20, array.get(1));

        array.add(1, 15);
        assertEquals(15, array.get(1));
        assertEquals(3, array.size());

        array.remove(1);
        assertEquals(20, array.get(1));
        assertEquals(2, array.size());
    }

    @Test
    void testMyLinkedListOperations() {
        MyLinkedList list = new MyLinkedList(metrics);
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));

        list.add(100);
        list.add(200);
        assertTrue(list.contains(100));
        assertFalse(list.contains(300));

        list.add(0, 50);
        assertEquals(50, list.get(0));

        list.remove(1);
        assertEquals(200, list.get(1));
    }

    @Test
    void testMinHeapOrderingAndProperty() {
        MinHeap heap = new MinHeap(5, metrics);
        assertThrows(IllegalStateException.class, heap::extractMin);
        assertThrows(IllegalStateException.class, heap::peekMin);

        heap.insert(42);
        heap.insert(15);
        heap.insert(108);
        heap.insert(4);

        assertEquals(4, heap.peekMin());
        assertEquals(4, heap.extractMin());
        assertEquals(15, heap.extractMin());
        assertEquals(42, heap.extractMin());
        assertEquals(108, heap.extractMin());
    }
}