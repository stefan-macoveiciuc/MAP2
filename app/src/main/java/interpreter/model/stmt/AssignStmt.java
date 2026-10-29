package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.IValue;

public record AssignStmt(
        String name,
        IExp expression
) implements IStmt {

    @Override
    public PrgState execute(PrgState state)
            throws MyException {

        var symTable = state.getSymTable();

        if (!symTable.containsKey(name)) {
            throw new StatementException(
                    "variable " + name + " was not declared"
            );
        }

        IValue newValue = expression.eval(symTable);
        IValue oldValue = symTable.get(name);

        if (!newValue.getType().equals(oldValue.getType())) {
            throw new StatementException(
                    "declared type of variable " + name
                            + " and type of the assigned expression do not match"
            );
        }

        symTable.put(name, newValue);

        return state;
    }

    @Override
    public String toString() {
        return name + " = " + expression;
    }
}