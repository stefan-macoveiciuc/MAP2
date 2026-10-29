package interpreter.repository;

import interpreter.model.PrgState;

public class Repository implements IRepository {

    private PrgState currentProgram;

    public Repository(PrgState currentProgram) {
        this.currentProgram = currentProgram;
    }

    @Override
    public PrgState getCurrentProgram() {
        return currentProgram;
    }

    @Override
    public void setCurrentProgram(PrgState state) {
        this.currentProgram = state;
    }
}