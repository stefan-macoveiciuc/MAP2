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

class OpenRFileStmtTest {

    @TempDir
    Path dir;

    @Test
    void opensFileAndAddsToFileTable() throws Exception {
        Path file = dir.resolve("numbers.in");
        Files.writeString(file, "15\n");

        StringValue name = new StringValue(file.toString());
        ValueExp exp = new ValueExp(name);
        PrgState state = PrgState.of(new NopStmt());

        new OpenRFileStmt(exp).execute(state);

        try {
            assertTrue(state.getFileTable().isDefined(name));
        } finally {
            new CloseRFileStmt(exp).execute(state);
        }
    }

    @Test
    void openingSameFileTwiceThrows() throws Exception {
        Path file = dir.resolve("numbers.in");
        Files.writeString(file, "15\n");

        ValueExp exp = new ValueExp(
                new StringValue(file.toString()));
        PrgState state = PrgState.of(new NopStmt());

        new OpenRFileStmt(exp).execute(state);

        try {
            assertThrows(StatementException.class,
                    () -> new OpenRFileStmt(exp).execute(state));
        } finally {
            new CloseRFileStmt(exp).execute(state);
        }
    }

    @Test
    void missingFileThrows() {
        Path file = dir.resolve("does-not-exist.in");
        PrgState state = PrgState.of(new NopStmt());

        assertThrows(StatementException.class,
                () -> new OpenRFileStmt(
                        new ValueExp(new StringValue(file.toString()))
                ).execute(state));
    }

    @Test
    void nonStringFileNameThrows() {
        PrgState state = PrgState.of(new NopStmt());

        assertThrows(StatementException.class,
                () -> new OpenRFileStmt(
                        new ValueExp(new IntValue(5))
                ).execute(state));
    }
}