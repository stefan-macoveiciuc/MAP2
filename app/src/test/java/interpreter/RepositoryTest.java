package interpreter.repository;

import interpreter.model.PrgState;
import interpreter.model.stmt.NopStmt;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest {

    @TempDir
    Path dir;

    @Test
    void returnsCurrentProgram() {
        PrgState state = PrgState.of(new NopStmt());

        Repository repo = new Repository(
                state,
                dir.resolve("log.txt").toString()
        );

        assertSame(state, repo.getCrtPrg());
    }

    @Test
    void writesAllFourSectionsInOrder() throws Exception {
        Path logFile = dir.resolve("log.txt");

        Repository repo = new Repository(
                PrgState.of(new NopStmt()),
                logFile.toString()
        );

        repo.logPrgStateExec();

        String log = Files.readString(logFile);

        int a = log.indexOf("ExeStack:");
        int b = log.indexOf("SymTable:");
        int c = log.indexOf("Out:");
        int d = log.indexOf("FileTable:");

        assertTrue(0 <= a && a < b && b < c && c < d);
    }

    @Test
    void appendsInsteadOfOverwriting() throws Exception {
        Path logFile = dir.resolve("log.txt");

        Repository repo = new Repository(
                PrgState.of(new NopStmt()),
                logFile.toString()
        );

        repo.logPrgStateExec();
        repo.logPrgStateExec();

        String log = Files.readString(logFile);

        assertEquals(2,
                log.split("ExeStack:", -1).length - 1);
    }
}