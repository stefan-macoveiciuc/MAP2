package interpreter.adt;

import interpreter.model.adt.MyStack;
import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class MyStackTest {

    @Test
    void pushAndPopFollowLifoOrder() throws ADTException {
        MyStack<Integer> stack = new MyStack<>();

        stack.push(10);
        stack.push(20);

        assertEquals(20, stack.pop());
        assertEquals(10, stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    void popEmptyStackThrowsException() {
        MyStack<Integer> stack = new MyStack<>();

        assertThrows(ADTException.class, stack::pop);
    }

    @Test
    void toListReturnsTopFirst() {
        MyStack<Integer> stack = new MyStack<>();

        stack.push(10);
        stack.push(20);
        stack.push(30);

        assertEquals(List.of(30, 20, 10), stack.toList());
    }

    @Test
    void toListDoesNotModifyStack() throws ADTException {
        MyStack<Integer> stack = new MyStack<>();
        stack.push(5);

        stack.toList();

        assertEquals(5, stack.pop());
    }
}