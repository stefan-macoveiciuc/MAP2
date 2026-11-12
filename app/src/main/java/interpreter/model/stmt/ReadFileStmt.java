package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.type.IntType;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;

import java.io.IOException;

public record ReadFileStmt(IExp exp, String varName) implements IStmt {

    @Override
    public PrgState execute(PrgState state) throws MyException {

        var symTable = state.getSymTable();

        if (!symTable.isDefined(varName)) {
            throw new StatementException(
                    "readFile: variable is not declared: " + varName
            );
        }

        if (!(symTable.lookup(varName).getType() instanceof IntType)) {
            throw new StatementException(
                    "readFile: variable must be of type int"
            );
        }

        IValue value = exp.eval(symTable);

        if (!(value instanceof StringValue fileName)) {
            throw new StatementException(
                    "readFile: file name must be a string"
            );
        }

        var fileTable = state.getFileTable();

        if (!fileTable.isDefined(fileName)) {
            throw new StatementException(
                    "readFile: file is not open: " + fileName
            );
        }

        try {
            String line = fileTable.lookup(fileName).readLine();

            int number;

            if (line == null) {
                number = 0;
            } else {
                number = Integer.parseInt(line.strip());
            }

            symTable.update(varName, new IntValue(number));

        } catch (IOException | NumberFormatException e) {
            throw new StatementException(
                    "readFile: cannot read integer: " + e.getMessage()
            );
        }

        return state;
    }

    @Override
    public String toString() {
        return "readFile(" + exp + ", " + varName + ")";
    }
}