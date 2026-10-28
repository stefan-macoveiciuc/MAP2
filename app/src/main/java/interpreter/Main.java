package interpreter;

import interpreter.model.adt.*;
import interpreter.model.exp.*;
import interpreter.model.value.*;

public class Main {

    public static void main(String[] args) {

        IDictionary<String, IValue> symbolTable =
                new MyDictionary<>();

        symbolTable.put("x", new IntValue(10));
        symbolTable.put("active", new BoolValue(true));

        // Test 1: Value expression
        IExp valueExp = new ValueExp(new IntValue(25));

        System.out.println(
                "ValueExp: " + valueExp.eval(symbolTable)
        );

        // Test 2: Variable expression
        IExp varExp = new VarExp("x");

        System.out.println(
                "VarExp: " + varExp.eval(symbolTable)
        );

        // Test 3: Arithmetic expression
        IExp arithExp = new ArithExp(
                ArithOp.ADD,
                new VarExp("x"),
                new ValueExp(new IntValue(5))
        );

        System.out.println(
                "ArithExp: " + arithExp.eval(symbolTable)
        );

        // Test 4: Logical expression
        IExp logicExp = new LogicExp(
                LogicOp.AND,
                new VarExp("active"),
                new ValueExp(new BoolValue(false))
        );

        System.out.println(
                "LogicExp: " + logicExp.eval(symbolTable)
        );

        // Test 5: Nested arithmetic expression
        IExp nestedExp = new ArithExp(
                ArithOp.ADD,
                new ValueExp(new IntValue(2)),
                new ArithExp(
                        ArithOp.MUL,
                        new ValueExp(new IntValue(3)),
                        new ValueExp(new IntValue(5))
                )
        );

        System.out.println(
                "Nested expression: " + nestedExp.eval(symbolTable)
        );
    }
}