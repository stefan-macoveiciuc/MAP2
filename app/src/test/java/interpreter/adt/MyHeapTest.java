
package interpreter.adt;

import interpreter.exceptions.ADTException;
import interpreter.model.adt.MyHeap;
import interpreter.model.value.*;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MyHeapTest {

    @Test
    void heapInitiallyEmpty() {
        MyHeap heap = new MyHeap();

        assertTrue(heap.getContent().isEmpty());
    }

    @Test
    void firstAddressIsOne() {
        MyHeap heap = new MyHeap();

        assertEquals(1, heap.allocate(new IntValue(20)));
    }

    @Test
    void allocationUsesDifferentAddresses() {
        MyHeap heap = new MyHeap();

        assertEquals(1, heap.allocate(new IntValue(20)));
        assertEquals(2, heap.allocate(new IntValue(30)));
        assertEquals(3, heap.allocate(new IntValue(40)));
    }

    @Test
    void getAllocatedValue() throws Exception {
        MyHeap heap = new MyHeap();

        int address = heap.allocate(new IntValue(20));

        assertEquals(new IntValue(20), heap.get(address));
    }

    @Test
    void updateExistingValue() throws Exception {
        MyHeap heap = new MyHeap();

        int address = heap.allocate(new IntValue(20));

        heap.update(address, new IntValue(30));

        assertEquals(new IntValue(30), heap.get(address));
        assertEquals(1, heap.getContent().size());
    }

    @Test
    void addressIsDefined() {
        MyHeap heap = new MyHeap();

        int address = heap.allocate(new IntValue(20));

        assertTrue(heap.isDefined(address));
        assertFalse(heap.isDefined(0));
        assertFalse(heap.isDefined(100));
    }

    @Test
    void readingInvalidAddressThrows() {
        MyHeap heap = new MyHeap();

        assertThrows(
                ADTException.class,
                () -> heap.get(100)
        );
    }

    @Test
    void updatingInvalidAddressThrows() {
        MyHeap heap = new MyHeap();

        assertThrows(
                ADTException.class,
                () -> heap.update(100, new IntValue(20))
        );
    }

    @Test
    void setContentReplacesOldHeap() {
        MyHeap heap = new MyHeap();

        heap.allocate(new IntValue(20));
        heap.allocate(new IntValue(30));

        heap.setContent(Map.of(2, new IntValue(30)));

        assertFalse(heap.isDefined(1));
        assertTrue(heap.isDefined(2));
        assertEquals(1, heap.getContent().size());
    }

    @Test
    void allocationAfterGarbageCollection() {
        MyHeap heap = new MyHeap();

        heap.allocate(new IntValue(20));
        heap.allocate(new IntValue(30));

        heap.setContent(Map.of(2, new IntValue(30)));

        int address = heap.allocate(new IntValue(40));

        assertEquals(3, address);
    }

    @Test
    void getContentIsReadOnly() {
        MyHeap heap = new MyHeap();

        heap.allocate(new IntValue(20));

        assertThrows(
                UnsupportedOperationException.class,
                () -> heap.getContent().put(2, new IntValue(30))
        );
    }
}
