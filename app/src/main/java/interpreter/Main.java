package interpreter;

import interpreter.model.type.*;
import interpreter.model.value.*;

public class Main {

    public static void main(String[] args) {

        IType intType = new IntType();
        IType boolType = new BoolType();

        System.out.println("Integer type: " + intType);
        System.out.println("Integer default: " + intType.defaultValue());

        System.out.println("Boolean type: " + boolType);
        System.out.println("Boolean default: " + boolType.defaultValue());

        IValue number = new IntValue(25);
        IValue active = new BoolValue(true);

        System.out.println("Number: " + number);
        System.out.println("Number type: " + number.getType());

        System.out.println("Active: " + active);
        System.out.println("Active type: " + active.getType());
    }
}