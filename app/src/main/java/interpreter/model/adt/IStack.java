package interpreter.model.adt;

import java.util.List;

public interface IStack<T> {
    void push(T element);
    T pop();
    boolean isEmpty();
    List<T> getElements();
}
