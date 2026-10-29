package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.repository.IRepository;

public class Controller {

    private final IRepository repository;
    private final boolean displayFlag;

    public Controller(IRepository repository, boolean displayFlag) {
        this.repository = repository;
        this.displayFlag = displayFlag;
    }

    public PrgState oneStep() throws MyException {

        PrgState state = repository.getCurrentProgram();

        if (state.getExeStack().isEmpty()) {
            throw new StatementException(
                    "execution stack is empty"
            );
        }

        IStmt currentStatement =
                state.getExeStack().pop();

        PrgState newState =
                currentStatement.execute(state);

        repository.setCurrentProgram(newState);

        return newState;
    }

    public void allStep() throws MyException {

        PrgState state = repository.getCurrentProgram();

        if (displayFlag) {
            System.out.println("Initial state:");
            System.out.println(state);
        }

        while (!state.getExeStack().isEmpty()) {

            state = oneStep();

            if (displayFlag) {
                System.out.println();
                System.out.println(state);
            }
        }
    }
}