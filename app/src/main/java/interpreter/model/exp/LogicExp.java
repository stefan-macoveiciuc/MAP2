package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

public record LogicExp(
        LogicOp op,
        IExp left,
        IExp right
) implements IExp {

    @Override
    public IValue eval(IDictionary<String, IValue> symbolTable)
            throws MyException {

        IValue leftValue = left.eval(symbolTable);

        if (!(leftValue instanceof BoolValue leftBool)) {
            throw new ExpressionException(
                    "first operand is not a boolean"
            );
        }

        IValue rightValue = right.eval(symbolTable);

        if (!(rightValue instanceof BoolValue rightBool)) {
            throw new ExpressionException(
                    "second operand is not a boolean"
            );
        }

        boolean a = leftBool.val();
        boolean b = rightBool.val();

        return switch (op) {
            case AND -> new BoolValue(a && b);
            case OR -> new BoolValue(a || b);
        };
    }

    @Override
    public String toString() {
        return "(" + left + " " + op + " " + right + ")";
    }
}