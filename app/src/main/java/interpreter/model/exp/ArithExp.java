package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;

public record ArithExp(
        ArithOp op,
        IExp left,
        IExp right
) implements IExp {

    @Override
    public IValue eval(IDictionary<String, IValue> symbolTable)
            throws MyException {

        IValue leftValue = left.eval(symbolTable);

        if (!(leftValue instanceof IntValue leftInt)) {
            throw new ExpressionException(
                    "first operand is not an integer"
            );
        }

        IValue rightValue = right.eval(symbolTable);

        if (!(rightValue instanceof IntValue rightInt)) {
            throw new ExpressionException(
                    "second operand is not an integer"
            );
        }

        int a = leftInt.val();
        int b = rightInt.val();

        return switch (op) {
            case ADD -> new IntValue(a + b);
            case SUB -> new IntValue(a - b);
            case MUL -> new IntValue(a * b);
            case DIV -> {
                if (b == 0) {
                    throw new ExpressionException("division by zero");
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