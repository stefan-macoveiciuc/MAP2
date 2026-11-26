package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.repository.IRepository;
import static interpreter.controller.GarbageCollector
        .getAddrFromSymTable;

import static interpreter.controller.GarbageCollector
        .safeGarbageCollector;

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

        PrgState prg = repo.getCrtPrg();

        repo.logPrgStateExec();

        while (!prg.getExeStack().isEmpty()) {

            oneStep(prg);

            // State before garbage collection
            repo.logPrgStateExec();

            // Remove unreachable heap locations
            prg.getHeap().setContent(
                    safeGarbageCollector(
                            getAddrFromSymTable(
                                    prg.getSymTable()
                                            .toMap()
                                            .values()
                            ),
                            prg.getHeap().getContent()
                    )
            );

            // State after garbage collection
            repo.logPrgStateExec();

            display(prg);
        }
    }
}