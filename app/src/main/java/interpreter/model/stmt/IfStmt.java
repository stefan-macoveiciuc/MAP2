package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

public record IfStmt(
        IExp condition,
        IStmt thenBranch,
        IStmt elseBranch
) implements IStmt {

    @Override
    public PrgState execute(PrgState state)
            throws MyException {

        IValue conditionValue =
                condition.eval(state.getSymTable());

        if (!(conditionValue instanceof BoolValue boolValue)) {
            throw new StatementException(
                    "if condition is not a boolean"
            );
        }

        if (boolValue.val()) {
            state.getExeStack().push(thenBranch);
        } else {
            state.getExeStack().push(elseBranch);
        }

        return state;
    }

    @Override
    public String toString() {
        return "if (" + condition + ") then ("
                + thenBranch + ") else (" + elseBranch + ")";
    }
}