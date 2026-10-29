package interpreter.repository;

import interpreter.model.PrgState;

public interface IRepository {
    PrgState getCurrentProgram();
    void setCurrentProgram(PrgState state);
}