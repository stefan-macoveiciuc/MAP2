
package interpreter;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.Test;
import interpreter.model.adt.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ADTTest {

    @Test
    void stackUsesLifoOrder() throws ADTException {
        IStack<Integer> stack = new MyStack<>();

        stack.push(10);
        stack.push(20);
        stack.push(30);

        assertEquals(30, stack.pop());
        assertEquals(20, stack.pop());
        assertEquals(10, stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    void poppingEmptyStackThrows() {
        IStack<Integer> stack = new MyStack<>();

        assertThrows(ADTException.class, stack::pop);
    }

    @Test
    void dictionaryCanStoreAndLookup() throws ADTException {
        IDictionary<String, Integer> dictionary =
                new MyDictionary<>();

        dictionary.put("age", 20);

        assertTrue(dictionary.containsKey("age"));
        assertEquals(20, dictionary.get("age"));
    }

    @Test
    void dictionaryCanUpdateValue() throws ADTException {
        IDictionary<String, Integer> dictionary =
                new MyDictionary<>();

        dictionary.put("age", 20);
        dictionary.put("age", 21);

        assertEquals(21, dictionary.get("age"));
    }

    @Test
    void missingDictionaryKeyThrows() {
        IDictionary<String, Integer> dictionary =
                new MyDictionary<>();

        assertThrows(
                ADTException.class,
                () -> dictionary.get("missing")
        );
    }

    @Test
    void listCanAddGetAndCountElements() {
        IList<Integer> list = new MyList<>();

        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(10, list.getElements().get(0));
        assertEquals(20, list.getElements().get(1));
        assertEquals(30, list.getElements().get(2));

        assertEquals(3, list.getElements().size());
        assertEquals(List.of(10, 20, 30), list.getElements());
    }
}
