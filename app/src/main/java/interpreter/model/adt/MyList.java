
package interpreter.model.adt;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements IList<T> {

    private final List<T> list;

    public MyList() {
        list = new ArrayList<>();
    }

    @Override
    public void add(T value) {
        list.add(value);
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public List<T> toList() {
        return List.copyOf(list);
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
