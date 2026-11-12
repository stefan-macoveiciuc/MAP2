package interpreter.adt;

import interpreter.model.adt.MyDictionary;
import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyDictionaryTest {

    @Test
    void putAndLookup() throws ADTException {
        MyDictionary<String, Integer> dictionary =
                new MyDictionary<>();

        dictionary.put("a", 10);

        assertTrue(dictionary.isDefined("a"));
        assertEquals(10, dictionary.lookup("a"));
    }

    @Test
    void updateExistingKey() throws ADTException {
        MyDictionary<String, Integer> dictionary =
                new MyDictionary<>();

        dictionary.put("a", 10);
        dictionary.update("a", 20);

        assertEquals(20, dictionary.lookup("a"));
    }

    @Test
    void updateMissingKeyThrowsException() {
        MyDictionary<String, Integer> dictionary =
                new MyDictionary<>();

        assertThrows(ADTException.class,
                () -> dictionary.update("missing", 5));
    }

    @Test
    void lookupMissingKeyThrowsException() {
        MyDictionary<String, Integer> dictionary =
                new MyDictionary<>();

        assertThrows(ADTException.class,
                () -> dictionary.lookup("missing"));
    }

    @Test
    void removeExistingKey() throws ADTException {
        MyDictionary<String, Integer> dictionary =
                new MyDictionary<>();

        dictionary.put("a", 10);
        dictionary.remove("a");

        assertFalse(dictionary.isDefined("a"));
    }

    @Test
    void removeMissingKeyThrowsException() {
        MyDictionary<String, Integer> dictionary =
                new MyDictionary<>();

        assertThrows(ADTException.class,
                () -> dictionary.remove("missing"));
    }

    @Test
    void toMapReturnsReadOnlyCopy() {
        MyDictionary<String, Integer> dictionary =
                new MyDictionary<>();

        dictionary.put("a", 10);

        var copy = dictionary.toMap();

        assertEquals(10, copy.get("a"));

        assertThrows(UnsupportedOperationException.class,
                () -> copy.put("b", 20));

        dictionary.put("c", 30);

        assertFalse(copy.containsKey("c"));
    }
}