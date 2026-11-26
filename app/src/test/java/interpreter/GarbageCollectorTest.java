
package interpreter;

import interpreter.model.type.*;
import interpreter.model.value.*;
import interpreter.controller.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GarbageCollectorTest {

    @Test
    void extractsAddressesFromSymbolTable() {
        List<IValue> values = List.of(
                new IntValue(100),
                new BoolValue(true),
                new StringValue("hello"),
                new RefValue(1, new IntType()),
                new RefValue(2, new IntType())
        );

        assertEquals(
                Set.of(1, 2),
                GarbageCollector.getAddrFromSymTable(values)
        );
    }

    @Test
    void duplicateAddressesAreRemoved() {
        List<IValue> values = List.of(
                new RefValue(1, new IntType()),
                new RefValue(1, new IntType())
        );

        assertEquals(
                Set.of(1),
                GarbageCollector.getAddrFromSymTable(values)
        );
    }

    @Test
    void unsafeCollectorRemovesUnreachableAddress() {
        Map<Integer, IValue> heap = Map.of(
                1, new IntValue(20),
                2, new IntValue(30)
        );

        Map<Integer, IValue> result =
                GarbageCollector.unsafeGarbageCollector(
                        Set.of(2), heap
                );

        assertEquals(
                Map.of(2, new IntValue(30)),
                result
        );
    }

    @Test
    void unsafeCollectorIncorrectlyRemovesNestedReference() {
        Map<Integer, IValue> heap = Map.of(
                1, new IntValue(20),
                2, new RefValue(1, new IntType()),
                3, new IntValue(30)
        );

        Map<Integer, IValue> result =
                GarbageCollector.unsafeGarbageCollector(
                        Set.of(2, 3), heap
                );

        assertFalse(result.containsKey(1));
        assertTrue(result.containsKey(2));
        assertTrue(result.containsKey(3));
    }

    @Test
    void safeCollectorPreservesNestedReference() {
        Map<Integer, IValue> heap = Map.of(
                1, new IntValue(20),
                2, new RefValue(1, new IntType()),
                3, new IntValue(30)
        );

        Map<Integer, IValue> result =
                GarbageCollector.safeGarbageCollector(
                        Set.of(2, 3), heap
                );

        assertEquals(heap, result);
    }

    @Test
    void safeCollectorRemovesUnreachableAddress() {
        Map<Integer, IValue> heap = Map.of(
                1, new IntValue(20),
                2, new IntValue(30),
                3, new IntValue(40)
        );

        Map<Integer, IValue> result =
                GarbageCollector.safeGarbageCollector(
                        Set.of(2), heap
                );

        assertEquals(
                Map.of(2, new IntValue(30)),
                result
        );
    }

    @Test
    void safeCollectorFollowsMultipleReferences() {
        Map<Integer, IValue> heap = Map.of(
                1, new IntValue(20),
                2, new RefValue(1, new IntType()),
                3, new RefValue(2, new RefType(new IntType())),
                4, new IntValue(100)
        );

        Map<Integer, IValue> result =
                GarbageCollector.safeGarbageCollector(
                        Set.of(3), heap
                );

        assertEquals(Set.of(1, 2, 3), result.keySet());
        assertFalse(result.containsKey(4));
    }

    @Test
    void noReferencesMeansEmptyHeap() {
        Map<Integer, IValue> heap = Map.of(
                1, new IntValue(20),
                2, new IntValue(30)
        );

        Map<Integer, IValue> result =
                GarbageCollector.safeGarbageCollector(
                        Set.of(), heap
                );

        assertTrue(result.isEmpty());
    }

    @Test
    void ignoresNullAddress() {
        Map<Integer, IValue> heap = Map.of(
                1, new IntValue(20)
        );

        Map<Integer, IValue> result =
                GarbageCollector.safeGarbageCollector(
                        Set.of(0), heap
                );

        assertTrue(result.isEmpty());
    }

    @Test
    void ignoresNonexistentAddress() {
        Map<Integer, IValue> heap = Map.of(
                1, new IntValue(20)
        );

        Map<Integer, IValue> result =
                GarbageCollector.safeGarbageCollector(
                        Set.of(100), heap
                );

        assertTrue(result.isEmpty());
    }

    @Test
    void reachableAddressesAreCorrect() {
        Map<Integer, IValue> heap = Map.of(
                1, new IntValue(20),
                2, new RefValue(1, new IntType()),
                3, new IntValue(30),
                4, new IntValue(40)
        );

        assertEquals(
                Set.of(1, 2, 3),
                GarbageCollector.reachableAddresses(
                        Set.of(2, 3), heap
                )
        );
    }

    @Test
    void collectorDoesNotModifyOriginalHeap() {
        Map<Integer, IValue> heap = Map.of(
                1, new IntValue(20),
                2, new IntValue(30)
        );

        GarbageCollector.safeGarbageCollector(
                Set.of(2), heap
        );

        assertEquals(2, heap.size());
    }
}
