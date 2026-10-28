package interpreter.model.adt;

import javax.management.RuntimeErrorException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class MyStack<T> implements IStack<T> {
    private final Deque<T> stack;

    public MyStack(){
        stack = new ArrayDeque<>();
    }

    @Override
    public void push(T element) {
        stack.push(element);
    }

    @Override
    public T pop(){
        if(stack.isEmpty()){
            throw new RuntimeException("Stack is empty!");
        }
        return stack.pop();
    }

    @Override
    public boolean isEmpty(){
        return stack.isEmpty();
    }

    @Override
    public List<T> getElements(){
        return new ArrayList<>(stack);
    }

    @Override
    public String toString(){
        return stack.toString();
    }
}
