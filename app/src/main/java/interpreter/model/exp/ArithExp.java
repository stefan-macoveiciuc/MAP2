package interpreter.model.exp;

import interpreter.model.adt.IDictionary;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;

public record ArithExp(
        ArithOp op,
        IExp left,
        IExp right
) implements IExp {

    @Override
    public IValue eval(IDictionary<String, IValue> symbolTable) {

        IValue leftValue = left.eval(symbolTable);
        IValue rightValue = right.eval(symbolTable);

        if (!(leftValue instanceof IntValue leftInt)) {
            throw new RuntimeException("First operand is not an integer");
        }

        if (!(rightValue instanceof IntValue rightInt)) {
            throw new RuntimeException("Second operand is not an integer");
        }

        int a = leftInt.val();
        int b = rightInt.val();

        return switch (op) {
            case ADD -> new IntValue(a + b);
            case SUB -> new IntValue(a - b);
            case MUL -> new IntValue(a * b);
            case DIV -> {
                if (b == 0) {
                    throw new RuntimeException("Division by zero");
                }
                yield new IntValue(a / b);
            }
        };
    }

    @Override
    public String toString() {
        return "(" + left + " " + op + " " + right + ")";
    }
}