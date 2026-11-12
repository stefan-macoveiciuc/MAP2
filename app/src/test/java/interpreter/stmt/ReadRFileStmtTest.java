package interpreter.stmt;

import interpreter.exceptions.*;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.stmt.*;
import interpreter.model.type.*;
import interpreter.model.value.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ReadFileStmtTest {

    @TempDir
    Path dir;

    private PrgState createState() throws MyException {
        PrgState state = PrgState.of(new NopStmt());
        new VarDeclStmt("x", new IntType()).execute(state);
        return state;
    }

    @Test
    void readsNumbersAndReturnsZeroAtEOF() throws Exception {
        Path file = dir.resolve("numbers.in");
        Files.writeString(file, "15\n50\n");

        ValueExp exp = new ValueExp(
                new StringValue(file.toString()));
        PrgState state = createState();

        new OpenRFileStmt(exp).execute(state);

        try {
            new ReadFileStmt(exp, "x").execute(state);
            assertEquals(new IntValue(15),
                    state.getSymTable().lookup("x"));

            new ReadFileStmt(exp, "x").execute(state);
            assertEquals(new IntValue(50),
                    state.getSymTable().lookup("x"));

            new ReadFileStmt(exp, "x").execute(state);
            assertEquals(new IntValue(0),
                    state.getSymTable().lookup("x"));
        } finally {
            new CloseRFileStmt(exp).execute(state);
        }
    }

    @Test
    void unopenedFileThrows() throws MyException {
        PrgState state = createState();

        ValueExp exp = new ValueExp(
                new StringValue("not-open.in"));

        assertThrows(StatementException.class,
                () -> new ReadFileStmt(exp, "x").execute(state));
    }

    @Test
    void undeclaredVariableThrows() throws Exception {
        Path file = dir.resolve("numbers.in");
        Files.writeString(file, "15\n");

        ValueExp exp = new ValueExp(
                new StringValue(file.toString()));
        PrgState state = PrgState.of(new NopStmt());

        new OpenRFileStmt(exp).execute(state);

        try {
            assertThrows(StatementException.class,
                    () -> new ReadFileStmt(exp, "missing")
                            .execute(state));
        } finally {
            new CloseRFileStmt(exp).execute(state);
        }
    }

    @Test
    void nonIntegerVariableThrows() throws Exception {
        Path file = dir.resolve("numbers.in");
        Files.writeString(file, "15\n");

        ValueExp exp = new ValueExp(
                new StringValue(file.toString()));
        PrgState state = PrgState.of(new NopStmt());

        new VarDeclStmt("x", new BoolType()).execute(state);
        new OpenRFileStmt(exp).execute(state);

        try {
            assertThrows(StatementException.class,
                    () -> new ReadFileStmt(exp, "x")
                            .execute(state));
        } finally {
            new CloseRFileStmt(exp).execute(state);
        }
    }

    @Test
    void invalidIntegerThrows() throws Exception {
        Path file = dir.resolve("invalid.in");
        Files.writeString(file, "hello\n");

        ValueExp exp = new ValueExp(
                new StringValue(file.toString()));
        PrgState state = createState();

        new OpenRFileStmt(exp).execute(state);

        try {
            assertThrows(StatementException.class,
                    () -> new ReadFileStmt(exp, "x")
                            .execute(state));
        } finally {
            new CloseRFileStmt(exp).execute(state);
        }
    }

    @Test
    void nonStringFileNameThrows() throws MyException {
        PrgState state = createState();

        assertThrows(StatementException.class,
                () -> new ReadFileStmt(
                        new ValueExp(new IntValue(5)), "x"
                ).execute(state));
    }
}