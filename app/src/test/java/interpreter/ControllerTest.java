package interpreter;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.*;
import interpreter.model.exp.*;
import interpreter.model.stmt.*;
import interpreter.model.type.*;
import interpreter.model.value.*;
import interpreter.repository.Repository;
import interpreter.controller.Controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {

    @Test
    void example1PrintsTwo() throws MyException {

        IStmt program = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt(
                                "v",
                                new ValueExp(new IntValue(2))
                        ),
                        new PrintStmt(new VarExp("v"))
                )
        );

        PrgState state = new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                program
        );

        Controller controller = new Controller(
                new Repository(state),
                false
        );

        controller.allStep();

        assertTrue(state.getExeStack().isEmpty());

        assertEquals(
                new IntValue(2),
                state.getOut().getElements().get(0)
        );
    }
}