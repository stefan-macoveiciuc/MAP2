
package interpreter.model.exp;

import interpreter.model.adt.IDictionary;
import interpreter.model.value.*;
import interpreter.exceptions.*;

public record RelationalExp(
        RelOp op, IExp left, IExp right
) implements IExp {

    @Override
    public IValue eval(IDictionary<String, IValue> symTable)
            throws MyException {

        IValue first = left.eval(symTable);

        if (!(first instanceof IntValue(int n1))) {
            throw new ExpressionException(
                    "First operand is not an integer"
            );
        }

        IValue second = right.eval(symTable);

        if (!(second instanceof IntValue(int n2))) {
            throw new ExpressionException(
                    "Second operand is not an integer"
            );
        }

        boolean result = switch (op) {
            case LT -> n1 < n2;
            case LE -> n1 <= n2;
            case EQ -> n1 == n2;
            case NE -> n1 != n2;
            case GT -> n1 > n2;
            case GE -> n1 >= n2;
        };

        return new BoolValue(result);
    }

    @Override
    public String toString() {
        return "(" + left + " " + op + " " + right + ")";
    }
}
