
package interpreter;

import interpreter.controller.Controller;
import interpreter.model.PrgState;
import interpreter.model.adt.*;
import interpreter.model.exp.*;
import interpreter.model.stmt.*;
import interpreter.model.type.*;
import interpreter.model.value.*;
import interpreter.repository.*;
import interpreter.view.*;

public class Main {

    private static Controller createController(IStmt program) {

        PrgState state = new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                program
        );

        IRepository repository = new Repository(state);

        return new Controller(repository, true);
    }

    public static void main(String[] args) {

        TextMenu menu = new TextMenu();

        // Ex1: int v; v = 2; print(v)
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));

        // Ex2: int a; int b; a = 2 + 3 * 5; b = a + 1; print(b)
        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a",
                                new ArithExp(ArithOp.ADD, new ValueExp(new IntValue(2)),
                                        new ArithExp(ArithOp.MUL, new ValueExp(new IntValue(3)),
                                                new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",
                                        new ArithExp(ArithOp.ADD, new VarExp("a"),
                                                new ValueExp(new IntValue(1)))),
                                        new PrintStmt(new VarExp("b"))))));

        // Ex3: bool a; int v; a = true; (if a then v = 2 else v = 3); print(v)
        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),
                                        new AssignStmt("v", new ValueExp(new IntValue(2))),
                                        new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                        new PrintStmt(new VarExp("v"))))));

        menu.addCommand(
                new ExitCommand("0", "Exit")
        );

        menu.addCommand(
                new RunExample(
                        "1",
                        "Ex1: int v; v = 2; print(v)",
                        () -> createController(ex1)
                )
        );

        menu.addCommand(
                new RunExample(
                        "2",
                        "Ex2: int a; int b; a = 2 + 3 * 5; b = a + 1; print(b)",
                        () -> createController(ex2)
                )
        );

        menu.addCommand(
                new RunExample(
                        "3",
                        "Ex3: bool a; int v; a = true; if a then v = 2 else v = 3; print(v)",
                        () -> createController(ex3)
                )
        );

        menu.show();
    }
}
