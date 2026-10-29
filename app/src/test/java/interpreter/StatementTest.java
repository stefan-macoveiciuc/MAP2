
package interpreter;


import interpreter.model.stmt.*;
import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.*;
import interpreter.model.exp.*;
import interpreter.model.type.*;
import interpreter.model.value.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StatementTest {

    private PrgState state(IStmt program) {
        return new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                program
        );
    }

    private PrgState run(IStmt program) throws MyException {
        PrgState state = state(program);

        while (!state.getExeStack().isEmpty()) {
            IStmt statement = state.getExeStack().pop();
            state = statement.execute(state);
        }

        return state;
    }

    private IExp num(int value) {
        return new ValueExp(new IntValue(value));
    }

    private IExp bool(boolean value) {
        return new ValueExp(new BoolValue(value));
    }

    @Test
    void compoundStatementPushesFirstStatementOnTop()
            throws MyException {

        IStmt first = new NopStmt();
        IStmt second = new PrintStmt(num(5));

        PrgState state = state(
                new CompStmt(first, second)
        );

        IStmt compound = state.getExeStack().pop();
        compound.execute(state);

        assertEquals(first, state.getExeStack().pop());
        assertEquals(second, state.getExeStack().pop());
    }

    @Test
    void declarationCreatesDefaultInteger() throws MyException {
        PrgState state = run(
                new VarDeclStmt("x", new IntType())
        );

        assertEquals(
                new IntValue(0),
                state.getSymTable().get("x")
        );
    }

    @Test
    void declarationCreatesDefaultBoolean() throws MyException {
        PrgState state = run(
                new VarDeclStmt("active", new BoolType())
        );

        assertEquals(
                new BoolValue(false),
                state.getSymTable().get("active")
        );
    }

    @Test
    void redeclaringVariableThrows() {
        IStmt program = new CompStmt(
                new VarDeclStmt("x", new IntType()),
                new VarDeclStmt("x", new IntType())
        );

        assertThrows(
                StatementException.class,
                () -> run(program)
        );
    }

    @Test
    void assignmentUpdatesVariable() throws MyException {
        IStmt program = new CompStmt(
                new VarDeclStmt("x", new IntType()),
                new AssignStmt("x", num(10))
        );

        PrgState state = run(program);

        assertEquals(
                new IntValue(10),
                state.getSymTable().get("x")
        );
    }

    @Test
    void assignmentToUndeclaredVariableThrows() {
        IStmt program = new AssignStmt("x", num(10));

        assertThrows(
                StatementException.class,
                () -> run(program)
        );
    }

    @Test
    void assignmentWithWrongTypeThrows() {
        IStmt program = new CompStmt(
                new VarDeclStmt("x", new IntType()),
                new AssignStmt("x", bool(true))
        );

        assertThrows(
                StatementException.class,
                () -> run(program)
        );
    }

    @Test
    void printAddsValueToOutput() throws MyException {
        PrgState state = run(
                new PrintStmt(num(42))
        );

        assertEquals(
                new IntValue(42),
                state.getOut().getElements().get(0)
        );
    }

    @Test
    void ifTrueExecutesThenBranch() throws MyException {
        IStmt program = new IfStmt(
                bool(true),
                new PrintStmt(num(1)),
                new PrintStmt(num(0))
        );

        PrgState state = run(program);

        assertEquals(
                new IntValue(1),
                state.getOut().getElements().get(0)
        );
        assertEquals(1, state.getOut().getElements().size());
    }

    @Test
    void ifFalseExecutesElseBranch() throws MyException {
        IStmt program = new IfStmt(
                bool(false),
                new PrintStmt(num(1)),
                new PrintStmt(num(0))
        );

        PrgState state = run(program);

        assertEquals(
                new IntValue(0),
                state.getOut().getElements().get(0)
        );
        assertEquals(1, state.getOut().getElements().size());
    }

    @Test
    void nonBooleanIfConditionThrows() {
        IStmt program = new IfStmt(
                num(5),
                new NopStmt(),
                new NopStmt()
        );

        assertThrows(
                StatementException.class,
                () -> run(program)
        );
    }

    @Test
    void nopDoesNotChangeProgramData() throws MyException {
        PrgState state = run(new NopStmt());

        assertTrue(state.getExeStack().isEmpty());
        assertTrue(state.getSymTable().getContent().isEmpty());
        assertTrue(state.getOut().getElements().isEmpty());
    }
}
