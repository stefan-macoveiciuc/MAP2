package interpreter;

import interpreter.model.adt.*;

public class Main {

    public static void main(String[] args) {

        IStack<Integer> stack = new MyStack<>();
        stack.push(10);
        stack.push(20);

        System.out.println("Stack: " + stack);
        System.out.println("Popped: " + stack.pop());

        IDictionary<String, Integer> dictionary = new MyDictionary<>();
        dictionary.put("age", 21);

        System.out.println("Dictionary: " + dictionary);
        System.out.println("Age: " + dictionary.get("age"));

        IList<String> list = new MyList<>();
        list.add("Hello");
        list.add("World");

        System.out.println("List: " + list);
    }
}