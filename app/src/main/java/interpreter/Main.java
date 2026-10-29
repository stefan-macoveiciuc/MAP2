package interpreter;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.*;
import interpreter.model.exp.*;
import interpreter.model.stmt.*;
import interpreter.model.type.*;
import interpreter.model.value.*;
import interpreter.repository.*;

public class Main {

    public static void main(String[] args) {

        // Example 1:
        // int v; v = 2; print(v)

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

        IRepository repository = new Repository(state);

        Controller controller = new Controller(
                repository,
                true
        );

        try {
            controller.allStep();

            System.out.println();
            System.out.println("Program finished!");
            System.out.println(
                    "Final output: "
                            + repository.getCurrentProgram().getOut()
            );

        } catch (MyException e) {
            System.out.println(
                    "Interpreter error: " + e.getMessage()
            );
        }
    }
}