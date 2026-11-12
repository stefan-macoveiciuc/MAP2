package interpreter.controller;

import interpreter.Examples;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.model.value.IntValue;
import interpreter.repository.Repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @TempDir
    Path dir;

    private PrgState run(IStmt program) throws MyException {
        PrgState state = PrgState.of(program);

        Repository repo = new Repository(
                state,
                dir.resolve("log.txt").toString()
        );

        Controller controller = new Controller(repo, false);
        controller.allStep();

        return state;
    }

    @Test
    void example1Prints2() throws MyException {
        PrgState state = run(Examples.EX1);

        assertEquals(new IntValue(2),
                state.getOut().get(0));
    }

    @Test
    void example2Prints18() throws MyException {
        PrgState state = run(Examples.EX2);

        assertEquals(new IntValue(18),
                state.getOut().get(0));
    }

    @Test
    void example3Prints2() throws MyException {
        PrgState state = run(Examples.EX3);

        assertEquals(new IntValue(2),
                state.getOut().get(0));
    }

    @Test
    void example4Reads15And50() throws Exception {
        Path file = dir.resolve("test.in");
        Files.writeString(file, "15\n50\n");

        PrgState state = run(
                Examples.fileExample(file.toString())
        );

        assertEquals(new IntValue(15),
                state.getOut().get(0));

        assertEquals(new IntValue(50),
                state.getOut().get(1));

        assertTrue(state.getFileTable().toMap().isEmpty());
    }

    @Test
    void example4WithEmptyFilePrintsZeroTwice()
            throws Exception {

        Path file = dir.resolve("empty.in");
        Files.writeString(file, "");

        PrgState state = run(
                Examples.fileExample(file.toString())
        );

        assertEquals(new IntValue(0),
                state.getOut().get(0));

        assertEquals(new IntValue(0),
                state.getOut().get(1));

        assertTrue(state.getFileTable().toMap().isEmpty());
    }

    @Test
    void example5Prints5() throws MyException {
        PrgState state = run(Examples.EX5);

        assertEquals(new IntValue(5),
                state.getOut().get(0));
    }

    @Test
    void executionLeavesStackEmpty() throws MyException {
        PrgState state = run(Examples.EX1);

        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    void controllerWritesLogAfterEveryStep()
            throws Exception {

        run(Examples.EX1);

        String log = Files.readString(
                dir.resolve("log.txt")
        );

        int snapshots =
                log.split("ExeStack:", -1).length - 1;

        assertTrue(snapshots > 1);
        assertTrue(log.contains("SymTable:"));
        assertTrue(log.contains("Out:"));
        assertTrue(log.contains("FileTable:"));
    }
}