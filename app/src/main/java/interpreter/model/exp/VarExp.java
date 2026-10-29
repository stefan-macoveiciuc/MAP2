package interpreter.model.exp;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.ExpressionException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.IValue;

public record VarExp(String name) implements IExp {

    @Override
    public IValue eval(IDictionary<String, IValue> symbolTable)
            throws ExpressionException {

        if (!symbolTable.containsKey(name)) {
            throw new ExpressionException(
                    "variable " + name + " is not defined"
            );
        }

        try {
            return symbolTable.get(name);
        } catch (ADTException e) {
            throw new ExpressionException(
                    "variable " + name + " is not defined"
            );
        }
    }

    @Override
    public String toString() {
        return name;
    }
}