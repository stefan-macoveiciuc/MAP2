package interpreter.model.exp;

import interpreter.model.adt.IDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

public record LogicExp(
        LogicOp op,
        IExp left,
        IExp right
) implements IExp {

    @Override
    public IValue eval(IDictionary<String, IValue> symbolTable) {

        IValue leftValue = left.eval(symbolTable);
        IValue rightValue = right.eval(symbolTable);

        if (!(leftValue instanceof BoolValue leftBool)) {
            throw new RuntimeException("First operand is not a boolean");
        }

        if (!(rightValue instanceof BoolValue rightBool)) {
            throw new RuntimeException("Second operand is not a boolean");
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