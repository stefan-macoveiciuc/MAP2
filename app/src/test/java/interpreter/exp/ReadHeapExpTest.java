
package interpreter.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.model.adt.*;
import interpreter.model.exp.*;
import interpreter.model.type.*;
import interpreter.model.value.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReadHeapExpTest {

    @Test
    void readsIntegerFromHeap() throws Exception {
        MyDictionary<String, IValue> symTable =
                new MyDictionary<>();

        MyHeap heap = new MyHeap();

        int address = heap.allocate(new IntValue(20));

        symTable.put("v", new RefValue(address, new IntType()));

        ReadHeapExp exp = new ReadHeapExp(new VarExp("v"));

        assertEquals(
                new IntValue(20),
                exp.eval(symTable, heap)
        );
    }

    @Test
    void readsBooleanFromHeap() throws Exception {
        MyDictionary<String, IValue> symTable =
                new MyDictionary<>();

        MyHeap heap = new MyHeap();

        int address = heap.allocate(new BoolValue(true));

        symTable.put("v", new RefValue(address, new BoolType()));

        assertEquals(
                new BoolValue(true),
                new ReadHeapExp(new VarExp("v"))
                        .eval(symTable, heap)
        );
    }

    @Test
    void readsNestedReference() throws Exception {
        MyDictionary<String, IValue> symTable =
                new MyDictionary<>();

        MyHeap heap = new MyHeap();

        int address1 = heap.allocate(new IntValue(20));

        int address2 = heap.allocate(
                new RefValue(address1, new IntType())
        );

        symTable.put(
                "a",
                new RefValue(address2, new RefType(new IntType()))
        );

        ReadHeapExp exp = new ReadHeapExp(
                new ReadHeapExp(new VarExp("a"))
        );

        assertEquals(
                new IntValue(20),
                exp.eval(symTable, heap)
        );
    }

    @Test
    void nonReferenceOperandThrows() {
        ReadHeapExp exp = new ReadHeapExp(
                new ValueExp(new IntValue(20))
        );

        assertThrows(
                ExpressionException.class,
                () -> exp.eval(new MyDictionary<>(), new MyHeap())
        );
    }

    @Test
    void uninitializedReferenceThrows() {
        MyDictionary<String, IValue> symTable =
                new MyDictionary<>();

        symTable.put("v", new RefValue(0, new IntType()));

        ReadHeapExp exp = new ReadHeapExp(new VarExp("v"));

        assertThrows(
                ExpressionException.class,
                () -> exp.eval(symTable, new MyHeap())
        );
    }

    @Test
    void nonexistentAddressThrows() {
        MyDictionary<String, IValue> symTable =
                new MyDictionary<>();

        symTable.put("v", new RefValue(100, new IntType()));

        ReadHeapExp exp = new ReadHeapExp(new VarExp("v"));

        assertThrows(
                ExpressionException.class,
                () -> exp.eval(symTable, new MyHeap())
        );
    }

    @Test
    void readsUpdatedHeapValue() throws Exception {
        MyDictionary<String, IValue> symTable =
                new MyDictionary<>();

        MyHeap heap = new MyHeap();

        int address = heap.allocate(new IntValue(20));

        symTable.put("v", new RefValue(address, new IntType()));

        ReadHeapExp exp = new ReadHeapExp(new VarExp("v"));

        assertEquals(new IntValue(20), exp.eval(symTable, heap));

        heap.update(address, new IntValue(30));

        assertEquals(new IntValue(30), exp.eval(symTable, heap));
    }
}
