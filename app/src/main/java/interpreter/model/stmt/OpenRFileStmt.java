package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.IValue;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public record OpenRFileStmt(IExp exp) implements IStmt {

    @Override
    public PrgState execute(PrgState state) throws MyException {

        IValue value = exp.eval(state.getSymTable());

        if (!(value instanceof StringValue fileName)) {
            throw new StatementException(
                    "openRFile: expression is not a string"
            );
        }

        var fileTable = state.getFileTable();

        if (fileTable.isDefined(fileName)) {
            throw new StatementException(
                    "openRFile: file is already open: " + fileName
            );
        }

        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader(fileName.val())
            );

            fileTable.put(fileName, reader);

        } catch (IOException e) {
            throw new StatementException(
                    "openRFile: cannot open file: " + e.getMessage()
            );
        }

        return state;
    }

    @Override
    public String toString() {
        return "openRFile(" + exp + ")";
    }
}