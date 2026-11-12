package interpreter.repository;

import interpreter.exceptions.*;
import interpreter.model.PrgState;

import interpreter.exceptions.RepositoryException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private final List<PrgState> states = new ArrayList<>();
    private final String logFilePath;

    public Repository(PrgState initial, String logFilePath) {
        states.add(initial);
        this.logFilePath = logFilePath;
    }

    @Override
    public PrgState getCrtPrg() {
        return states.get(0);
    }

    @Override
    public void logPrgStateExec() throws MyException {
        try (PrintWriter logFile = new PrintWriter(
                new BufferedWriter(
                        new FileWriter(logFilePath, true)))) {

            logFile.println(getCrtPrg().toLogString());

        } catch (IOException e) {
            throw new RepositoryException(
                    "Cannot write log file: " + e.getMessage());
        }
    }
}