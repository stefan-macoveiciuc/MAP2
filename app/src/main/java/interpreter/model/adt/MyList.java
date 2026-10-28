package interpreter.model.adt;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements IList<T>{
    private final List<T> list;

    public MyList(){
        list = new ArrayList<>();
    }

    @Override
    public void add(T element){
        list.add(element);
    }

    @Override
    public List<T> getElements(){
        return new ArrayList<>(list);
    }

    @Override
    public String toString(){
        return list.toString();
    }
}
