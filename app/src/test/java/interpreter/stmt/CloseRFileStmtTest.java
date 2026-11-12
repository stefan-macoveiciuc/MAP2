package interpreter.stmt;

import interpreter.exceptions.*;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.stmt.*;
import interpreter.model.value.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class CloseRFileStmtTest {

    @TempDir
    Path dir;

    @Test
    void closingFileRemovesItFromFileTable() throws Exception {
        Path file = dir.resolve("numbers.in");
        Files.writeString(file, "15\n");

        StringValue name = new StringValue(file.toString());
        ValueExp exp = new ValueExp(name);
        PrgState state = PrgState.of(new NopStmt());

        new OpenRFileStmt(exp).execute(state);
        assertTrue(state.getFileTable().isDefined(name));

        new CloseRFileStmt(exp).execute(state);

        assertFalse(state.getFileTable().isDefined(name));
    }

    @Test
    void closingUnopenedFileThrows() {
        PrgState state = PrgState.of(new NopStmt());

        assertThrows(StatementException.class,
                () -> new CloseRFileStmt(
                        new ValueExp(new StringValue("missing.in"))
                ).execute(state));
    }

    @Test
    void nonStringFileNameThrows() {
        PrgState state = PrgState.of(new NopStmt());

        assertThrows(StatementException.class,
                () -> new CloseRFileStmt(
                        new ValueExp(new IntValue(10))
                ).execute(state));
    }

    @Test
    void closingFileTwiceThrows() throws Exception {
        Path file = dir.resolve("numbers.in");
        Files.writeString(file, "15\n");

        ValueExp exp = new ValueExp(
                new StringValue(file.toString()));
        PrgState state = PrgState.of(new NopStmt());

        new OpenRFileStmt(exp).execute(state);
        new CloseRFileStmt(exp).execute(state);

        assertThrows(StatementException.class,
                () -> new CloseRFileStmt(exp).execute(state));
    }
}