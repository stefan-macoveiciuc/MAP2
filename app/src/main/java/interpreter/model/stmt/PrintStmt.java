package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.IValue;

public record PrintStmt(IExp expression) implements IStmt {

    @Override
    public PrgState execute(PrgState state)
            throws MyException {

        IValue value = expression.eval(state.getSymTable());

        state.getOut().add(value);

        return state;
    }

    @Override
    public String toString() {
        return "print(" + expression + ")";
    }
}