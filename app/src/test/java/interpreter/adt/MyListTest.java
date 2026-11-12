package interpreter.adt;

import interpreter.model.adt.MyList;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class MyListTest {

    @Test
    void addAndGet() {
        MyList<String> list = new MyList<>();

        list.add("first");
        list.add("second");

        assertEquals("first", list.get(0));
        assertEquals("second", list.get(1));
        assertEquals(2, list.size());
    }

    @Test
    void toListPreservesInsertionOrder() {
        MyList<Integer> list = new MyList<>();

        list.add(3);
        list.add(1);
        list.add(2);

        assertEquals(List.of(3, 1, 2), list.toList());
    }

    @Test
    void toListReturnsReadOnlyCopy() {
        MyList<Integer> list = new MyList<>();
        list.add(10);

        var copy = list.toList();

        assertThrows(UnsupportedOperationException.class,
                () -> copy.add(20));

        list.add(30);

        assertEquals(List.of(10), copy);
    }
}