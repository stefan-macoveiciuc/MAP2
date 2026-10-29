package interpreter.model;

import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IList;
import interpreter.model.adt.IStack;
import interpreter.model.stmt.IStmt;
import interpreter.model.value.IValue;

public class PrgState {

    private final IStack<IStmt> exeStack;
    private final IDictionary<String, IValue> symTable;
    private final IList<IValue> out;

    public PrgState(
            IStack<IStmt> exeStack,
            IDictionary<String, IValue> symTable,
            IList<IValue> out,
            IStmt originalProgram
    ) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;

        this.exeStack.push(originalProgram);
    }

    public IStack<IStmt> getExeStack() {
        return exeStack;
    }

    public IDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public IList<IValue> getOut() {
        return out;
    }

    @Override
    public String toString() {
        return "ExeStack: " + exeStack
                + "\nSymTable: " + symTable
                + "\nOut: " + out;
    }
}