package interpreter.model.adt;

import java.util.List;

public interface IList<T> {
    void add(T element);
    List<T> getElements();
}
