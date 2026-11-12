package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.repository.IRepository;

public class Controller {
    private final IRepository repo;
    private final boolean displayFlag;

    public Controller(IRepository repo, boolean displayFlag) {
        this.repo = repo;
        this.displayFlag = displayFlag;
    }

    public PrgState oneStep(PrgState state) throws MyException {
        if (state.getExeStack().isEmpty()) {
            throw new interpreter.exceptions.StatementException(
                    "Execution stack is empty");
        }

        IStmt stmt = state.getExeStack().pop();
        return stmt.execute(state);
    }

    private void display(PrgState state) {
        if (displayFlag) {
            System.out.println(state);
        }
    }

    public void allStep() throws MyException {
        PrgState state = repo.getCrtPrg();

        repo.logPrgStateExec();
        display(state);

        while (!state.getExeStack().isEmpty()) {
            oneStep(state);
            repo.logPrgStateExec();
            display(state);
        }
    }
}